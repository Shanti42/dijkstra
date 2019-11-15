package dijkstra;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * Re-routes passengers from an airport to a final destination
 */
public final class RouteFinder {

    private final Set<Node> nodes;

    private RouteFinder(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public static final RouteFinder of(Set<Node> nodes) {
        Objects.requireNonNull(nodes, "Received null Set of Airports");

        return new RouteFinder(nodes);
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
    public final RouteNode route(Node origin, Node destination, LocalTime departureTime, ConnectionType connectionType) {
        //check for null values
        Objects.requireNonNull(origin, "RouteFinder, route() -> origin null");
        Objects.requireNonNull(destination, "RouteFinder, route() -> destination null");
        Objects.requireNonNull(departureTime, "RouteFinder route() -> departureTime null");
        Objects.requireNonNull(connectionType, "RouteFinder route() -> connectionType null");

        RouteState routeState = RouteState.of(nodes, origin, departureTime);
        while (!routeState.allReached()) {
            RouteNode currentAirportNode = routeState.closestUnreached();
            if (currentAirportNode.getNode().equals(destination)) {
                return currentAirportNode;
            } else if (currentAirportNode.availableFlights(connectionType).isEmpty()) {
                return null;
            } else {
                findFastestConnectedFlight(currentAirportNode, connectionType, routeState);
            }

        }
        //no route found
        return null;
    }

    // Finds the fastest connected flight and sets is as the next route node
    private void findFastestConnectedFlight(RouteNode currentAirportNode, ConnectionType connectionType, RouteState routeState) {
        for (Connection connection : currentAirportNode.availableFlights(connectionType)) {
            RouteTime destinationArrivalTime = routeState.airportNode(connection.destination()).getArrivalTime();
            if (new RouteTime(connection.arrivalTime()).compareTo(destinationArrivalTime) < 0) {
                routeState.replaceNode(RouteNode.of(connection, currentAirportNode));
            }
        }
    }


}
