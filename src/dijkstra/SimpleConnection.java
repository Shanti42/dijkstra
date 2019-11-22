package dijkstra;


public final class SimpleConnection extends Connection {

    private SimpleConnection(Node origin, Node destination, Cost cost, ConnectionType connectionType){
        super(origin, destination, cost, connectionType);
    }

    public static final SimpleConnection of(Node origin, Node destination, Cost cost, ConnectionType connectionType){
        return new SimpleConnection(origin, destination, cost, connectionType);
    }


    @Override
    boolean isLowerCost(Connection connection) {
        return cost().compareTo(connection.cost()) < 0;
    }
}