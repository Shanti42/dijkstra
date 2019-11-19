package dijkstra;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * Re-routes passengers from an airport to a final destination
 */
public final class PathFinder {

    private final Set<Node> nodes;

    private PathFinder(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public static final PathFinder of(Set<Node> nodes) {
        Objects.requireNonNull(nodes, "Received null Set of Airports");

        return new PathFinder(nodes);
    }

    /**
     * Finds and returns tha last route node in the fastest route
     * from the departure aiprot to final destination
     *
     * @param origin        the departure airport
     * @param destination   the final destination
     * @param departureTime the time of departure
     * @param connectionType     the fareclass of the passenger
     * @return
     */
    public final PathNode route(Node origin, Node destination, LocalTime departureTime, ConnectionType connectionType) {
        //check for null values
        Objects.requireNonNull(origin, "PathFinder, route() -> origin null");
        Objects.requireNonNull(destination, "PathFinder, route() -> destination null");
        Objects.requireNonNull(departureTime, "PathFinder route() -> departureTime null");
        Objects.requireNonNull(connectionType, "PathFinder route() -> connectionType null");

        PathState pathState = PathState.of(nodes, origin, departureTime);
        while (!pathState.allReached()) {
            PathNode currentAirportNode = pathState.closestUnreached();
            if (currentAirportNode.getNode().equals(destination)) {
                return currentAirportNode;
            } else if (currentAirportNode.availableNodes(connectionType).isEmpty()) {
                return null;
            } else {
                findFastestConnectedFlight(currentAirportNode, connectionType, pathState);
            }

        }
        //no route found
        return null;
    }

    // Finds the fastest connected flight and sets is as the next route node
    private void findFastestConnectedFlight(PathNode currentAirportNode, ConnectionType connectionType, PathState pathState) {
        for (Connection connection : currentAirportNode.availableNodes(connectionType)) {
            PathTime destinationArrivalTime = pathState.airportNode(connection.getDestination()).getArrivalTime();
            if (new PathTime(connection.arrivalTime()).compareTo(destinationArrivalTime) < 0) {
                pathState.replaceNode(PathNode.of(connection, currentAirportNode));
            }
        }
    }


}
