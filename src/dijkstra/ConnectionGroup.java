package dijkstra;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * Represents a set of connections that have the same origin airport and that is
 * organized by departure
 */
final class ConnectionGroup {

	private final static Logger LOGGER =
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	// Represents origin airport for all connections in group
	private final Node origin;

	// Map of departure time to the collection of connections that have that
	// departure time
	private final NavigableMap<Cost, Set<Connection>> connections = new TreeMap<Cost, Set<Connection>>();

	// connections is organized by departure time (based on Discussion board)
	private ConnectionGroup(Node origin) {
		this.origin = origin;
	}

	static final ConnectionGroup of(Node origin) {
		Objects.requireNonNull(origin, "ConnectionGroup - build() origin is null");
		return new ConnectionGroup(origin);
	}

	// Adds a connection to the collection mapped to its departure time
	final boolean add(Connection connection) {
		try {
			validateConnectionOrigin(connection, "add() - Connections must originate from the same node to be added");
			return connections.computeIfAbsent(connection.cost(), fl -> new HashSet<Connection>()).add(connection);
		} catch (Exception e) {
			return false;
		}
	}

	// Removes a connection if it is mapped to the collection of connections at its
	// departure time
	final boolean remove(Connection connection) {
		try {
			validateConnectionOrigin(connection,
					"remove() - Connections must originate from the same node to be removed");
			return connections.computeIfPresent(connection.cost(), (key, oldVal) -> oldVal).remove(connection);
		} catch (Exception e) {
			return false;
		}
	}

	// Returns a set of all connections at or after the given departure time
	final Set<Connection> connectionsAtOrAfter(Cost cutOff) {
		Objects.requireNonNull(cutOff, "ConnectionGroup - connectionsAtOrAfter() departureTime is null");
		return connections.tailMap(cutOff).values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
	}

	final Set<Connection> allConnections() {
		return connections.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
	}

	public Node getOrigin() {
		return origin;
	}

	// Throws if connection airport does not have the same origin airport as the
	// ConnectionGroup
	public void validateConnectionOrigin(Connection connection, String errorMsg) {
		Objects.requireNonNull(connection, "validateConnectionOrigin() - Connection is null");
		if (!origin.getID().equals(connection.originID())) {
			LOGGER.log(Level.SEVERE, "Connection origin was invalid");
			throw new IllegalArgumentException((errorMsg));
		}
	}

}
