// Caitlin -- done!

package dijkstra;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that represents a connected graph of possible paths starting
 * at an origin point.
 *
 * If the graph/list of nodes are not fully connected, then it won't contain connections
 * to all points on the graph
 */
final class PathState {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Map<Node, PathNode> nodeMap;
    private final NavigableSet<PathNode> unreached;

    private PathState(Set<Node> nodes, Node origin, Cost<Addable> cost) {
        unreached = new TreeSet<>();
        nodeMap = new HashMap<>();
        addToList(origin, PathNode.of(origin));
        for (Node node : nodes) {
            if (!node.equals(origin)) {
                addToList(node, PathNode.of(node));
            }
        }
    }

    /**
     * Builder method that takes in a list of nodes in the graph, a starting Node, and any cost just to reach/start
     * at that node
     *
     * @param nodes the set of nodes in the graph
     * @param origin the node that the graph "starts" at
     * @param cost the cost (if any) associated with starting at that point
     *
     * @return a new PathState with the associated nodes, origin, and cost
     *
     * @throws NullPointerException if nodes, origin, or cost are null (cost can be zero, but not null/unknown)
     */
    static PathState of(Set<Node> nodes, Node origin, Cost<Addable> cost) {
        Objects.requireNonNull(nodes, "PathState, of() -> null nodes set");
        Objects.requireNonNull(origin, "PathState, of() -> null origin");
        Objects.requireNonNull(cost, "PathState, of() -> null cost time");
        try {
            return new PathState(nodes, origin, cost);
        } catch (RuntimeException e) {
            if (e instanceof NullPointerException) {
                LOGGER.log(Level.SEVERE, "Null pointer exception found when generating PathState.");
            }
            if (e instanceof IllegalArgumentException) {
                LOGGER.log(Level.SEVERE, "Illegal argument exception found when generating PathState.");
            }

            throw new IllegalStateException("Error when creating PathState");
        }
    }

    /**
     * Builder method that takes in a list of nodes in the graph and a starting Node
     *
     * @param nodes the set of nodes in the graph
     * @param origin the node that the graph "starts" at
     *
     * @return a new PathState with the associated nodes, origin, and the same cost as the origin node
     */
    static PathState of(Set<Node> nodes, Node origin) {
        return PathState.of(nodes, origin, origin.getNodeCost());
    }

    /**
     * Add a node and a pathNode to the PathState
     * Put the input node to the nodeMap
     * Add the input pathNode to the unreached set
     *
     * @param node
     * @param pathNode
     */
    private void addToList(Node node, PathNode pathNode) {
        Objects.requireNonNull(node, "Node null");
        Objects.requireNonNull(pathNode, "PathNode null");

        nodeMap.put(node, pathNode);
        unreached.add(pathNode);
    }

    /**
     * Replaces the route node for the corresponding node, assumes node is in the route state and is unreached.
     *
     * @param pathNode the node to replace
     *
     * @throws NullPointerException if the input pathNode is null
     * @throws AssertionError if the node associated with PathNode isn't in PathState, or the node is already reached
     *
     */
    final void replaceNode(PathNode pathNode) {
        Objects.requireNonNull(pathNode, "PathState, replaceNode -> given route node is null");
        Node node = pathNode.getNode();
        assert (nodeMap.containsKey(node));
        assert (unreached.contains(nodeMap.get(node)));

        PathNode prevNode = nodeMap.replace(node, pathNode);
        unreached.remove(prevNode);
        unreached.add(pathNode);
    }

    /**
     * Returns true if all nodes are reached, and false otherwise.
     *
     * @return boolean that is true if all nodes are reached, and false otherwise.
     */
    final boolean allReached() {
        return unreached.isEmpty();
    }

    /**
     * Returns the closest unreached point on this graph, if any.
     *
     * @return a PathNode that represents the closest unreached point on this graph.
     * If there are no more reachable nodes (disconnected graph), then it returns null.
     *
     * @throws NoSuchElementException if all nodes are reached
     */
    final PathNode closestUnreached() {
        PathNode smallestArrivalTime = unreached.pollFirst();
        if (smallestArrivalTime == null) {
            LOGGER.log(Level.SEVERE, "Searching for unreached node in PathState when all nodes have been reached");
            throw new NoSuchElementException("All Nodes have been reached");
        } else if (!smallestArrivalTime.isKnown()) {
            return null;
        } else {
            return smallestArrivalTime;
        }
    }

    /**
     * Gets the PathNode on this graph associated with the input node
     *
     * @param node the node that we want to find
     *
     * @return the route node corresponding to the node, assumes the node is in the route state
     *
     * @throws NullPointerException if node is null
     * @throws AssertionError if this PathState doesn't contain a node that matches the input
     */
    final PathNode pathNode(Node node) {
        Objects.requireNonNull(node, "PathState, pathNode -> node is null");
        assert (nodeMap.containsKey(node));
        return nodeMap.get(node);
    }

    static class TESTHOOK {
        public static void addToList_test(PathState state, Node node, PathNode pathNode) {
            state.addToList(node, pathNode);
        }
    }
}
