package dijkstra;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PathNodeTest {
    private Node bob = Node.of("BOB", Cost.of(SimpleAddable.of(BigInteger.valueOf(3))));
    private Node susan = Node.of("Susan", Cost.of(SimpleAddable.of(BigInteger.valueOf(3))));
    private Cost small = Cost.of(SimpleAddable.of(BigInteger.valueOf(0)));
    private ConnectionType bus = ConnectionType.of("BUS");

    private Cost<Addable> cost1 = Cost.of(SimpleAddable.of(BigInteger.valueOf(1)));
    private Cost<Addable> cost2 = Cost.of(SimpleAddable.of(BigInteger.valueOf(2)));
    private Cost<Addable> cost3 = Cost.of(SimpleAddable.of(10));
    private Cost<Addable> cost4 = Cost.of(SimpleAddable.of(50));
    private Cost<Addable> cost5 = Cost.of(SimpleAddable.of(11));
    private Cost<Addable> cost6 = Cost.of(SimpleAddable.of(12));

    private Node node1;
    private Node node2;
    private Node node3;
    private Node node4_cost1;

    private PathNode pathNode1;
    private PathNode pathNode2;
    private PathNode pathNode3;

    private ConnectionType type1 = ConnectionType.of("Jetpack");
    private ConnectionType type2 = ConnectionType.of("Eaten by Whale");

    private Node unknownNode = Node.of("AB", Cost.UNKNOWN);
    private PathNode unknownPathNode = PathNode.of(unknownNode);

    PathNode bob_pathNode = PathNode.of(bob);
    Connection connection = SimpleConnection.of(bob, susan, small, bus);;

    @Before
    public void resetValues() {
        node1 = Node.of("ABC", cost1);
        node4_cost1 = Node.of("ABC", cost1);
        node2 = Node.of("XYZ", cost2);
        node3 = Node.of("MNO", cost3);

        pathNode1 = PathNode.of(node1);
        pathNode2 = PathNode.of(node2, pathNode1);
        pathNode3 = PathNode.of(node3, pathNode2);
    }

    @Test
    public void getNode_Test() {
        assertEquals(pathNode1.getNode(), node1);
    }

    @Test
    public void getCost_Test() {
        assertEquals(pathNode1.getCost(), cost1);
    }

    @Test
    public void getPrevious_Test() {
        assertNull(pathNode1.getPrevious());
        assertEquals(pathNode2.getPrevious(), pathNode1);
        assertEquals(pathNode3.getPrevious(), pathNode2);
    }

    @Test
    public void availableNodes_Test() {
        // testing no connection types
        Set<Connection> testSet = new HashSet<>();
        assertEquals(testSet, pathNode1.availableConnections());

        Connection connection1_2 = SimpleConnection.of(node1, node2, cost4);
        assertEquals(testSet, pathNode2.availableConnections());
        testSet.add(connection1_2);
        assertEquals(testSet, pathNode1.availableConnections());

        Connection connection1_3 = SimpleConnection.of(node1, node3, cost5);
        testSet.add(connection1_3);
        assertEquals(testSet, pathNode1.availableConnections());

        Connection connection2_3 = SimpleConnection.of(node2, node3, cost6);
        Set<Connection> testSet2 = new HashSet<>();
        testSet2.add(connection2_3);
        assertEquals(testSet2, pathNode2.availableConnections());
    }

    @Test
    public void availibleNodes_Test_ConnectionTypes() {
        Set<Connection> testSet1 = new HashSet<>();
        Set<Connection> testSet2 = new HashSet<>();
        Set<Connection> testSet3 = new HashSet<>();

        Connection connection1_2 = SimpleConnection.of(node1, node2, cost1, type1);
        Connection connection1_3 = SimpleConnection.of(node1, node3, cost1, type2);

        testSet1.add(connection1_2);
        testSet2.add(connection1_3);
        testSet3.add(connection1_2);
        testSet3.add(connection1_3);

        assertEquals(testSet1, pathNode1.availableConnections(type1));
        assertEquals(testSet2, pathNode1.availableConnections(type2));
        assertEquals(testSet3, pathNode1.availableConnections());
        assertEquals(pathNode1.availableConnections(), pathNode1.availableConnections(null));
    }

    @Test(expected = AssertionError.class)
    public void availableNodes_Test_CostUnknown() {
        unknownPathNode.availableConnections();
    }

    @Test(expected = AssertionError.class)
    public void availableNodes_Test_CostUnknown_ConnectionType() {
        unknownPathNode.availableConnections(type1);
    }

    @Test
    public void isKnown_Test() {
        assertFalse(unknownPathNode.isKnown());
        assertTrue(pathNode1.isKnown());
    }

    @Test
    public void compareTo_Test() {
        assertTrue(pathNode1.compareTo(pathNode2) < 0);
        assertTrue(pathNode2.compareTo(pathNode1) > 0);
        assertTrue(pathNode1.compareTo(pathNode1) == 0);
        assertTrue(unknownPathNode.compareTo(unknownPathNode) == 0);
        assertTrue(pathNode1.compareTo(unknownPathNode) < 0);
        assertTrue(unknownPathNode.compareTo(pathNode1) > 0);

        PathNode sameCost1 = PathNode.of(node1);
        PathNode sameCost2 = PathNode.of(node4_cost1);
        assertTrue(sameCost1.compareTo(sameCost2) == 0);
    }

    @Test(expected = NullPointerException.class)
    public void compareTo_Test_null() {
        pathNode1.compareTo(null);
    }

    @Test
    public void testToString() {
        assertEquals(pathNode1.toString(), node1.getID());
    }

    @Test(expected = NullPointerException.class)
    public void testOfNullConnection() {
        PathNode.of(null);
    }

    @Test
    public void testOf() {
        PathNode path = PathNode.of(connection, bob_pathNode);
        assertEquals(path.getCost(), connection.getCost().plus
                (connection.getOrigin().getNodeCost().plus(connection.getDestination().getNodeCost())));
    }


}