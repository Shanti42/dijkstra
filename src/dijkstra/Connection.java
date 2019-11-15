package dijkstra;

/**
 * Represents Connections of different types.
 * A Connection represents a non stop path from one node to another
 */
abstract class Connection {

    private final Node origin;
    private final Node destination;
    private final Cost cost;
    private final ConnectionType connectionType;


    Connection(Node origin, Node destination, Cost cost, ConnectionType connectionType){
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.connectionType = connectionType;
    }

    String originID(){
        return origin.getID();
    }


    Node getOrigin() {
        return origin;
    }

    Node getDestination() {
        return destination;
    }

    Cost getCost() {
        return cost;
    }

    ConnectionType getConnectionType() {
        return connectionType;
    }

    //Returns the value of isLowerCost method
    abstract boolean isLowerCost(Connection connection, Object obj);

    @Override
    public int hashCode() {
        return (origin.getID() + destination.getID() + connectionType.toString()).hashCode();
    }
}