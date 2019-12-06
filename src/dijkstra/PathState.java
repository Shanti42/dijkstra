// Caitlin -- done (package private, so no javadoc)
// package private because don't need to override

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
     * The constructor for the PathState class
     * 
     * @param nodes        a set of nodes on the path
     * @param origin       the origin of the path
     * @param cost		   total cost of the path
     * @return a new PathState class
     */
    static PathState of(Set<Node> nodes, Node origin, Cost<Addable> cost) {
        Objects.requireNonNull(nodes, "PathState, of() -> null nodes set");
        Objects.requireNonNull(origin, "PathState, of() -> null origin");
        Objects.requireNonNull(cost, "PathState, of() -> null cost time");
        try {
            return new PathState(nodes, origin, cost);
        } catch (Exception e) {
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
     * The constructor for the PathState class with zero cost
     * 
     * @param nodes       a set of nodes on the path
     * @param origin      the origin of the path
     * @return a new PathState class with zero cost
     */
    static PathState of(Set<Node> nodes, Node origin) {
        return PathState.of(nodes, origin, Cost.ZERO);
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
     * Replace the route node for the corresponding node, assumes node is in the route state and is unreached
     * 
     * @param pathNode    input pathNode used to replace
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
     * 
     * @return true if all nodes are reached
     */
    final boolean allReached() {
        return unreached.isEmpty();
    }

    /**
     * 
     * @return the closest pathNode in the unreached set, which has the minimum cost
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
     * 
     * @param node
     * @return the route node corresponding to the node, assumes the node is in the route state
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
