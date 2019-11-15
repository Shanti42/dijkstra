package dijkstra;

import java.time.LocalTime;
import java.util.*;

/**
 * Keeps track of the state of the route
 */
final class PathState {

    private Map<Node, RouteNode> airportNode;

    private final NavigableSet<RouteNode> unreached;


    private PathState(Set<Node> nodes, Node origin, LocalTime departureTime) {
        unreached = new TreeSet<>();
        airportNode = new HashMap<>();
        addToList(origin, RouteNode.of(origin, new RouteTime(departureTime), null));
        for (Node node : nodes) {
            if (!node.equals(origin)) {
                addToList(node, RouteNode.of(node));
            }
        }
    }

    private void addToList(Node node, RouteNode routeNode) {
        Objects.requireNonNull(node, "Node null");
        Objects.requireNonNull(routeNode, "RouteNode null");
        airportNode.put(node, routeNode);
        unreached.add(routeNode);
    }

    //build method
    static PathState of(Set<Node> nodes, Node origin, LocalTime departureTime) {
        Objects.requireNonNull(nodes, "PathState, of() -> null airport set");
        Objects.requireNonNull(origin, "PathState, of() -> null origin airport");
        Objects.requireNonNull(departureTime, "PathState, of() -> null departure time");
        return new PathState(nodes, origin, departureTime);
    }

    //replaces the route node for the corresponding airport, assumes airport is in the route state and is unreached
    final void replaceNode(RouteNode routeNode) {
        Objects.requireNonNull(routeNode, "PathState, replaceNode -> given route node is null");
        Node node = routeNode.getNode();
        assert (airportNode.containsKey(node));
        assert (unreached.contains(airportNode(node)));

        RouteNode prevNode = airportNode.replace(node, routeNode);
        unreached.remove(prevNode);
        unreached.add(routeNode);
    }

    //returns true if all nodes are reached
    final boolean allReached() {
        return unreached.isEmpty();
    }

    final RouteNode closestUnreached() {
        RouteNode smallestArrivalTime = unreached.pollFirst();
        if(smallestArrivalTime == null) {
            throw new NoSuchElementException("All Airports have been reached");
        } else {
            return smallestArrivalTime;
        }
    }

    //returns the route node corresponding to the node, assumes the node is in the route state
    final RouteNode airportNode(Node node) {
        Objects.requireNonNull(node, "PathState, airportNode -> node is null");
        assert (airportNode.containsKey(node));
        return airportNode.get(node);
    }

}
