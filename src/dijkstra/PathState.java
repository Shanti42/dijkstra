package dijkstra;

import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;

/**
 * Keeps track of the state of the route
 */
final class PathState {

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Map<Node, PathNode> nodeMap;
    private final NavigableSet<PathNode> unreached;

    private PathState(Set<Node> nodes, Node origin, Cost cost) {
        unreached = new TreeSet<>();
        nodeMap = new HashMap<>();
        addToList(origin, PathNode.of(origin, cost, null));
        for (Node node : nodes) {
            if (!node.equals(origin)) {
                addToList(node, PathNode.of(node));
            }
        }
    }

    //build method
    static PathState of(Set<Node> nodes, Node origin, Cost cost) {
        Objects.requireNonNull(nodes, "PathState, of() -> null nodes set");
        Objects.requireNonNull(origin, "PathState, of() -> null origin");
        Objects.requireNonNull(cost, "PathState, of() -> null cost time");
        try {
            return new PathState(nodes, origin, cost);
        } catch(Exception e) {
            
        }
        return null;
    }

    static PathState of(Set<Node> nodes, Node origin) {
        return PathState.of(nodes, origin, Cost.ZERO);
    }



    private void addToList(Node node, PathNode pathNode) {
        Objects.requireNonNull(node, "Node null");
        Objects.requireNonNull(pathNode, "PathNode null");

        nodeMap.put(node, pathNode);
        unreached.add(pathNode);
    }

    //replaces the route node for the corresponding airport, assumes airport is in the route state and is unreached
    final void replaceNode(PathNode pathNode) {
        Objects.requireNonNull(pathNode, "PathState, replaceNode -> given route node is null");
        Node node = pathNode.getNode();
        assert (nodeMap.containsKey(node));
        assert (unreached.contains(nodeMap.get(node)));

        PathNode prevNode = nodeMap.replace(node, pathNode);
        unreached.remove(prevNode);
        unreached.add(pathNode);
    }

    //returns true if all nodes are reached
    final boolean allReached() {
        return unreached.isEmpty();
    }

    final PathNode closestUnreached() {
        PathNode smallestArrivalTime = unreached.pollFirst();
        if(smallestArrivalTime == null) {
            throw new NoSuchElementException("All Airports have been reached");
        } else if(smallestArrivalTime.getCost().equals(Cost.UNKNOWN)) {
            return null;
        } else {
            return smallestArrivalTime;
        }
    }

    //returns the route node corresponding to the node, assumes the node is in the route state
    final PathNode pathNode(Node node) {
        Objects.requireNonNull(node, "PathState, airportNode -> node is null");
        assert (nodeMap.containsKey(node));
        return nodeMap.get(node);
    }

    public static class TESTHOOK {
        public static void addToList_test(PathState state, Node node, PathNode pathNode) {
            state.addToList(node, pathNode);
        }
    }
}
