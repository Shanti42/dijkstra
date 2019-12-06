// Caitlin -- done!
// technically javadoc isn't required because package private D:
// should be package private because is only for testing purposes

package dijkstra;

import java.util.Objects;

/**
 * A simple implementation of Connection.
 */
final class SimpleConnection extends Connection {

    private SimpleConnection(Node origin, Node destination, Cost<Addable> cost, ConnectionType connectionType) {
        super(origin, destination, cost, connectionType);
    }

    /**
     * Returns a new SimpleConnection based on the specified parameters, where the SimpleConnection
     * has a specified ConnectionType
     * If any of the inputs are null, throws an exception.
     *
     * @param origin the node that the connection starts at
     * @param destination the node that the connection goes to
     * @param cost the weight of the node/the cost of traversing the node
     * @param connectionType the type of connection that this simpleConnection represents
     *
     * @return a new SimpleConnection based on the input parameters
     *
     * @throw NullPointerException is thrown if origin, destination, cost, and/or connectionType is null
     */
    static final SimpleConnection of(Node origin, Node destination, Cost<Addable> cost, ConnectionType connectionType) {
        Objects.requireNonNull(origin, "SimpleCollection, of -> origin null");
        Objects.requireNonNull(destination, "SimpleCollection, of -> destination null");
        Objects.requireNonNull(cost, "SimpleCollection, of -> cost null");
        Objects.requireNonNull(connectionType, "SimpleCollection, of -> connectionType null");
        return new SimpleConnection(origin, destination, cost, connectionType);
    }

    /**
     * Returns a new SimpleConnection based on the specified parameters (with no specified ConnectionType).
     * If any of the inputs are null, throws an exception.
     *
     * @param origin the node that the connection starts at
     * @param destination the node that the connection goes to
     * @param cost the weight of the node/the cost of traversing the node
     *
     * @return a new SimpleConnection based on the input parameters
     *
     * @throw NullPointerException is thrown if origin, destination, and/or cost is null
     */
    static final SimpleConnection of(Node origin, Node destination, Cost<Addable> cost) {
        return new SimpleConnection(origin, destination, cost, null);
    }


    /**
     * Determines if the input connection represents a lower cost edge than this edge
     *
     * @param connection the Connection to be compared with this object.
     *
     * @return true if this cost is less than "connection"'s cost, false otherwise
     *
     * @throw NullPointerException is thrown if connection is null
     */
    @Override
    boolean isLowerCost(Connection connection) {
        Objects.requireNonNull(connection, "SimpleCollection, isLowerCost -> connection null");
        return getCost().compare(connection.getCost()) < 0;
    }
}