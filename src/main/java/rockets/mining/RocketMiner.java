package rockets.mining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import javax.security.auth.callback.LanguageCallback;
import java.util.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class RocketMiner {
    private static Logger logger = LoggerFactory.getLogger(RocketMiner.class);

    private DAO dao;

    public RocketMiner(DAO dao) {
        this.dao = dao;
    }

    /**
     * TODO: to be implemented & tested!
     * Returns the top-k most active rockets, as measured by number of completed launches.
     *
     * @param k the number of rockets to be returned.
     * @return the list of k most active rockets.
     */
    public List<Rocket> mostLaunchedRockets(int k)
    {
        logger.info("find most launched " + k + " rockets");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        notNull(launches,"launches can not be null");
        Map<Rocket, Long> rocketLongMap = launches.stream().collect(Collectors.groupingBy(Launch::getLaunchVehicle, Collectors.counting()));
        Map<Rocket, Long> sortedRockets = new LinkedHashMap<>();
        rocketLongMap.entrySet().stream().sorted(Map.Entry.<Rocket, Long>comparingByValue().reversed()).forEach(a -> sortedRockets.put(a.getKey(), a.getValue()));
        ArrayList<Rocket> finalResult = new ArrayList<>(sortedRockets.keySet());
        return finalResult.stream().limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the top-k most reliable launch service providers as measured
     * by percentage of successful launches.
     *
     * @param k the number of launch service providers to be returned.
     * @return the list of k most reliable ones.
     */
    public List<LaunchServiceProvider> mostReliableLaunchServiceProviders(int k)
    {
        logger.info("find most reliable " + k + " launch service providers");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        List<Launch> LaunchList = new ArrayList<>(launches);
        for (Launch launch : launches)
        {
            if(launch.getLaunchOutcome() == Launch.LaunchOutcome.SUCCESSFUL)
                LaunchList.add(launch);
        }
        //change the data type of Map keyvalue from LaunchServiceProvider to String
        Map<String, Long> counts = LaunchList.stream().collect(Collectors.groupingBy(o -> o.getLaunchVehicle().getManufacturer(),Collectors.counting()));
        Map<String, Long> sortedLsp = new LinkedHashMap<>();
        counts.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(a -> sortedLsp.put(a.getKey(), a.getValue()));
        ArrayList<LaunchServiceProvider> mostLsp = new ArrayList<>();
        // use the for loop to generate a new Collection to store the LaunchServiceProvider.
        for(String key : sortedLsp.keySet())
        {
            for(Launch launch : launches)
            {
                // get each of the launch and then compare the name of the launchServiceProvider.
                LaunchServiceProvider provider = launch.getLaunchServiceProvider();
                  if(key.equals(provider.getName()))
                      //add the LaunchServiceProvider into the new list.
                      mostLsp.add(provider);
            }
        }
        return mostLsp.stream().limit(k).collect(Collectors.toList());
    }

    /**
     * <p>
     * Returns the top-k most recent launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most recent launches.
     */
    public List<Launch> mostRecentLaunches(int k) {
        logger.info("find most recent " + k + " launches");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Comparator<Launch> launchDateComparator = (a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate());
        return launches.stream().sorted(launchDateComparator).limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the dominant country who has the most launched rockets in an orbit.
     *
     * @param orbit the orbit
     * @return the country who sends the most payload to the orbit
     */
    public String dominantCountry(String orbit) {
        logger.info("find dominant country who launched the most rockets to the " + orbit);
        Collection<Launch> launches = dao.loadAll(Launch.class);
        notNull(launches,"launches can not be null");
        ArrayList<Rocket> rockets = new ArrayList<>();
        notNull(rockets,"rockets can not be null");
        Map<String,Integer> countryMap = new HashMap<>();
        for (Launch launch : launches) {
            if (launch.getOrbit().equals(orbit))
                rockets.add(launch.getLaunchVehicle());
        }
        for (Rocket rocket : rockets) {
            String launchedCountry = rocket.getCountry();
            if (countryMap.containsKey(launchedCountry))
            {
                int count = countryMap.get(launchedCountry) + 1;
                countryMap.replace(launchedCountry,count);
            }
            else
                countryMap.put(launchedCountry,1);
        }
        int dominantV = 0;
        String dominantK = null;
        Iterator keys = countryMap.keySet().iterator();
        while (keys.hasNext()) {
            Object key = keys.next();
            dominantK = key.toString();
            int value = countryMap.get(dominantK);
            if (value > dominantV) {
                dominantV = value;
                countryMap.replace(dominantK, dominantV);
            }
            else if (value == dominantV)
                countryMap.replace(dominantK, dominantV);
        }
        return dominantK;
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns the top-k most expensive launches.
     *
     * @param k the number of launches to be returned.
     * @return the list of k most expensive launches.
     */
    public List<Launch> mostExpensiveLaunches(int k) {
        logger.info("find most Expensive " + k + "Launches");
        Collection<Launch> launchesCost = dao.loadAll(Launch.class);
        Comparator<Launch> launchComparator = (a,b) -> -a.getPrice().compareTo(b.getPrice());
        return launchesCost.stream().sorted(launchComparator).limit(k).collect(Collectors.toList());
    }

    /**
     * TODO: to be implemented & tested!
     * <p>
     * Returns a list of launch service provider that has the top-k highest
     * sales revenue in a year.
     *
     * @param k the number of launch service provider.
     * @param year the year in request
     * @return the list of k launch service providers who has the highest sales revenue.
     */
    public List<LaunchServiceProvider> highestRevenueLaunchServiceProviders(int k, int year) {

        logger.info("find highest Revenue of " + k + " launch service providers");
        Collection<Launch> launches = dao.loadAll(Launch.class);
        Map<LaunchServiceProvider,BigDecimal> launchServiceProviderIntegerMap = new HashMap<>();
        for (Launch launch : launches)
        {
            LocalDate date = launch.getLaunchDate();
            if (year == date.getYear())
            {
                BigDecimal revenue = new BigDecimal("0");
                if (launchServiceProviderIntegerMap.containsKey(launch.getLaunchServiceProvider()))
                {
                    revenue = revenue.add(launch.getPrice());
                    launchServiceProviderIntegerMap.put(launch.getLaunchServiceProvider(),revenue);
                }
                else
                    launchServiceProviderIntegerMap.put(launch.getLaunchServiceProvider(),new BigDecimal("0"));
            }
        }
        launchServiceProviderIntegerMap.entrySet().stream().sorted((a,b) -> -a.getValue().compareTo(b.getValue())).forEach(a -> launchServiceProviderIntegerMap.put(a.getKey(), a.getValue()));
        ArrayList<LaunchServiceProvider> launchServiceProviderList = new ArrayList<>(launchServiceProviderIntegerMap.keySet());
        return launchServiceProviderList.stream().limit(k).collect(Collectors.toList());
    }

}
