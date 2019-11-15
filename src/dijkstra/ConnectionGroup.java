package dijkstra;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a set of flights that have the same origin airport
 * and that is organized by departure
 */
public final class ConnectionGroup {

    //Represents origin airport for all flights in group
    private final Node origin;

    //Map of departure time to the collection of flights that have that departure time
    private final NavigableMap<LocalTime, Set<Connection>> flights = new TreeMap<LocalTime, Set<Connection>>();

    //flights is organized by departure time (based on Discussion board)
    private ConnectionGroup(Node origin) {
        this.origin = origin;
    }

    public static final ConnectionGroup of(Node origin) {
        Objects.requireNonNull(origin, "ConnectionGroup - build() origin is null");
        return new ConnectionGroup(origin);
    }

    //Adds a connection to the collection mapped to its departure time
    public final boolean add(Connection connection) {
        validateFlightOrigin(connection, "add() - Flights must originate from the same airport to be added");

        return flights.computeIfAbsent(connection.departureTime(), fl -> new HashSet<Connection>()).add(connection);
    }

    //Removes a connection if it is mapped to the collection of flights at its departure time
    public final boolean remove(Connection connection) {
        validateFlightOrigin(connection, "remove() - Flights must originate from the same airport to be removed");

        return flights.computeIfPresent(connection.departureTime(), (key, oldVal) -> oldVal).remove(connection);
    }

    //Returns a set of all flights at or after the given departure time
    public final Set<Connection> flightsAtOrAfter(LocalTime departureTime) {
        Objects.requireNonNull(departureTime, "ConnectionGroup - flightsAtOrAfter() departureTime is null");
        return flights.tailMap(departureTime).values().stream().flatMap(Collection::stream).collect(Collectors.toSet());

    }

    public Node getOrigin() {
        return origin;
    }

    //Throws if connection airport does not have the same origin airport as the ConnectionGroup
    public void validateFlightOrigin(Connection connection, String errorMsg) {
        Objects.requireNonNull(connection, "validateFlightOrigin() - Connection is null");
        if (!origin.getID().equals(connection.origin().getID())) {
            throw new IllegalArgumentException((errorMsg));
        }
    }

}
