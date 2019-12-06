package dijkstra;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.*;

public class PathStateTest {

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

        PathNode replacement = PathNode.of(node2, state.pathNode(node1));
        state.replaceNode(replacement);

        assertTrue(replacement.equals(state.pathNode(node2)));
    }

    @Test(expected = AssertionError.class)
    public void replaceNode_test_invalidNode() {
        Node node4 = Node.of("CDE", cost4);
        PathNode replacement = PathNode.of(node4, state.pathNode(node2));
        state.replaceNode(replacement);
    }

    @Test(expected = AssertionError.class)
    public void replaceNode_test_knownNode() {
        PathNode replacement = PathNode.of(node1, state.pathNode(node2));

        state.closestUnreached(); // pops origin
        state.replaceNode(replacement);
    }

    @Test
    public void allReached_test() {
        assertFalse(state.allReached());
        state.replaceNode(PathNode.of(node2, state.pathNode(node1)));
        state.replaceNode(PathNode.of(node3, state.pathNode(node2)));

        state.closestUnreached(); // pops origin
        assertFalse(state.allReached());

        state.closestUnreached(); // pops node2
        assertFalse(state.allReached());

        state.closestUnreached(); // pops node3
        assertTrue(state.allReached());
    }

    @Test
    public void closestUnreached_test() {
        PathNode replace1 = PathNode.of(node2, state.pathNode(node1));
        PathNode replace2 = PathNode.of(node3, state.pathNode(node2));

        state.replaceNode(replace1);
        state.replaceNode(replace2);

        assertEquals(state.pathNode(node1), state.closestUnreached());
        assertEquals(replace1, state.closestUnreached());
        assertEquals(replace2, state.closestUnreached());
    }

    @Test(expected = NoSuchElementException.class)
    public void closestUnreached_test_tooMany() {
        PathNode replace1 = PathNode.of(node2, state.pathNode(node1));
        PathNode replace2 = PathNode.of(node3, state.pathNode(node2));

        state.replaceNode(replace1);
        state.replaceNode(replace2);

        assertEquals(state.pathNode(node1), state.closestUnreached());
        state.closestUnreached();
        state.closestUnreached();
        state.closestUnreached();
        state.closestUnreached();
    }

    @Test
    public void closestUnreached_test_notReachable() {
        state.replaceNode(PathNode.of(node2, state.pathNode(node1)));
        state.closestUnreached(); // node1
        state.closestUnreached(); // node2
        assertNull(state.closestUnreached()); // node3 (unreachable, no path)
    }

    @Test
    public void pathNode_test() {
        PathNode expected = PathNode.of(node2, null);
        assertEquals(expected, state.pathNode(node2));
    }

    @Test(expected = NullPointerException.class)
    public void pathNode_null() {
        state.pathNode(null);
    }

    @Test(expected = AssertionError.class)
    public void pathNode_invalidArgument() {
        Node node4 = Node.of("Not here", cost1);
        state.pathNode(node4);
    }

    @Test
    public void addToList_test() {
        Node node4 = Node.of("123", cost2);
        PathNode path4 = PathNode.of(node4, null);
        PathState.TESTHOOK.addToList_test(state, node4, path4);
        assertEquals(path4, state.pathNode(node4));
    }

    @Test(expected = NullPointerException.class)
    public void assToList_test_null_pathNode() {
        Node node4 = Node.of("123", cost2);
        PathState.TESTHOOK.addToList_test(state, node4, null);
    }

    @Test(expected = NullPointerException.class)
    public void assToList_test_null_node() {
        Node node4 = Node.of("123", cost2);
        PathNode path4 = PathNode.of(node4, null);
        PathState.TESTHOOK.addToList_test(state, null, path4);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionInOf() {
        Node node4 = Node.of("123", cost2);
        Set<Node> nodes = new HashSet<>();
        nodes.add(null);
        PathState.of(nodes, node4, cost1);
    }

}