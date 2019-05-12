package rockets.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notBlank;

public class User extends Entity {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        notBlank(firstName,"firstName cannot be null or empty");
        if(checkName(firstName))
            this.firstName = firstName;
        else
            throw new IllegalArgumentException("wrong firstName format");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        notBlank(lastName,"lastName cannot be null or empty");
        if(checkName(lastName))
            this.lastName = lastName;
        else
            throw new IllegalArgumentException("wrong lastName format");
    }

    public boolean checkName(String str){
        for (int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if (!Character.isLetter(ch)){
                return false;
            }
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        notBlank(email, "email cannot be null or empty");
        if(checkEmail(email))
            this.email = email;
        else{
            throw new IllegalArgumentException("wrong email format");
        }

        this.email = email;
    }

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        notBlank(password, "password cannot be null or empty");
        if(checkMatch(password))
            this.password = password;
        else
            throw new IllegalArgumentException("wrong password format");
    }
    // 6 - 12 include letters and numbers
    public static boolean checkMatch(String str) {
        boolean isDigit = false;//include numbers
        boolean isLetter = false;//include letters
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //use char find the numbers
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //use char find the letters
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]{6,12}$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    // match the given password against user's password and return the result
    public boolean isPasswordMatch(String password) {
        return this.password.equals(password.trim());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
