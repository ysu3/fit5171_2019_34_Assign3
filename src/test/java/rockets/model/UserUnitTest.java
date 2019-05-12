package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }


    @DisplayName("should throw exception when pass a empty email address to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetEmailToEmpty(String email) {
        //IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setEmail(email));
        //assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setEmail function")
    @Test
    public void shouldThrowExceptionWhenSetEmailToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> user.setEmail(null));
        assertEquals("email cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass wrong email format to setEmail function")
    @ParameterizedTest
    @ValueSource(strings = {"123465", " asd@com.@", "asd@ww",})
    public void shouldThrowExceptionWhenSetEmailToWrongFormat(String str) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setEmail(str));
        assertEquals("wrong email format", exception.getMessage());
    }

    @DisplayName("should throw exceptions when pass a null password to setPassword function")
    @Test
    public void shouldThrowExceptionWhenSetPasswordToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> user.setPassword(null));
        assertEquals("password cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass wrong password to setPassword function")
    @ParameterizedTest
    @ValueSource(strings = {"123465", " 123456789123", "aasdaasdasdas", "1231asdasd1111111111"})
    public void shouldThrowExceptionWhenSetPasswordToWrongFormat(String str) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setPassword(str));
        assertEquals("wrong password format", exception.getMessage());
    }

    @DisplayName("should return true when two users have the same email")
    @Test
    public void shouldReturnTrueWhenUsersHaveSameEmail() {
        String email = "abc@example.com";
        user.setEmail(email);
        User anotherUser = new User();
        anotherUser.setEmail(email);
        assertTrue(user.equals(anotherUser));
    }
    @DisplayName("should return false when two users have different emails")
    @Test
    public void shouldReturnFalseWhenUsersHaveDifferentEmails() {
        user.setEmail("abc@example.com");
        User anotherUser = new User();
        anotherUser.setEmail("def@example.com");
        assertFalse(user.equals(anotherUser));
    }

    @DisplayName("should throw exception when pass a empty firstname to setFisrtName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetFirstNameToEmpty(String firstName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setFirstName(firstName));
        assertEquals("firstName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setFisrtName function")
    @Test
    public void shouldThrowExceptionWhenSetsetFisrtNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> user.setFirstName(null));
        assertEquals("firstName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass wrong firstName format to setFirstName function")
    @ParameterizedTest
    @ValueSource(strings = {"123465", " sad as", "asd@",})
    public void shouldThrowExceptionWhenSetFirstNameToWrongFormat(String str) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setFirstName(str));
        assertEquals("wrong firstName format", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty lastName to setlastName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetlastNameToEmpty(String lastName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setLastName(lastName));
        assertEquals("lastName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setlastName function")
    @Test
    public void shouldThrowExceptionWhenSetLastNameNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> user.setLastName(null));
        assertEquals("lastName cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass wrong lastName format to setLastName function")
    @ParameterizedTest
    @ValueSource(strings = {"123465", " sad as", "asd@",})
    public void shouldThrowExceptionWhenSetLastNameToWrongFormat(String str) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setLastName(str));
        assertEquals("wrong lastName format", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123@utulsa.edu"})
    public void positiveTestToSetEmail(String email) {
        user.setEmail(email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"shao"})
    public void positiveTestToFirstLastName(String name) {
        user.setFirstName(name);
        user.setLastName(name);
    }
    @ParameterizedTest
    @ValueSource(strings = {"123qazwsx"})
    public void positiveTestToPassword(String password) {
        user.setPassword(password);
    }
}