package dijkstra.tests;
import static dijkstra.SeatClass.*;

import dijkstra.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests for the RouteNode, RouteState, RouteTime, and RouteFinder classes
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RouteTest  {


    public Node node1;
    public Node node2;
    public Node node3;
    public Node node4;

    public RouteTime routeTime1;
    public RouteTime routeTime2;
    public RouteTime routeTime3;
    public RouteTime routeUnknown;

    public Leg leg;
    public FlightSchedule flightSchedule;
    public SeatConfiguration seatConfig;
    public Flight flight;

    public Set<Node> nodes = new HashSet<>();

    @BeforeAll
    void initializeRoutes() {
        node1 = Node.of("CLE",Duration.ofHours(5));
        node2 = Node.of("CLE",Duration.ofHours(7));
        node3 = Node.of("CLE",Duration.ofHours(3));
        node4 = Node.of("LGA",Duration.ofHours(1));

        routeTime1 = new RouteTime(LocalTime.of(6,0));
        routeTime2 = new RouteTime(LocalTime.of(5,0));
        routeTime3 = new RouteTime(LocalTime.of(3,0));
        routeUnknown = new RouteTime(null);

        leg = Leg.of(node1, Node.of("LGA", Duration.ofHours(6)));
        flightSchedule = FlightSchedule.of(LocalTime.MIN,LocalTime.NOON);

        seatConfig = SeatConfiguration.of(new EnumMap<SeatClass,Integer>(SeatClass.class));
        seatConfig.setSeats(BUSINESS, 10);
        seatConfig.setSeats(ECONOMY, 20);
        seatConfig.setSeats(PREMIUM_ECONOMY, 5);

        flight = SimpleConnection.of("A1102", leg, flightSchedule, seatConfig);

        nodes.add(node1);
        nodes.add(node4);
    }

    /**
     *      --- RouteTime Tests ---
     */
    /**
     * RouteTime isKnown() method test
     */
    @Test
    void testRouteTimeIsKnown() {
        RouteTime routeKnown = new RouteTime(LocalTime.NOON);

        assertFalse(routeUnknown.isKnown());
        assertTrue(routeKnown.isKnown());
    }

    /**
     * RouteTime plus() method tests
     */
    @Test
    void testRouteTimePlus() {
        RouteTime routeOfFive = new RouteTime(LocalTime.of(5,0));

        Duration durationOne = Duration.ofHours(1);
        Duration durationThree = Duration.ofHours(3);
        Duration durationTen = Duration.ofHours(10);

        RouteTime routeOfSix = new RouteTime(LocalTime.of(6,0));
        RouteTime routeOfEight = new RouteTime(LocalTime.of(8,0));
        RouteTime routeOfFifteen = new RouteTime(LocalTime.of(15,0));

        assertEquals(RouteTime.UNKNOWN,routeUnknown.plus(durationOne));
        assertNotNull(routeOfFive.plus(durationOne));
        assertTrue(routeOfSix.compareTo(routeOfFive.plus(durationOne)) ==0);
        assertTrue(routeOfEight.compareTo(routeOfFive.plus(durationThree)) ==0);
        assertTrue(routeOfFifteen.compareTo(routeOfFive.plus(durationTen)) ==0);

    }

    /**
     * RouteTime compareTo method tests
     */
    @Test
    void testRouteTimeCompareTo() {

        assertTrue(routeTime1.compareTo(routeTime1) == 0); //Compare RouteTime 6 to 6
        assertFalse(routeTime1.compareTo(routeTime2) == 0); //Compare RouteTime 6 to 6
        assertTrue( routeTime1.compareTo(routeTime2) > 0); //Compare RouteTime 6 to 5
        assertTrue(routeTime3.compareTo(routeTime1) < 0); //Compare RouteTime 3 to 6
        assertTrue(routeTime1.compareTo(new RouteTime(null)) <0 ); //Compare RouteTime with route time with null value


    }

    /**
     * RouteNode Tests
     */
    /**
     * Tests for all three of RouteNode's build methods
     */
    @Test
    void testRouteNodeBuildMethods() {

        RouteNode routeNode1 = RouteNode.of(node1, routeTime1, null);
        assertNotNull(routeNode1);
        assertNotNull(RouteNode.of(node2, routeTime2, routeNode1));
        assertNotNull(RouteNode.of(flight,routeNode1));
        assertNotNull(RouteNode.of(node3));

        assertThrows(NullPointerException.class, () -> RouteNode.of(null,routeTime1,null));
        assertThrows(NullPointerException.class, () -> RouteNode.of(node4,null,null));
        assertThrows(NullPointerException.class, () -> RouteNode.of(null ,routeNode1));
        assertThrows(NullPointerException.class, () -> RouteNode.of(null));

    }

    /**
     * RouteNode isArrivalTimeKnown() method
     */
    @Test
    void testRouteNodeIsArrivalTimeKnown() {
        RouteNode knownArrival = RouteNode.of(node1, routeTime1,  null);
        RouteNode unknownArrival = RouteNode.of(node1, RouteTime.UNKNOWN, null);
        assertTrue(knownArrival.isArrivalTimeKnown());
        assertFalse(unknownArrival.isArrivalTimeKnown());
    }

    /**
     * RouteNode departureTime() method
     */
    @Test
    void testRouteNodeDepartureTime() {
        //node1 has a connection time of 5 and arrival time is 6. Returned RouteTime should be 11
        RouteNode routeNode = RouteNode.of(node1, routeTime1, null);

        RouteTime expectedRouteTime = new RouteTime(LocalTime.of(11,0));
        RouteTime computedRouteTime = routeTime1.plus(Duration.ofHours(5));
        assertTrue(expectedRouteTime.compareTo(routeNode.departureTime()) ==0);
        assertTrue(computedRouteTime.compareTo(routeNode.departureTime()) ==0);
    }

    /**
     * RouteNode availableConnections method test
     */
    @Test
    void testRouteNodeAvailableFlights() {

        Node origin = Node.of("CLE",Duration.ofHours(3));
        Node dest1 = Node.of("LGA",Duration.ofHours(5));
        Node dest2 = Node.of("LAX",Duration.ofHours(2));

        Leg leg1 = Leg.of(origin,dest1);
        Leg leg2 = Leg.of(origin, dest2);

        LocalTime depart1 = LocalTime.of(5,0);
        LocalTime depart2 = LocalTime.of(6,0);
        LocalTime arrive1 = LocalTime.of(7,0);
        LocalTime arrive2 = LocalTime.of(8,0);
        FlightSchedule schedule1 = FlightSchedule.of(depart1,arrive1);
        FlightSchedule schedule2 = FlightSchedule.of(depart2,arrive2);

        SeatConfiguration seatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
        seatConfig.setSeats(ECONOMY, 10);
        seatConfig.setSeats(BUSINESS, 20);

        Flight flight1 = SimpleConnection.of("A101",leg1,schedule1,seatConfig);
        Flight flight2 = SimpleConnection.of("B101",leg2,schedule2,seatConfig);

        origin.addConnection(flight1);
        origin.addConnection(flight2);
        RouteNode routeNode = RouteNode.of(origin, new RouteTime(arrive1), null);
        FareClass fareClass = FareClass.of(4, BUSINESS);

        Set<Flight> flightSet = routeNode.getNode().availableConnections(depart1,fareClass);
        //System.out.println(flightSet.isEmpty());
        //System.out.println(flightSet.toString());
        assertTrue(flightSet.size() == 2);

    }

    /**
     * RouteNode compareTo() method test
     */
    @Test
    void testRouteNodeCompareTo() {
        RouteNode routeNode1  = RouteNode.of(node1, routeTime1, null);
        RouteNode routeNode2 = RouteNode.of(node2, routeTime2, routeNode1);
        RouteNode routeNode3 = RouteNode.of(node4, routeTime1, routeNode2);

        assertTrue(routeNode1.compareTo(routeNode1) == 0); //Compare RouteTime 6 to 6
        assertTrue(routeNode1.compareTo(routeNode2) > 0); //Compare RouteTime 6 to 5
        assertTrue(routeNode1.compareTo(routeNode3) < 0); //RouteTimes are the same so compares 'CLE' to 'LGA'

    }

    /**
     * RouteFinder Tests
     */
    /**
     * RouteFinder build method tests
     */
    @Test
    void testRouteFinderOf() {
        assertThrows(NullPointerException.class, () -> RouteFinder.of(null));
        assertNotNull(RouteFinder.of(nodes));
    }

    /**
     * RouteFinder route() method test #1 - Asserts the predicted output using a connected graph
     */
    @Test
    void testRouteFinderRouteConnectedGraph() {
        //Connected Graph
        Node origin = Node.of("CLE", Duration.ofHours(1));
        Node destLGA = Node.of("LGA", Duration.ofHours(1));
        Node destMIA = Node.of("MIA", Duration.ofHours(1));
        Node destLAX = Node.of("LAX", Duration.ofHours(1));

        //Legs of flight from CLE to NY to LA
        Leg leg1 = Leg.of(origin, destLGA);
        Leg leg2 = Leg.of(destLGA, destLAX);

        //Legs of flight from CLE to Miami to LA
        Leg leg3 = Leg.of(origin, destMIA);
        Leg leg4 = Leg.of(destMIA, destLAX);

        //Flight Schedule for CLE to NY to LA
        FlightSchedule schedule1 = FlightSchedule.of(LocalTime.of(3,0), LocalTime.of(4,0)); //1 Hour Flight
        FlightSchedule schedule2 = FlightSchedule.of(LocalTime.of(6,0), LocalTime.of(9,0)); //3 Hours Flight

        //Flight Schedule for CLE to Miami to LA
        FlightSchedule schedule3 = FlightSchedule.of(LocalTime.of(2,0), LocalTime.of(4, 0)); //2 Hour Flight
        FlightSchedule schedule4 = FlightSchedule.of(LocalTime.of(5,0), LocalTime.of(8,0)); //3 Hour Flight

        SeatConfiguration seatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
        seatConfig.setSeats(ECONOMY, 19);
        FareClass fareClass = FareClass.of(4, ECONOMY);

        Flight flight1 = SimpleConnection.of("A101", leg1, schedule1, seatConfig);
        Flight flight2 = SimpleConnection.of("B101", leg2, schedule2, seatConfig);
        Flight flight3 = SimpleConnection.of("A102", leg3, schedule3, seatConfig);
        Flight flight4 = SimpleConnection.of("C101", leg4, schedule4, seatConfig);

        origin.addConnection(flight1);
        origin.addConnection(flight3);

        destLGA.addConnection(flight2);
        destMIA.addConnection(flight4);

        Set<Node> nodes = new HashSet<>();
        nodes.add(origin);
        nodes.add(destLGA);
        nodes.add(destMIA);
        nodes.add(destLAX);

        RouteFinder routeFinder = RouteFinder.of(nodes);

        assertEquals(destLAX.getID(), routeFinder.route(origin, destLAX, LocalTime.of(1,0), fareClass).getNode().getID());
        assertEquals(destLAX.getID(), routeFinder.route(origin, destLAX, LocalTime.of(2,0), fareClass).getNode().getID());
        assertEquals(destLAX.getID(), routeFinder.route(origin, destLAX, LocalTime.of(0,0), fareClass).getNode().getID());

        //Should return null because departureTime is 1 minute later then the last available departureTime
        assertNull(routeFinder.route(origin, destLAX, LocalTime.of(2,1), fareClass));

    }

    /**
     * RouteFinder route() method test #2 - Test using a disconnected graph
     */
    @Test
    void testRouteFinderRouteDisconnectedGraph() {
        //Connected Graph
        Node origin = Node.of("CLE", Duration.ofHours(1));
        Node destLGA = Node.of("LGA", Duration.ofHours(1));
        Node destLAX = Node.of("LAX", Duration.ofHours(1));
        Node destMIA = Node.of("MIA", Duration.ofHours(1));

        //Legs of flight from CLE to NY to LA
        Leg leg1 = Leg.of(origin, destLGA);
        Leg leg2 = Leg.of(destLGA, destLAX);

        //Legs of flight from CLE to Miami
        Leg leg3 = Leg.of(origin, destMIA);

        //Flight Schedule for CLE to NY to LA
        FlightSchedule schedule1 = FlightSchedule.of(LocalTime.of(2,0), LocalTime.of(3,0)); //1 Hour Flight
        FlightSchedule schedule2 = FlightSchedule.of(LocalTime.of(5,0), LocalTime.of(8,0)); //3 Hours Flight

        //Flight Schedule for CLE to Miami
        FlightSchedule schedule3 = FlightSchedule.of(LocalTime.of(2,0), LocalTime.of(4, 0)); //2 Hour Flight

        SeatConfiguration seatConfig = SeatConfiguration.of(new EnumMap<SeatClass, Integer>(SeatClass.class));
        seatConfig.setSeats(ECONOMY, 19);
        FareClass fareClass = FareClass.of(4, ECONOMY);

        Flight flight1 = SimpleConnection.of("A101", leg1, schedule1, seatConfig);
        Flight flight2 = SimpleConnection.of("B101", leg2, schedule2, seatConfig);
        Flight flight3 = SimpleConnection.of("A102", leg3, schedule3, seatConfig);

        origin.addConnection(flight1);
        origin.addConnection(flight3);

        destLGA.addConnection(flight2);

        Set<Node> nodes = new HashSet<>();
        nodes.add(origin);
        nodes.add(destLAX);
        nodes.add(destLGA);
        nodes.add(destMIA);

        RouteFinder routeFinder = RouteFinder.of(nodes);

        //No shortest path due to disconnected graph
        assertNull(routeFinder.route(origin, destLAX, LocalTime.of(2,0), fareClass));
        assertNull(routeFinder.route(origin, destLAX, LocalTime.of(1,0), fareClass));
        assertNull(routeFinder.route(origin, destLAX, LocalTime.of(0,0), fareClass));

        assertNull(routeFinder.route(origin, destMIA, LocalTime.of(2,0), fareClass));
        assertEquals(destMIA.getID(), routeFinder.route(origin, destMIA, LocalTime.of(1,0), fareClass).getNode().getID());
        assertEquals(destMIA.getID(), routeFinder.route(origin, destMIA, LocalTime.of(0,0), fareClass).getNode().getID());
    }


}
