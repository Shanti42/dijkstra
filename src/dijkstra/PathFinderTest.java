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
import java.util.stream.Collectors;
import java.util.List;

public class PathFinderTest  {

    private Node node0;
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
        node0 = Node.of("123",Cost.ZERO);
        node1 = Node.of("ABC",Cost.ZERO);
        node2 = Node.of("DEF",Cost.ZERO);
        node3 = Node.of("GHI",Cost.ZERO);
        node4 = Node.of("JKL",Cost.ZERO);

        Connection con0_2 = SimpleConnection.of(node0, node2, cost4);
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

        PathNode result = finder.bestPath(node1, node4, null);
        assertTrue(result != null);
        //result.totalCost();
        //System.out.println(result.totalCost().cost());
        assertEquals(result.getNode(), node4);
    }

    @Test
    public void testRouteFinderDisconnectedGraph() {
        Node node5 = Node.of("QWER", cost3);
        nodes.add(node5);
        PathFinder pathFinderDisconnected = PathFinder.of(nodes);

        PathNode result = pathFinderDisconnected.bestPath(node1, node5);
        assertNull(result);
    }

    @Test
    public void testFindShortestPathLocal() {
        PathState state = PathState.of(nodes, node1);
        assertNull(state.pathNode(node2).getPrevious());
        PathFinder.TESTHOOK.findShortestPathLocal_test(finder, PathNode.of(node1, Cost.ZERO), null, state);
        assertNotNull(state.pathNode(node2).getPrevious());

        PathNode prev = state.pathNode(node2);

        PathFinder.TESTHOOK.findShortestPathLocal_test(finder, PathNode.of(node0, Cost.ZERO), null, state);
        assertEquals(prev, state.pathNode(node2));
    }

    @Test
    public void stressTest() {

        int NUMBER_OF_NODES = 100;
        int MAX_WEIGHT = 20;
        int RUN_COUNT = 1000;

        for(int j = 0; j < RUN_COUNT; j++) {
            Set<Node> my_nodes = new HashSet<>();
            for(int i = 0; i < NUMBER_OF_NODES; i++) {
                String str = i + "!";
                Cost c = Cost.of(SimpleAddable.of((int) Math.floor(Math.random() * MAX_WEIGHT)));

                my_nodes.add(Node.of(str, c));
            }

            PathFinder finder = PathFinder.of(my_nodes);

            int start = (int) Math.floor(Math.random() * NUMBER_OF_NODES);
            int end = (int) Math.floor(Math.random() * NUMBER_OF_NODES);
            List<Node> my_nodes_list = my_nodes.stream().collect(Collectors.toList());

            if(start != end) {
                finder.bestPath(my_nodes_list.get(start), my_nodes_list.get(end));
            }
        }
    }


}
