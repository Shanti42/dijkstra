// this is public because it's abstract

package dijkstra;

/**
 *
 * Represents a non stop path from one node to another node
 */
public abstract class Connection {

    private final Node origin;
    private final Node destination;
    private final Cost<Addable> cost;
    private final ConnectionType connectionType;

    /**
     * The constructor for the connection class
     *
     * @param origin         node the connection begins at
     * @param destination    the node the connection ends at
     * @param cost           the cost of the connection
     * @param connectionType the type of the connection
     */
    Connection(Node origin, Node destination, Cost<Addable> cost, ConnectionType connectionType) {
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.connectionType = connectionType;
        origin.addConnection(this);
    }

    /**
     * Return the origin id of the Connection
     *
     * @return the origin id of the Connection
     */
    protected String originID() {
        return origin.getID();
    }

    /**
     * Returns the origin of the Connection
     *
     * @return the origin of the Connection
     */
    protected Node getOrigin() {
        return origin;
    }

    /**
     * Returns the destination of the Connection
     *
     * @return the destination of the Connection
     */
    protected Node getDestination() {
        return destination;
    }

    /**
     * Returns the cost of the Connection
     *
     * @return the cost of the Connection
     */
    protected Cost<Addable> getCost() {
        return cost;
    }

    /**
     * Returns the connection type of the Connection
     *
     * @return the connection type of the Connection
     */
    protected ConnectionType getConnectionType() {
        return connectionType;
    }

    //Returns the value of isLowerCost method

    /**
     * Indicates if this connection has a lower cost than the given connection
     *
     * @param connection the connection being compared
     * @return true if this connection has a lower cost than the given connection, false otherwise
     */
    protected abstract boolean isLowerCost(Connection connection);

    /**
     * Returns the hash code of this Connection
     *
     * @return the hash code of this Connection
     */
    @Override
    public int hashCode() {
        if (connectionType == null) {
            return (origin.getID() + destination.getID()).hashCode();
        }
        return (origin.getID() + destination.getID() + connectionType.toString()).hashCode();
    }
}