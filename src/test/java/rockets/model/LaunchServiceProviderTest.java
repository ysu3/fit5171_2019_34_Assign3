package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class LaunchServiceProviderTest {

    private LaunchServiceProvider launchServiceProvider;

    @BeforeEach
    public void setUp(){
        launchServiceProvider = new LaunchServiceProvider("xiChangStation",1982,"China");
    }

    @DisplayName("should throw exception when pass a empty setHeadquarters function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetHeadquartersNameToEmpty(String headquarters) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launchServiceProvider.setHeadquarters(headquarters));
        assertEquals("headquarters cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setHeadquarters function")
    @Test
    public void shouldThrowExceptionWhenSetHeadquartersNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launchServiceProvider.setHeadquarters(null));
        assertEquals("headquarters cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setRockets function")
    @Test
    public void shouldThrowExceptionWhenSetRocketsToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launchServiceProvider.setRockets(null));
        assertEquals("rockets cannot be null or empty", exception.getMessage());
    }


    @DisplayName("should throw exception when pass a empty to SetCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetCountryToEmpty(String country) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launchServiceProvider.setCountry(country));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to SetCountry function")
    @Test
    public void shouldThrowExceptionWhenSetCountryToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launchServiceProvider.setCountry(null));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty to SetCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetNameToEmpty(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launchServiceProvider.setName(name));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setName function")
    @Test
    public void shouldThrowExceptionWhenSetNameToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launchServiceProvider.setName(null));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass Strings or non-integer to setYearFounded function")
    @ParameterizedTest
    @ValueSource(ints = {-1, 1200, 2100})
    public void shouldThrowExceptionWhenSetYearFoundedToNegative(int year) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launchServiceProvider.setYearFounded(year));
        assertEquals("Invalid Year", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setHeadquarters function")
    @Test
    public void shouldThrowExceptionWhenSetHeadquartersToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launchServiceProvider.setHeadquarters(null));
        assertEquals("headquarters cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass invalid to setName function")
    @ParameterizedTest
    @ValueSource(strings = {"asd2#@", " asda@3SAD@",})
    public void shouldThrowExceptionWhenSetNameToInvalid(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launchServiceProvider.setName(name));
        assertEquals("Invalid input", exception.getMessage());
    }

    @DisplayName("should throw exception when pass invalid to setCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"asd2#@", " asda@3SAD@",})
    public void shouldThrowExceptionWhenSetCountryToInvalid(String country) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launchServiceProvider.setCountry(country));
        assertEquals("Invalid input", exception.getMessage());
    }

    @DisplayName("positive test for Name")
    @Test
    public void PositiveTestName()
    {
        String name = "melb";
        launchServiceProvider.setName(name);
        assertTrue(name.equals(launchServiceProvider.getName()));

    }

    @DisplayName("positive test for Country")
    @Test
    public void PositiveTestCountry()
    {
        String name = "melb";
        launchServiceProvider.setCountry(name);
        assertTrue(name.equals(launchServiceProvider.getCountry()));

    }

    @DisplayName("positive test for headQuarter")
    @Test
    public void PositiveTestHeadQuarter()
    {
        String name = "melb";
        launchServiceProvider.setHeadquarters(name);
        assertTrue(name.equals(launchServiceProvider.getHeadquarters()));
    }

    @DisplayName("positive test for year")
    @Test
    public void PositiveTestYear()
    {
        int name = 1988;
        launchServiceProvider.setYearFounded(name);
        assertTrue(name ==(launchServiceProvider.getYearFounded()));
    }
    



}

