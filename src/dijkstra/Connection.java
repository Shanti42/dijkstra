package dijkstra;

/**
 * Represents Connections of different types.
 * A Connection represents a non stop path from one node to another
 */
public abstract class Connection {

    private final Node origin;
    private final Node destination;
    private final Cost cost;
    private final ConnectionType connectionType;

    Connection(Node origin, Node destination, Cost cost, ConnectionType connectionType){
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.connectionType = connectionType;
        this.origin.addConnection(this);
    }

    String originID(){
        return origin.getID();
    }

    Node origin() {
        return origin;
    }

    Node destination() {
        return destination;
    }

    Cost cost() {
        return cost;
    }

    ConnectionType connectionType() {
        return connectionType;
    }

    //Returns the value of isLowerCost method
    abstract boolean isLowerCost(Connection connection);

    @Override
    public int hashCode() {
        if(connectionType == null) {
            return (origin.getID() + destination.getID()).hashCode();
        }
        return (origin.getID() + destination.getID() + connectionType.toString()).hashCode();
    }
}