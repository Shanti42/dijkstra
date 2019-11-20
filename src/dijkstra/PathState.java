// CAITLIN DO THIS

package dijkstra;

import java.time.LocalTime;
import java.util.*;

/**
 * Keeps track of the state of the route
 */
final class PathState {

    private Map<Node, PathNode> nodeMap;

    private final NavigableSet<PathNode> unreached;


    private PathState(Set<Node> nodes, Node origin, LocalTime departureTime) {
        unreached = new TreeSet<>();
        nodeMap = new HashMap<>();
        addToList(origin, PathNode.of(origin, new PathTime(departureTime), null));
        for (Node node : nodes) {
            if (!node.equals(origin)) {
                addToList(node, PathNode.of(node));
            }
        }
    }

    private void addToList(Node node, PathNode pathNode) {
        Objects.requireNonNull(node, "Node null");
        Objects.requireNonNull(pathNode, "PathNode null");

        nodeMap.put(node, pathNode);
        unreached.add(pathNode);
    }

    //build method
    static PathState of(Set<Node> nodes, Node origin, LocalTime departureTime) {
        Objects.requireNonNull(nodes, "PathState, of() -> null airport set");
        Objects.requireNonNull(origin, "PathState, of() -> null origin airport");
        Objects.requireNonNull(departureTime, "PathState, of() -> null departure time");
        return new PathState(nodes, origin, departureTime);
    }

    //replaces the route node for the corresponding airport, assumes airport is in the route state and is unreached
    final void replaceNode(PathNode pathNode) {
        Objects.requireNonNull(pathNode, "PathState, replaceNode -> given route node is null");
        Node node = pathNode.getNode();
        assert (airportNode.containsKey(node));
        assert (unreached.contains(airportNode(node)));

        PathNode prevNode = airportNode.replace(node, pathNode);
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

}
