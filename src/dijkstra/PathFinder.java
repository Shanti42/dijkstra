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
        Objects.requireNonNull(nodes, "Received null set of nodes");

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
    public final PathNode bestPath(Node start, Node end, ConnectionType connectionType) {
        //check for null values
        Objects.requireNonNull(start, "PathFinder, bestPath() -> origin null");
        Objects.requireNonNull(end, "PathFinder, bestPath() -> destination null");
        Objects.requireNonNull(connectionType, "PathFinder bestPath() -> connectionType null");

        PathState pathState = PathState.of(nodes, origin, departureTime);

        loop Search_Loop:
        while (!pathState.allReached()) {
            PathNode currentNode = pathState.closestUnreached();

            if(!currentNode.isKnown()) {
                break Search_Loop;
            }

            if(currentNode.equals(end)) {
                return currentNode;
            }

            /*
            * for all available paths from “currentNode” with proper connectionType:
            if that (destination node’s total cost via the path) < (previous cost of the node)
            replace that node with a node that has “currentNode” as it’s “previous”
            * */
            findShortestPathLocal(currentNode, connectionType, pathState);

        }
        //no route found
        return null;
    }

    private void findShortestPathLocal(PathNode currentNode, ConnectionType connectionType, PathState pathState) {
        for (Connection connection : currentNode.availibleNodes(connectionType)) {
            Cost destinationCost = pathState.pathNode(connection.getDestination()).getCost();
            if(connection.cost().compareTo(destinationCost) < 0) {
                pathState.replaceNode(PathNode.of(connection, currentAirportNode));
            }
        }

    }
    /*
    * private void findFastestConnectedFlight(PathNode currentAirportNode, ConnectionType connectionType, PathState pathState) {
        for (Connection connection : currentAirportNode.availableNodes(connectionType)) {
            PathTime destinationArrivalTime = pathState.airportNode(connection.getDestination()).getArrivalTime();
            if (new PathTime(connection.arrivalTime()).compareTo(destinationArrivalTime) < 0) {
                pathState.replaceNode(PathNode.of(connection, currentAirportNode));
            }
        }
    }
    * */
}
