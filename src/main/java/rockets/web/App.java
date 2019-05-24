package rockets.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;
import rockets.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.resource.ClassPathResource;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.logging.log4j.core.util.Closer.closeSilently;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    private static DAO dao;

    public static void setDao(DAO dao) {
        App.dao = dao;
    }

    public static void main(String[] args) throws IOException {
        Properties properties = loadProperties();

        int port = Integer.parseInt(properties.getProperty("spark.port"));
        port(port);

        String dbAddress = properties.getProperty("neo4j.dir");
        if (null == dao) {
            dao = new Neo4jDAO(dbAddress);
        }

        // "/"
        handleGetIndex();

        // "/hello"
        handleGetHello();

        // "/register"
        handleGetRegister();

        // "/register"
        handlePostRegister();

        // "/login"
        handleGetLogin();

        // "/login"
        handlePostLogin();

        // "/logout"
        handleGetLogout();

        // "/user/:id"
        handleGetUserById();

        // "/users"
        handleGetUsers();

        // "/rocket/:id
        handleGetRocket();

        // "/rocket/create
        handleGetCreateRocket();

        // "/rocket/create
        handlePostCreateRocket();

        // "/rockets"
        handleGetRockets();

        // "/launches"
//        handleGetLaunches();

    }

    public static void stop() {
        Spark.stop();
    }

    private static void handleGetUsers() {
        get("/users", (req, res) -> {
            Map<String, Object> attributes = new HashMap<String, Object>();
            try {
                attributes.put("users", dao.loadAll(User.class));
                return new ModelAndView(attributes, "users.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "users.html.ftl");
            }
        }, new FreeMarkerEngine());

    }

    private static void handleGetIndex() {
        get("/", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            User user = getLoggedInUser(req);
            attributes.put("user", user);
            return new ModelAndView(attributes, "base_page.html.ftl");
            //return handleBaseHelloView(req, res, attributes);
        }, new FreeMarkerEngine());
    }

    private static void handleGetRegister() {
        get("/register", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("email", "");
            attributes.put("firstName", "");
            attributes.put("lastName", "");

            return new ModelAndView(attributes, "register.html.ftl");
        }, new FreeMarkerEngine());
    }


    /**
     * TODO: a serious bug in this method. Fix it (and test to verify)!
     */
    private static void handlePostRegister() {
        post("/register", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");

            attributes.put("email", email);
            attributes.put("firstName", firstName);
            attributes.put("lastName", lastName);

            logger.info("Registering <" + email + ">, " + password);

            User user;
            try {
                user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                dao.createOrUpdate(user);

                res.status(301);
                req.session(true);
                req.session().attribute("user", user);
                res.redirect("/hello");
                return new ModelAndView(attributes, "base_page.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "register.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    private static void handleGetHello() {
        get("/hello", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            User user = getLoggedInUser(req);
            if (null != user) {
                attributes.put("user", user);
            }
            return new ModelAndView(attributes, "base_page.html.ftl");
        }, new FreeMarkerEngine());
    }

    private static void handleGetLogin() {
        get("/login", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String user_name = req.params("user_name");
            if (null == user_name || user_name.trim().isEmpty()) {
                attributes.put("user_name", "");
            } else {
                attributes.put("user_name", user_name);
            }

            return new ModelAndView(attributes, "login.html.ftl");
        }, new FreeMarkerEngine());
    }

    private static void handlePostLogin() {
        post("/login", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String user_name = req.queryParams("user_name");
            String password = req.queryParams("password");

            logger.info("Logging in <" + user_name + ">, " + password);

            User user = null;
            try {
                user = dao.getUserByEmail(user_name);
            } catch (Exception e) {
                handleException(res, attributes, e, "login.html.ftl");
            }
            if (null != user && user.getPassword().equals(password)) {
                res.status(301);
                req.session(true);
                req.session().attribute("user", user);
                res.redirect("/hello");
                return new ModelAndView(attributes, "base_page.html.ftl");
            } else {
                attributes.put("errorMsg", "Invalid email/password combination.");
                attributes.put("user_name", user_name);
                return new ModelAndView(attributes, "login.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    private static void handleGetLogout() {
        get("/logout", (req, res) -> {
            User user = getLoggedInUser(req);
            spark.Session session = req.session();
            if (null != session && null != user) {
                session.invalidate();
            }
            res.redirect("/");
            return "";
        });
    }

    private static ModelAndView handleException(Response res, Map<String, Object> attributes, Exception e, String templateName) {
        res.status(500);
        if (e instanceof SQLException && null != e.getCause()) {
            attributes.put("errorMsg", e.getCause().getMessage());
        } else {
            attributes.put("errorMsg", e.getMessage());
        }
        e.printStackTrace();
        return new ModelAndView(attributes, templateName);
    }


    private static User getLoggedInUser(Request req) {
        spark.Session session = req.session();
        User user = null;
        if (null != session) {
            user = session.<User>attribute("user");
        }
        return user;
    }

    private static void handleGetUserById() {
        get("/user/:id", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            User user = getLoggedInUser(req);
            attributes.put("user", user);
            try {
                String id = req.params(":id");
                User person = dao.load(User.class, Long.parseLong(id));
                if (null != person) {
                    attributes.put("user", person);
                } else {
                    attributes.put("errorMsg", "No user with the ID " + id + ".");
                }
                return new ModelAndView(attributes, "user.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "user.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    // TODO: Need to TDD this
    private static void handleGetRocket() {
        get("/rocket/:id", (req,res)-> {
            Map<String,Object> attributes = new HashMap<>();
            try{
                String id = req.params(":id");
                Rocket rocket = dao.load(Rocket.class, Long.parseLong(id));
                if(null != rocket)
                    attributes.put("rocket",rocket);
                else {
                    attributes.put("errorMsg","No rocket with the ID " + id + ".");
                }
                return new ModelAndView(attributes, "rocket.html.ftl");
            }
            catch (Exception e) {
                return handleException(res, attributes, e, "rocket.html.ftl");
            }
        },new FreeMarkerEngine());
    }

    // TODO: Need to TDD this
    private static void handlePostCreateRocket() {
        post("/rockets", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            String rockets_name = req.queryParams("rockets_name");
            String country = req.queryParams("country");
            //LaunchServiceProvider manufacturer = req.queryParams("manufacturer");
            String massToLEO = req.queryParams("massToLEO");
            String massToGTO = req.queryParams("massToGTO");
            String massToOther = req.queryParams("massToOther");



            logger.info("Rockets <" + rockets_name + ">, ");

            Rocket rocket = null;
            try {
                rocket = new Rocket();
                rocket.setName(rockets_name);
                rocket.setCountry(country);
                //rocket.setManufacturer(manufacturer);
                rocket.setMassToLEO(massToLEO);
                rocket.setMassToGTO(massToGTO);
                rocket.setMassToOther(massToOther);
                dao.createOrUpdate(rocket);
                rocket = dao.getRocketByName(rockets_name);
            } catch (Exception e) {
                handleException(res, attributes, e, "rockets.html.ftl");
            }
            if (null != rocket && rocket.getName().equals(rockets_name)) {
                res.status(301);
                req.session(true);
                req.session().attribute("rocket", rocket);
                res.redirect("/rocket");
                return new ModelAndView(attributes, "base_page.html.ftl");
            } else {
                attributes.put("errorMsg", "Invalid Rocket name you chose.");
                attributes.put("rocket_name", rockets_name);
                return new ModelAndView(attributes, "rockets.html.ftl");
                
            }
        }, new FreeMarkerEngine());
    }

    // TODO: Need to TDD this
    private static void handleGetCreateRocket() {
        get("/rocket/create", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("rockets_name", "");
            attributes.put("country", "");
            attributes.put("massToLEO", "");
            attributes.put("massToGTO", "");
            attributes.put("massToOther", "");

            return  new ModelAndView(attributes, "rocket.html.ftl");
        }, new FreeMarkerEngine());
    }


    private static void handleGetRockets() {
        get("/rockets", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            try {
                attributes.put("missions", dao.loadAll(Rocket.class));
                return new ModelAndView(attributes, "rockets.html.ftl");
            } catch (Exception e) {
                return handleException(res, attributes, e, "rockets.html.ftl");
            }
        }, new FreeMarkerEngine());
    }

    private static Properties loadProperties() throws IOException {
        ClassPathResource resource = new ClassPathResource("app.properties");
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = resource.getInputStream();
            properties.load(stream);
            return properties;
        } finally {
            closeSilently(stream);
        }
    }
}
