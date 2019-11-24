package dijkstra;

import java.util.Objects;

/**
 * A simple implementation of a connection
 */
public final class SimpleConnection extends Connection {

    private SimpleConnection(Node origin, Node destination, Cost cost, ConnectionType connectionType) {
        super(origin, destination, cost, connectionType);
    }

    public static final SimpleConnection of(Node origin, Node destination, Cost cost, ConnectionType connectionType) {
        Objects.requireNonNull(origin, "SimpleCollection, of -> origin null");
        Objects.requireNonNull(destination, "SimpleCollection, of -> destination null");
        Objects.requireNonNull(cost, "SimpleCollection, of -> cost null");
        Objects.requireNonNull(connectionType, "SimpleCollection, of -> connectionType null");
        return new SimpleConnection(origin, destination, cost, connectionType);
    }

    public static final SimpleConnection of(Node origin, Node destination, Cost cost) {
        return new SimpleConnection(origin, destination, cost, null);
    }


    @Override
    boolean isLowerCost(Connection connection) {
        Objects.requireNonNull(connection, "SimpleCollection, isLowerCost -> connection null");
        return getCost().compareTo(connection.getCost()) < 0;
    }
}