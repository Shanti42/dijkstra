package dijkstra;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a set of connections that have the same origin airport
 * and that is organized by departure
 */
public final class ConnectionGroup {

    //Represents origin airport for all connections in group
    private final Node origin;

    //Map of departure time to the collection of connections that have that departure time
    private final NavigableMap<Cost, Set<Connection>> connections = new TreeMap<Cost, Set<Connection>>();

    //connections is organized by departure time (based on Discussion board)
    private ConnectionGroup(Node origin) {
        this.origin = origin;
    }

    public static final ConnectionGroup of(Node origin) {
        Objects.requireNonNull(origin, "ConnectionGroup - build() origin is null");
        return new ConnectionGroup(origin);
    }

    //Adds a connection to the collection mapped to its departure time
    public final boolean add(Connection connection) {
        validateConnectionOrigin(connection, "add() - Connections must originate from the same node to be added");

        return connections.computeIfAbsent(connection.getCost(), fl -> new HashSet<Connection>()).add(connection);
    }

    //Removes a connection if it is mapped to the collection of connections at its departure time
    public final boolean remove(Connection connection) {
        validateConnectionOrigin(connection, "remove() - Connections must originate from the same node to be removed");

        return connections.computeIfPresent(connection.getCost(), (key, oldVal) -> oldVal).remove(connection);
    }

    //Returns a set of all connections at or after the given departure time
    public final Set<Connection> connectionsAtOrAfter(LocalTime cutOffTime) {
        Objects.requireNonNull(cutOffTime, "ConnectionGroup - connectionsAtOrAfter() departureTime is null");
        return connections.tailMap((cutOffTime)).values().stream().flatMap(Collection::stream).collect(Collectors.toSet());

    }

    public Node getOrigin() {
        return origin;
    }

    //Throws if connection airport does not have the same origin airport as the ConnectionGroup
    public void validateConnectionOrigin(Connection connection, String errorMsg) {
        Objects.requireNonNull(connection, "validateConnectionOrigin() - Connection is null");
        if (!origin.getID().equals(connection.originID())) {
            throw new IllegalArgumentException((errorMsg));
        }
    }

}
