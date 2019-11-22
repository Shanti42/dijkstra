// CAITLIN DO THIS!!!!!!

package dijkstra;//.tests;

import dijkstra.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


//import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class PathFinderTest  {

    private Node node1;
    private Node node2;
    private Node node3;
    private Node node4;

    private Cost cost1 = Cost.of(SimpleAddable.of(1));
    private Cost cost2 = Cost.of(SimpleAddable.of(2));
    private Cost cost3 = Cost.of(SimpleAddable.of(3));
    private Cost cost4 = Cost.of(SimpleAddable.of(4));

    public PathFinder finder;

    public Set<Node> nodes = new HashSet<>();

    @Before
    public void initializeRoutes() {
        node1 = Node.of("ABC",Cost.ZERO);
        node2 = Node.of("DEF",Cost.ZERO);
        node3 = Node.of("GHI",Cost.ZERO);
        node4 = Node.of("JKL",Cost.ZERO);

        Connection con1_2 = SimpleConnection.of(node1, node2, cost1);
        Connection con2_3 = SimpleConnection.of(node2, node3, cost1);
        Connection con3_4 = SimpleConnection.of(node3, node4, cost1);
        Connection con1_4 = SimpleConnection.of(node1, node4, cost2);

        nodes.addAll(Arrays.asList(node1, node2, node3, node4));
        finder = PathFinder.of(nodes);
    }

    /**
     * PathFinder Tests
     */
    /**
     * PathFinder build method tests
     * */

    @Test
    public void testRouteFinderOf_valid() {
        PathFinder path = PathFinder.of(nodes);
        assertEquals(PathFinder.TESTHOOK.getNodes_test(path), nodes);
    }

    @Test(expected = NullPointerException.class)
    public void testRouteFinderOf_null() {
        PathFinder.of(null);
    }

    @Test(expected = NullPointerException.class)
    public void testbestPath_null1() {
        finder.bestPath(null, node2);
    }

    @Test(expected = NullPointerException.class)
    public void testbestPath_null2() {
        finder.bestPath(node1, null);
    }

    @Test
    public void testRouteFinderRouteConnectedGraph() {
        //Connected Graph

        PathNode result = finder.bestPath(node1, node1, null);
        result.getTotalCost();
        //assertEquals(result.getTotalCost(), Cost.ZERO);

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

*/
}
