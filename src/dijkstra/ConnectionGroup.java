package dijkstra;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Represents a set of connections that have the same origin node and that is
 * organized by cost
 */
final class ConnectionGroup {

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    // Represents origin node for all connections in group
    private final Node origin;

    // When I change Cost here to Cost<Addable> it breaks :(
    // Map of departure time to the collection of connections that have that departure time
    private final NavigableMap<Cost, Set<Connection>> connections = new TreeMap<Cost, Set<Connection>>();

    /**
     * constructs a new empty connection group for connections that originate at the given node
     */
    private ConnectionGroup(Node origin) {
        this.origin = origin;
    }

    /**
     * Creates a new connection group that will contain connections starting at the given origin node
     *
     * @param origin the node connections in the connection group must originate from
     * @return the connection group with the given node as it's origin
     * @throws NullPointerException if the given origin is null
     */
    static ConnectionGroup of(Node origin) {
        Objects.requireNonNull(origin, "ConnectionGroup - build() origin is null");
        return new ConnectionGroup(origin);
    }


    /**
     * Adds a connection to the connection group by it's cost.
     * Returns true if the operation was successful.
     *
     * @param connection the connection to be added to the connection group
     * @return true if the connection was added successfully, false otherwise.
     */
    final boolean add(Connection connection) {
        try {
            validateConnectionOrigin(connection, "add() - Connections must originate from the same node to be added");
            return connections.computeIfAbsent(connection.getCost(), fl -> new HashSet<Connection>()).add(connection);
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Removes the connection from the connection group if it is contained in the group.
     * Returns true if the operation was successful.
     *
     * @param connection
     * @return returns true if the connection has been removed successfully
     */
    final boolean remove(Connection connection) {
        try {
            validateConnectionOrigin(connection,
                    "remove() - Connections must originate from the same node to be removed");
            return connections.computeIfPresent(connection.getCost(), (key, oldVal) -> oldVal).remove(connection);
        } catch (RuntimeException e) {
            return false;
        }
    }


    /**
     * Returns all connections with a greater cost than the given cost
     *
     * @param cutOff the cost being compared
     * @return all connections with a greater cost than the given cost
     * @throws NullPointerException if the given cost is null
     */
    final Set<Connection> connectionsAtOrAfter(Cost cutOff) {
        Objects.requireNonNull(cutOff, "ConnectionGroup - connectionsAtOrAfter() departureTime is null");
        return connections.tailMap(cutOff).values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    /**
     * Returns all connections of the connection group
     *
     * @return all connections of the connection group
     */
    final Set<Connection> allConnections() {
        return connections.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    }

    /**
     * Returns the origin of the connection group
     *
     * @return the origin of the connection group
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * Checks if the given connection's origin is the same as the connection group's origin,
     * throws an exception and logs the issue if the origins don't match
     *
     * @param connection the connection whose origin is being checked
     * @param errorMsg   the error message to be added to the exception if the origins don't match
     *
     * @throws IllegalArgumentException if the given connection's origin is not equal
     *                                  to the origin of the connection group
     * @throws NullPointerException if the given connection is null
     */
    public void validateConnectionOrigin(Connection connection, String errorMsg) {
        Objects.requireNonNull(connection, "validateConnectionOrigin() - Connection is null");
        if (!origin.equals(connection.getOrigin())) {
            LOGGER.log(Level.SEVERE, "Connection origin was invalid");
            throw new IllegalArgumentException((errorMsg));
        }
    }

}
