// CAITLIN DO THIS!!!!!!
/*
package dijkstra;//.tests;

import dijkstra.*;
import org.junit.Before;
import org.junit.Test;
//import org.junit.TestInstance;

//import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests for the PathNode, RouteState, PathTime, and PathFinder classes

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RouteTest  {


    public Node node1;
    public Node node2;
    public Node node3;
    public Node node4;

    public PathTime pathTime1;
    public PathTime pathTime2;
    public PathTime pathTime3;
    public PathTime routeUnknown;

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

        pathTime1 = new PathTime(LocalTime.of(6,0));
        pathTime2 = new PathTime(LocalTime.of(5,0));
        pathTime3 = new PathTime(LocalTime.of(3,0));
        routeUnknown = new PathTime(null);

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
     *      --- PathTime Tests ---
     */
    /**
     * PathTime isKnown() method test

    @Test
    void testRouteTimeIsKnown() {
        PathTime routeKnown = new PathTime(LocalTime.NOON);

        assertFalse(routeUnknown.isKnown());
        assertTrue(routeKnown.isKnown());
    }

    /**
     * PathTime plus() method tests

    @Test
    void testRouteTimePlus() {
        PathTime routeOfFive = new PathTime(LocalTime.of(5,0));

        Duration durationOne = Duration.ofHours(1);
        Duration durationThree = Duration.ofHours(3);
        Duration durationTen = Duration.ofHours(10);

        PathTime routeOfSix = new PathTime(LocalTime.of(6,0));
        PathTime routeOfEight = new PathTime(LocalTime.of(8,0));
        PathTime routeOfFifteen = new PathTime(LocalTime.of(15,0));

        assertEquals(PathTime.UNKNOWN,routeUnknown.plus(durationOne));
        assertNotNull(routeOfFive.plus(durationOne));
        assertTrue(routeOfSix.compareTo(routeOfFive.plus(durationOne)) ==0);
        assertTrue(routeOfEight.compareTo(routeOfFive.plus(durationThree)) ==0);
        assertTrue(routeOfFifteen.compareTo(routeOfFive.plus(durationTen)) ==0);

    }

    /**
     * PathTime compareTo method tests

    @Test
    void testRouteTimeCompareTo() {

        assertTrue(pathTime1.compareTo(pathTime1) == 0); //Compare PathTime 6 to 6
        assertFalse(pathTime1.compareTo(pathTime2) == 0); //Compare PathTime 6 to 6
        assertTrue( pathTime1.compareTo(pathTime2) > 0); //Compare PathTime 6 to 5
        assertTrue(pathTime3.compareTo(pathTime1) < 0); //Compare PathTime 3 to 6
        assertTrue(pathTime1.compareTo(new PathTime(null)) <0 ); //Compare PathTime with route time with null value


    }

    /**
     * PathNode Tests
     */
    /**
     * Tests for all three of PathNode's build methods

    @Test
    void testRouteNodeBuildMethods() {

        PathNode pathNode1 = PathNode.of(node1, pathTime1, null);
        assertNotNull(pathNode1);
        assertNotNull(PathNode.of(node2, pathTime2, pathNode1));
        assertNotNull(PathNode.of(flight, pathNode1));
        assertNotNull(PathNode.of(node3));

        assertThrows(NullPointerException.class, () -> PathNode.of(null, pathTime1,null));
        assertThrows(NullPointerException.class, () -> PathNode.of(node4,null,null));
        assertThrows(NullPointerException.class, () -> PathNode.of(null , pathNode1));
        assertThrows(NullPointerException.class, () -> PathNode.of(null));

    }

    /**
     * PathNode isArrivalTimeKnown() method

    @Test
    void testRouteNodeIsArrivalTimeKnown() {
        PathNode knownArrival = PathNode.of(node1, pathTime1,  null);
        PathNode unknownArrival = PathNode.of(node1, PathTime.UNKNOWN, null);
        assertTrue(knownArrival.isArrivalTimeKnown());
        assertFalse(unknownArrival.isArrivalTimeKnown());
    }

    /**
     * PathNode departureTime() method

    @Test
    void testRouteNodeDepartureTime() {
        //node1 has a connection time of 5 and arrival time is 6. Returned PathTime should be 11
        PathNode pathNode = PathNode.of(node1, pathTime1, null);

        PathTime expectedPathTime = new PathTime(LocalTime.of(11,0));
        PathTime computedPathTime = pathTime1.plus(Duration.ofHours(5));
        assertTrue(expectedPathTime.compareTo(pathNode.departureTime()) ==0);
        assertTrue(computedPathTime.compareTo(pathNode.departureTime()) ==0);
    }

    /**
     * PathNode availableConnections method test

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
        PathNode pathNode = PathNode.of(origin, new PathTime(arrive1), null);
        FareClass fareClass = FareClass.of(4, BUSINESS);

        Set<Flight> flightSet = pathNode.getNode().availableConnections(depart1,fareClass);
        //System.out.println(flightSet.isEmpty());
        //System.out.println(flightSet.toString());
        assertTrue(flightSet.size() == 2);

    }

    /**
     * PathNode compareTo() method test

    @Test
    void testRouteNodeCompareTo() {
        PathNode pathNode1 = PathNode.of(node1, pathTime1, null);
        PathNode pathNode2 = PathNode.of(node2, pathTime2, pathNode1);
        PathNode pathNode3 = PathNode.of(node4, pathTime1, pathNode2);

        assertTrue(pathNode1.compareTo(pathNode1) == 0); //Compare PathTime 6 to 6
        assertTrue(pathNode1.compareTo(pathNode2) > 0); //Compare PathTime 6 to 5
        assertTrue(pathNode1.compareTo(pathNode3) < 0); //RouteTimes are the same so compares 'CLE' to 'LGA'

    }

    /**
     * PathFinder Tests
     */
    /**
     * PathFinder build method tests

    @Test
    void testRouteFinderOf() {
        assertThrows(NullPointerException.class, () -> PathFinder.of(null));
        assertNotNull(PathFinder.of(nodes));
    }

    /**
     * PathFinder route() method test #1 - Asserts the predicted output using a connected graph

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

        PathFinder pathFinder = PathFinder.of(nodes);

        assertEquals(destLAX.getID(), pathFinder.route(origin, destLAX, LocalTime.of(1,0), fareClass).getNode().getID());
        assertEquals(destLAX.getID(), pathFinder.route(origin, destLAX, LocalTime.of(2,0), fareClass).getNode().getID());
        assertEquals(destLAX.getID(), pathFinder.route(origin, destLAX, LocalTime.of(0,0), fareClass).getNode().getID());

        //Should return null because departureTime is 1 minute later then the last available departureTime
        assertNull(pathFinder.route(origin, destLAX, LocalTime.of(2,1), fareClass));

    }

    /**
     * PathFinder route() method test #2 - Test using a disconnected graph

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

        PathFinder pathFinder = PathFinder.of(nodes);

        //No shortest path due to disconnected graph
        assertNull(pathFinder.route(origin, destLAX, LocalTime.of(2,0), fareClass));
        assertNull(pathFinder.route(origin, destLAX, LocalTime.of(1,0), fareClass));
        assertNull(pathFinder.route(origin, destLAX, LocalTime.of(0,0), fareClass));

        assertNull(pathFinder.route(origin, destMIA, LocalTime.of(2,0), fareClass));
        assertEquals(destMIA.getID(), pathFinder.route(origin, destMIA, LocalTime.of(1,0), fareClass).getNode().getID());
        assertEquals(destMIA.getID(), pathFinder.route(origin, destMIA, LocalTime.of(0,0), fareClass).getNode().getID());
    }


}
*/