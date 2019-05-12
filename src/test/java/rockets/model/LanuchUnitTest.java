package rockets.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class LanuchUnitTest {
    private Launch launch;

    @BeforeEach
    public void setUp() {
        launch = new Launch();
    }

    @DisplayName("should throw exception when pass null to setLauchDate function")
    @Test
    public void shouldThrowExceptionWhenSetLaunchDateToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchDate(null));
        assertEquals("launch date cannot be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass year smaller than 1945 to setLauchDate function")
    @Test
    public void shouldThrowExceptionWhenSetLaunchDateTo1800() {
        LocalDate date = LocalDate.of(1800,1,1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setLaunchDate(date));
        assertEquals("launch date cannot less than 1945", exception.getMessage());
    }

    @DisplayName("should throw exception when pass year exceed current data to setLauchDate function")
    @Test
    public void shouldThrowExceptionWhenSetLaunchDateTo2100() {
        LocalDate date = LocalDate.of(2100,1,1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setLaunchDate(date));
        assertEquals("launch date cannot greater than current date", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setLaunchVehicle function")
    @Test
    public void shouldThrowExceptionWhenSetLaunchVehicleToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchVehicle(null));
        assertEquals("vehicle can not be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setLaunchServiceProvider function")
    @Test
    public void shouldThrowExceptionWhenSetLaunchServiceProviderToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchServiceProvider(null));
        assertEquals("launchServiceProvider can not be null", exception.getMessage());
    }


    @DisplayName("should throw exception when pass null to setPayload function")
    @Test
    public void shouldThrowExceptionWhenSetPayloadstoNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setPayload(null));
        assertEquals("payload can not be null",exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setLaunchSite function")
    @Test
    public void shouldThrowExceptionWhenSetLaunchSiteToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchSite(null));
        assertEquals("launchSite can not be blank", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty launch site to setLaunchSite function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetLaunchSiteToEmpty(String launchSite) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setLaunchSite(launchSite));
        assertEquals("launchSite can not be blank", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setOrbit function")
    @Test
    public void shouldThrowExceptionWhenSetOrbitToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setOrbit(null));
        assertEquals("orbit can not be blank", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty orbit to setOrbit function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetOrbitToEmpty(String orbit) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setOrbit(orbit));
        assertEquals("orbit can not be blank", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setFunction function")
    @Test
    public void shouldThrowExceptionWhenSetFunctionToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setFunction(null));
        assertEquals("function can not be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass a empty function to setFunction function")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void shouldThrowExceptionWhenSetFunctionToEmpty(String function) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setFunction(function));
        assertEquals("function can not be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setPrice function")
    @Test
    public void shouldThrowExceptionWhenSetPriceToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setPrice(null));
        assertEquals("price can not be null", exception.getMessage());
    }

    @DisplayName("should throw exception when pass negative value to setPrice function")
    @Test
    public void shouldThrowExceptionWhenSetPriceToNegative() {
        BigDecimal price = new BigDecimal(-1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> launch.setPrice(price));
        assertEquals("price can not smaller than 0", exception.getMessage());
    }

    @DisplayName("should throw exception when pass null to setLaunchOutCome function")
    @Test
    public void shouldThrowExceptionWhenSetLaunchOutComeToNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> launch.setLaunchOutcome(null));
        assertEquals("launchOutcome can not be null", exception.getMessage());
    }

    @DisplayName("should return true when two launches have the same launch date and launch service provider")
    @Test
    public void shouldReturnTrueWhenLaunchesHaveSameDateVehicleServiceANDOrbit() {
        LocalDate date = LocalDate.of(1995, 2, 20);
        LaunchServiceProvider serviceProvider = new LaunchServiceProvider("China National Space Administration", 1920, "China");
        launch.setLaunchDate(date);
        Launch anotherLaunch = new Launch();
        anotherLaunch.setLaunchDate(date);
        assertTrue(launch.equals(anotherLaunch));

    }

    @DisplayName("positive test for Launch Site")
    @Test
    public void PositiveTestLaunchSiteGet()
    {
        String launchSiteName = "melb";
        launch.setLaunchSite(launchSiteName);
        assertTrue(launchSiteName.equals(launch.getLaunchSite()));

    }

    @DisplayName("positive test for Orbit")
    @Test
    public void PositiveTestOrbitGet()
    {
        String orbName = "GTO";
        launch.setOrbit(orbName);
        assertTrue(orbName.equals(launch.getOrbit()));
    }

    @DisplayName("positive test for Name")
    @Test
    public void PositiveTestLName()
    {
        String fName = "Balanced";
        launch.setFunction(fName);
        assertTrue(fName.equals(launch.getFunction()));
    }

    @DisplayName("positive test for Launch Price")
    @Test
    public void PositiveTestPrice() {
        BigDecimal bd = new BigDecimal(1000);
        launch.setPrice(bd);
        assertEquals(bd,launch.getPrice());
    }


}


