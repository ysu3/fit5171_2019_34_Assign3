package rockets.mining;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RocketMinerUnitTest {
    Logger logger = LoggerFactory.getLogger(RocketMinerUnitTest.class);

    private DAO dao;
    private RocketMiner miner;
    private List<Rocket> rockets;
    private List<LaunchServiceProvider> lsps;
    private List<Launch> launches;

    @BeforeEach
    public void setUp() {
        dao = mock(Neo4jDAO.class);
        miner = new RocketMiner(dao);
        rockets = Lists.newArrayList();

        lsps = Arrays.asList(
                new LaunchServiceProvider("ULA", 1990, "USA"),
                new LaunchServiceProvider("SpaceX", 2002, "USA"),
                new LaunchServiceProvider("ESA", 1975, "Europe ")
        );

        // index of lsp of each rocket
        int[] lspIndex = new int[]{0, 0, 0, 1, 1};
        // 5 rockets
        for (int i = 0; i < 5; i++) {
            rockets.add(new Rocket("rocket_" + i, "USA", lsps.get(lspIndex[i])));
        }
        // month of each launch
        int[] months = new int[]{1, 6, 4, 3, 4, 11, 6, 5, 12, 5};


        // index of rocket of each launch
        int[] rocketIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};

        int[] year = new int[]{1990,1990,1990,2018,2005,2003,2010,2012,2011,1995};
        // 10 launches
        launches = IntStream.range(0, 10).mapToObj(i -> {
            logger.info("create " + i + " launch in month: " + months[i]);
            Launch l = new Launch();
            l.setLaunchDate(LocalDate.of(year[i], months[i], 1));
            l.setLaunchVehicle(rockets.get(rocketIndex[i]));
            l.setLaunchSite("VAFB");
            l.setOrbit("LEO");
            l.setPrice(BigDecimal.valueOf(10000 + 1000*i));
            if (i <= 5) {
                l.setLaunchOutcome(Launch.LaunchOutcome.SUCCESSFUL);
            } else {
                l.setLaunchOutcome(Launch.LaunchOutcome.FAILED);
            }
            spy(l);
            return l;
        }).collect(Collectors.toList());
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnTopMostRecentLaunches(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getLaunchDate().compareTo(b.getLaunchDate()));
        List<Launch> loadedLaunches = miner.mostRecentLaunches(k);
        assertEquals(k, loadedLaunches.size());
        assertEquals(sortedLaunches.subList(0, k), loadedLaunches);
    }

    @ParameterizedTest
    @ValueSource(strings = { "USA", "GTO", "Others" })
    public void shouldReturnTheCountryWhichHasTheMostLaunchesOnThisOrbit(String orbit)
    {
        String country = miner.dominantCountry(orbit);
        assertEquals(country, null);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void shouldReturnMostExpensiveLaunches(int k)
    {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Launch> sortedLaunches = new ArrayList<>(launches);
        sortedLaunches.sort((a, b) -> -a.getPrice().compareTo(b.getPrice()));
        List<Launch> finalLaunchList = miner.mostExpensiveLaunches(k);
        assertEquals(k,finalLaunchList.size());
        assertEquals(sortedLaunches.subList(0,k),finalLaunchList);
    }

    @ParameterizedTest
    @CsvSource({"1,1990"})
    public void shouldReturnHighestRevenueLaunchServiceProviders(int k, int year)
    {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
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
        launchServiceProviderIntegerMap.entrySet().stream().sorted((a,b) -> -a.getValue().compareTo(b.getValue()));

        ArrayList<LaunchServiceProvider> launchServiceProviderList = new ArrayList<>(launchServiceProviderIntegerMap.keySet());
        List<LaunchServiceProvider> finalResult = launchServiceProviderList.stream().limit(k).collect(Collectors.toList());
        List<LaunchServiceProvider> loadedLaunchServiceProviders = miner.highestRevenueLaunchServiceProviders(k,year);
        assertEquals(k, loadedLaunchServiceProviders.size());
        assertEquals(finalResult, loadedLaunchServiceProviders);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2})
    public void shouldReturnMostSuccessfulLaunchServiceProviders(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        when(dao.loadAll(LaunchServiceProvider.class)).thenReturn(lsps);
        List<LaunchServiceProvider> sortedLaunchServiceProviders = new ArrayList<>(lsps);
        List<LaunchServiceProvider> loadedLSPs = miner.mostReliableLaunchServiceProviders(k);
        assertEquals(k, loadedLSPs.size());
        assertEquals(sortedLaunchServiceProviders.subList(0, k), loadedLSPs);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldReturnMostLaunchedRockets(int k) {
        when(dao.loadAll(Launch.class)).thenReturn(launches);
        List<Rocket> sortedLaunchedRocket = new ArrayList<>(rockets);
        for (Rocket rocket: rockets) {
            sortedLaunchedRocket.add(rocket);
        }
        List<Rocket> loadedRockets = miner.mostLaunchedRockets(k);
        assertEquals(loadedRockets.size(), k);
        assertEquals(sortedLaunchedRocket.subList(0, k), loadedRockets);
    }

}