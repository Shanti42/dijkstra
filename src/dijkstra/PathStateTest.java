// CAITLIN

package dijkstra;

import org.junit.Test;
import org.junit.Before;
import java.util.*;
import static org.junit.Assert.*;


import java.math.BigInteger;

public class PathStateTest {
    /*
    * To test:
    *
    * PathState of(Set<Node> nodes, Node origin, Cost cost) {
    * PathState of(Set<Node> nodes, Node origin)
    *
    * addToList(Node node, PathNode pathNode)
    * replaceNode(PathNode pathNode)
    * allReached()
    *
    * PathNode closestUnreached()
    * PathNode pathNode(Node node)
     * */

    private Cost<Addable> cost1 = Cost.of(SimpleAddable.of(1));
    private Cost<Addable> cost2 = Cost.of(SimpleAddable.of(2));
    private Cost<Addable> cost3 = Cost.of(SimpleAddable.of(10));
    private Cost<Addable> cost4 = Cost.of(SimpleAddable.of(50));
    private Cost<Addable> cost5 = Cost.of(SimpleAddable.of(11));
    private Cost<Addable> cost6 = Cost.of(SimpleAddable.of(12));

    private Node node1;
    private Node node2;
    private Node node3;

    private Set<Node> states = new HashSet<>();

    private PathState state;

    private ConnectionType type1 = ConnectionType.of("Jetpack");
    private ConnectionType type2 = ConnectionType.of("Eaten by Whale");

    @Before
    public void resetVariables() {
        node1 = Node.of("ABC", cost1);
        node2 = Node.of("XYZ", cost2);
        node3 = Node.of("MNO", cost3);

        states.addAll(Arrays.asList(node1, node2, node3));
        state = PathState.of(states, node1);

    }

    @Test
    public void replaceNode_test() {
        assertNull(state.pathNode(node2).getPrevious());

        PathNode replacement = PathNode.of(node2, cost6, state.pathNode(node1));
        state.replaceNode(replacement);

        assertTrue(replacement.equals(state.pathNode(node2)));
    }

    @Test(expected = AssertionError.class)
    public void replaceNode_test_invalidNode() {
        Node node4 = Node.of("CDE", cost4);
        PathNode replacement = PathNode.of(node4, cost5, state.pathNode(node2));
        state.replaceNode(replacement);
    }

    @Test(expected = AssertionError.class)
    public void replaceNode_test_knownNode() {
        PathNode replacement = PathNode.of(node1, cost6, state.pathNode(node2));

        state.closestUnreached(); // pops origin
        state.replaceNode(replacement);
    }

    @Test
    public void allReached_test() {
        assertFalse(state.allReached());
        state.replaceNode(PathNode.of(node2, cost6, state.pathNode(node1)));
        state.replaceNode(PathNode.of(node3, cost6, state.pathNode(node2)));

        state.closestUnreached(); // pops origin
        assertFalse(state.allReached());

        state.closestUnreached(); // pops node2
        assertFalse(state.allReached());

        state.closestUnreached(); // pops node3
        assertTrue(state.allReached());
    }
}