package dijkstra.tests;

import dijkstra.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalTime;
import java.time.Duration;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import static dijkstra.SeatClass.*;
import static dijkstra.tests.AirportCodes.CLE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Flight interface, and the AbstractConnection and SimpleConnection classes
 * Tests ConnectionGroup and FlightSchedule classes
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlightTest {

    public static String flightCode;
    public static String originCode;
    public static String destinationCode;

    public static LocalTime depart;
    public static LocalTime arrival;

    public static Duration originDuration;
    public static Duration destDuration;

    public static Node origin;
    public static Node destination;

    public static EnumMap<SeatClass, Integer> map = new EnumMap<SeatClass, Integer>(SeatClass.class);

    public static Leg leg;
    public static FlightSchedule flightSchedule;
    public static SeatConfiguration seatConfig;
    public static Flight flight;

    public EnumMap<SeatClass, Integer> seats1 = new EnumMap<>(SeatClass.class);
    public EnumMap<SeatClass, Integer> seats2 = new EnumMap<>(SeatClass.class);
    public EnumMap<SeatClass, Integer> seats3 = new EnumMap<>(SeatClass.class);
    public EnumMap<SeatClass, Integer> seats4 = new EnumMap<>(SeatClass.class);
    public EnumMap<SeatClass, Integer> seats3Limited = new EnumMap<>(SeatClass.class);

    public SeatConfiguration config1;
    public SeatConfiguration config2;
    public SeatConfiguration config3;
    public SeatConfiguration config4;


    public static FareClass econFareClass = FareClass.of(3, ECONOMY);
    public static FareClass busnFareClass = FareClass.of(7, BUSINESS);
    public static FareClass busnFareClassLow = FareClass.of(7, BUSINESS);
    public static FareClass premFareClass = FareClass.of(8, PREMIUM_ECONOMY);


    SimpleConnection flight1;
    SimpleConnection flight2;
    Flight flight3;
    Flight flight4;

    @BeforeAll
    void initializeFlights() {
        flightCode = "A112";
        originCode = "CLE";
        destinationCode = "LGA";

        depart = LocalTime.MIN;
        arrival = LocalTime.NOON;

        originDuration = Duration.ofHours(5);
        destDuration = Duration.ofHours(12);

        origin = Node.of(originCode, originDuration);
        destination = Node.of(destinationCode, originDuration);

        leg = Leg.of(origin, destination);

        flightSchedule = FlightSchedule.of(depart, arrival);

        map.put(BUSINESS, 10);
        map.put(ECONOMY, 20);
        seatConfig = SeatConfiguration.of(map);
        flight = SimpleConnection.of(flightCode, leg, flightSchedule, seatConfig);

        seats1.put(ECONOMY, 10);
        seats1.put(BUSINESS, 15);
        seats2.put(ECONOMY, -2);
        seats2.put(BUSINESS, 0);
        config1 = SeatConfiguration.of(seats1);
        config2 = SeatConfiguration.of(seats2);

        seats3.put(ECONOMY, 2);
        seats3.put(BUSINESS, 15);
        seats3.put(PREMIUM_ECONOMY, 8);
        seats4.put(ECONOMY, -2);
        seats4.put(BUSINESS, 0);
        seats4.put(PREMIUM_ECONOMY, 4);

        config3 = SeatConfiguration.of(seats3);
        config4 = SeatConfiguration.of(seats4);


        flight1 = SimpleConnection.of(CLE.toString(), leg, flightSchedule, config1);
        flight2 = SimpleConnection.of(CLE.toString(), leg, flightSchedule, config2);
        flight3 = SimpleConnection.of(CLE.toString(), leg, flightSchedule, config3);
        flight4 = SimpleConnection.of(CLE.toString(), leg, flightSchedule, config4);
    }

    /**
     * Helper routine Returns whether both SeatConfigurations are the same
     */
    boolean seatConfigSame(SeatConfiguration config1, SeatConfiguration config2){
        boolean isSame = true;
        No_Match:
        for(SeatClass seatClass: SeatClass.values()){
            if(config1.seats(seatClass) != config2.seats(seatClass)){
                isSame = false;
                break No_Match;
            }
        }
        return isSame;
    }
    /**
     *      ---   AbstractFlights and SimpleConnection Tests ---
     */
    /**
     *   SimpleConnection build method test - Tests exception handling for build method.
     *   Tests SimpleConnection build method functionality
     */
    @Test
    void testSimpleFlightOf(){

        assertThrows(NullPointerException.class, () -> {
            SimpleConnection.of(null,null,null, null);});
        assertThrows(NullPointerException.class, () -> {
            SimpleConnection.of(originCode,null,null, seatConfig);});
        assertThrows(NullPointerException.class, () -> {
            SimpleConnection.of(originCode,null,null, null);});
        assertThrows(NullPointerException.class, () -> {
            SimpleConnection.of(null,leg,null,null);});
        assertThrows(NullPointerException.class, () -> {
            SimpleConnection.of(null,null,flightSchedule,null);});
        assertThrows(NullPointerException.class, () -> {
            SimpleConnection.of(originCode,leg,null,null);});
        assertThrows(NullPointerException.class, () -> {
            SimpleConnection.of(originCode, null,flightSchedule,null);});
        assertThrows(NullPointerException.class, () -> {
            SimpleConnection.of(null,leg,flightSchedule,null);});

        assertNotNull(SimpleConnection.of(originCode, leg, flightSchedule, seatConfig));

    }

    /**
     * AbstractConnection isLowerCost() method test - Tests exception handling and functionality
     * Further testing is provided in FlightSchedule isLowerCost() test
     */
    @Test
    void testAbstractFlightIsShort() {
        Duration nullDuration = null;
        Duration duration = Duration.ofHours(10);

        assertThrows(NullPointerException.class, () -> {flight.isShort(nullDuration);});
        assertNotNull(flight.isShort(duration));
    }

    /**
     * AbstractConnection hasSeats() method test - Tests exception handling and functionality
     * Further testing is provided is SeatConfiguration hasSeat() test
     */
    @Test
    void testAbstractFlightHasSeats() {

        assertThrows(NullPointerException.class, () -> {flight.hasSeats(null);});
        assertNotNull((flight.hasSeats(premFareClass)));
    }

    /**
     *      --- ConnectionGroup Tests ---
     */

    /**
     * ConnectionGroup build method test - Tests exception handling for build method.
     * Tests ConnectionGroup build method functionality
     */
    @Test
    void testFlightGroupOf(){

        ConnectionGroup connectionGroup = ConnectionGroup.of(origin);
        Assertions.assertThrows(NullPointerException.class, () -> {
            ConnectionGroup.of(null);});
        assertNotNull(ConnectionGroup.of(origin));
    }

    /**
     * ConnectionGroup add() method
     */
    @Test
    void testFlightGroupAdd(){

        Flight nullFlight = null;
        //Flight intentionally created with wrong origin for testing purposes
        Flight badOriginFlight = SimpleConnection.of("Code", Leg.of(Node.of("JFK",Duration.ofHours(3)),destination),flightSchedule,config1);

        ConnectionGroup connectionGroup = ConnectionGroup.of(origin);

        assertThrows(NullPointerException.class, () -> {
            connectionGroup.add(nullFlight);});
        assertThrows(IllegalArgumentException.class, () -> {
            connectionGroup.add(badOriginFlight);});

        assertTrue(connectionGroup.add(flight));

        Set<Flight> flights = new HashSet<>();
        flights.add(flight);
        assertEquals(flights, connectionGroup.connectionsAtOrAfter(flight.departureTime()));

        assertFalse(connectionGroup.add(flight));

    }

    /**
     * ConnectionGroup remove() method
     */
    @Test
    void testFlightGroupRemove() {

        Flight nullFlight = null;
        //Flight intentionally created with wrong origin for testing purposes
        Flight badOriginFlight = SimpleConnection.of("Code", Leg.of(Node.of("JFK",Duration.ofHours(3)),destination),flightSchedule,config1);

        ConnectionGroup connectionGroup = ConnectionGroup.of(origin);

        assertThrows(NullPointerException.class, () -> {
            connectionGroup.add(nullFlight);});
        assertThrows(IllegalArgumentException.class, () -> {
            connectionGroup.add(badOriginFlight);});

        connectionGroup.add(flight);
        connectionGroup.add(flight1);

        assertTrue(connectionGroup.remove(flight));
        assertFalse(connectionGroup.remove(flight));

        Set<Flight> flights = new HashSet<>();
        flights.add(flight1);

        assertEquals(flights, connectionGroup.connectionsAtOrAfter(flight1.departureTime()));

    }

    /**
     * ConnectionGroup connectionsAtOrAfter() test
     */
    @Test
    void testFlightGroupFlightsAtOrAfter() {

        ConnectionGroup connectionGroup = ConnectionGroup.of(Node.of("CLE",Duration.ofHours(1)));

        connectionGroup.add(flight);
        connectionGroup.add(flight1);
        connectionGroup.add(flight2);
        assertNotNull(connectionGroup.connectionsAtOrAfter(flight.departureTime()));

        Set<Flight> flights = new HashSet<>();
        flights.add(flight);
        flights.add(flight1);
        flights.add(flight2);
        assertEquals(flights, connectionGroup.connectionsAtOrAfter(flight.departureTime()));

        assertEquals(new HashSet<>(), connectionGroup.connectionsAtOrAfter(LocalTime.of(20,0)));
    }

    /**
     *      ---- FlightSchedule tests ---
     */
    /**
     * FlightSchedule build method test
     */
    @Test
    void testFlightScheduleOf() {

        assertThrows(NullPointerException.class, () -> { FlightSchedule.of(null, null); });
        assertThrows(NullPointerException.class, () -> { FlightSchedule.of(depart, null); });
        assertThrows(NullPointerException.class, () -> { FlightSchedule.of(null, arrival); });

        assertNotNull(FlightSchedule.of(depart, arrival));
    }

    /**
     * FlightSchedule isLowerCost() test
     */
    @Test
    void testFlightScheduleIsShort() {

        Duration oneHour = Duration.ofHours(1);
        Duration twoHour = Duration.ofHours(2);
        Duration min30 = Duration.ofMinutes(30);

        LocalTime oneHourLoc = LocalTime.of(1, 0);
        LocalTime min30Loc = LocalTime.of(0, 30);
        LocalTime twoHourLoc = LocalTime.of(2, 0);

        FlightSchedule hourFlight = FlightSchedule.of(oneHourLoc, twoHourLoc);

        assertThrows(NullPointerException.class, () -> { hourFlight.isShort(null); });
        assertTrue(hourFlight.isShort(oneHour), "Duration equal to flight");
        assertFalse(hourFlight.isShort(min30), "Duration shorter than flight");
        assertTrue(hourFlight.isShort(twoHour), "Duration longer than flight");
    }

}
