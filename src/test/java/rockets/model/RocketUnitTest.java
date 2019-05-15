package rockets.model;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.CsvSource;


public class RocketUnitTest {
    private Rocket rocket;

    @BeforeEach
    public void setUp(){

        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        rocket = new Rocket("good","USA",manufacturer);}

    @AfterEach
    public void tearDown() {
    }


    @DisplayName("should create rocket successfully when given right parameters to constructor")
    @Test
    public void shouldConstructRocketObject() {
        String name = "BFR";
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name, country, manufacturer);
        assertNotNull(bfr);
    }

    @DisplayName("should throw exception when given null manufacturer to constructor")
    @Test
    public void shouldThrowExceptionWhenNoManufacturerGiven() {
        String name = "BFR";
        String country = "USA";
        assertThrows(NullPointerException.class, () -> new Rocket(name, country, null));
    }

    @DisplayName("should throw exception when pass null to setName function")
    @Test
    public void shouldThrowExceptionWhenSetNameToNull() {
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Rocket(null, "USA", manufacturer));
        assertEquals("The validated object is null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setCountry  function")
    @Test
    public void shouldThrowExceptionWhenSetCountryToNull() {
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Rocket("paceX", null, manufacturer));

        assertEquals("The validated object is null", exception.getMessage());
    }
    @DisplayName("should throw illegal argument exception when wrong values set to setMassToLEO and pass right value")
    //the rockets LEO limit is 140000, carry mass can not be strings, blank and negative.
    @ParameterizedTest
    @ValueSource(strings = {"-1", " 140001", "A", " "})
    public void shouldThrowExceptionWhenWrongValuePassedSetMassToLEO(String str){
        assertThrows(IllegalArgumentException.class,() -> rocket.setMassToLEO(str));
        rocket.setMassToLEO("200");
        assertEquals("200",rocket.getMassToLEO());
    }

    @DisplayName("should set rocket massToLEO value")
    @ValueSource(strings = {"10000", "15000"})
    public void shouldSetMassToLEOWhenGivenCorrectValue(String massToLEO) {
        String name = "BFR";
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");

        Rocket bfr = new Rocket(name, country, manufacturer);

        bfr.setMassToLEO(massToLEO);
        assertEquals(massToLEO, bfr.getMassToLEO());
    }

    @DisplayName("should throw exception when set massToLEO to null")
    @Test
    public void shouldThrowExceptionWhenSetMassToLEOToNull() {
        String name = "BFR";
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name, country, manufacturer);
        assertThrows(NullPointerException.class, () -> bfr.setMassToLEO(null));
    }

    @DisplayName("should throw exception when set massGTO to null")
    @Test
    public void shouldThrowExceptionWhenSetMassToGTOToNull() {
        String name = "BFR";
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name, country, manufacturer);
        assertThrows(NullPointerException.class, () -> bfr.setMassToLEO(null));
    }

    @DisplayName("should throw exception when set massToLEO to null")
    @Test
    public void shouldThrowExceptionWhenSetMassToOtherToNull() {
        String name = "BFR";
        String country = "USA";
        LaunchServiceProvider manufacturer = new LaunchServiceProvider("SpaceX", 2002, "USA");
        Rocket bfr = new Rocket(name, country, manufacturer);
        assertThrows(NullPointerException.class, () -> bfr.setMassToLEO(null));
    }

    @DisplayName("should throw illegal argument exception when set null to setMassToLEO")
    @Test
    public void shouldThrowExceptionWhenSetNullToSetMassToLEO(){
        assertThrows(NullPointerException.class,() -> rocket.setMassToLEO(null));
    }


    @DisplayName("should throw illegal argument exception when set null to setMassToGTO")
    @Test
    public void shouldThrowExceptionWhenSetNullToSetMassToGTO(){
        assertThrows(NullPointerException.class,() -> rocket.setMassToGTO(null));
    }

    @DisplayName("should throw illegal argument exception when wrong values set to setMassToGTO and pass right value")
    //the rockets GTO limit is 66000, carry mass can not be strings, blank and negative.
    @ParameterizedTest
    @ValueSource(strings = {"-1", "66001", "A", " "})
    public void shouldThrowExceptionWhenWrongValuePassedSetMassToGTO(String str){
        assertThrows(IllegalArgumentException.class,() -> rocket.setMassToGTO(str));
        rocket.setMassToGTO("200");
        assertEquals("200",rocket.getMassToGTO());
    }

    @DisplayName("should throw illegal argument exception when set null to setMassToOther")
    @Test
    public void shouldThrowExceptionWhenSetNullToSetMassToOther(){
        assertThrows(NullPointerException.class,() -> rocket.setMassToOther(null));
    }

    @DisplayName("should throw illegal argument exception when wrong values set to setMassToOther and pass right value")
    //the rockets Other limit is 50000, carry mass can not be strings, blank and negative.
    @ParameterizedTest
    @ValueSource(strings = {"-1", " 50001", "A", " "})
    public void shouldThrowExceptionWhenWrongValuePassedSetMassToOther(String str){
        assertThrows(IllegalArgumentException.class,() -> rocket.setMassToOther(str));
        rocket.setMassToOther("200");
        assertEquals("200",rocket.getMassToOther());
    }

    @DisplayName("should throw exception when pass a empty  to setName function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetNameToEmpty(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rocket.setName(name));
        assertEquals("name cannot be null or empty", exception.getMessage());
    }


    @DisplayName("should throw exception when pass a empty  to setCountry function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetCountryToEmpty(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rocket.setCountry(name));
        assertEquals("country cannot be null or empty", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setManufacturer function")
    @Test
    public void shouldThrowExceptionWhenSetManufacturerToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> rocket.setManufacturer(null));
        assertEquals("manufacturer name cannot be null or empty", exception.getMessage());
    }

    @DisplayName("The name can include alphabets and digits")
    @ParameterizedTest
    @ValueSource(strings = {"Ias23@@", "@@!!", "dasd11f^^"})
    public void shouldThrowExceptionWhenSetNameWithSpecialCharacters(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rocket.setName(name));
        assertEquals("wrong name format", exception.getMessage());
    }

    @DisplayName("The country can only include alphabets")
    @ParameterizedTest
    @ValueSource(strings = {"Ias23@@", "12312", "dasd11f^^"})
    public void shouldThrowExceptionWhenSetCountryWithSpecialCharacters(String country) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rocket.setCountry(country));
        assertEquals("wrong name format", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"asdaf", "asfgf", "as213sad"})
    public void PositiveName(String name) {
        rocket.setName(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"asdaf", "asfgf", "assad"})
    public void PositiveCountryName(String name) {
        rocket.setCountry(name);
    }



}