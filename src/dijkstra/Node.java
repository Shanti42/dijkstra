package dijkstra;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The Node on a route, including the identification ID and the connection time of the airport
 */
public final class Node implements Comparable<Node> {

    // short string identifier for the Node
    private final String ID;
    // the shortest length of time for a passenger to transfer planes
    private final Cost nodeCost;

    //New additions based on ConnectionGroup section of Assignment
    private final ConnectionGroup outConnections = ConnectionGroup.of(this);

    private Node(String ID, Cost nodeCost) {
        this.ID = ID;
        this.nodeCost = nodeCost;
    }

    static final Node of(String code, Cost nodeCost) {
        Objects.requireNonNull(code, "Node identifier ID is null");
        Objects.requireNonNull(nodeCost, "Connection time parameter is null");
        return new Node(code, nodeCost);
    }

    String getID() {
        return ID;
    }

    Cost getNodeCost() {
        return nodeCost;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Node) {
            Node otherNode = (Node) object;
            return getID().equals(otherNode.ID);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getID().hashCode();
    }

    @Override
    public int compareTo(Node otherNode) {
        return getID().compareTo(otherNode.getID());
    }

    @Override
    public String toString() {
        return getID();
    }

    final boolean addConnection(Connection connection) {
        return outConnections.add(connection);
    }

    final boolean removeConnection(Connection connection) {
        return outConnections.remove(connection);
    }


    Set<Connection> availableConnections(){
        return outConnections.allConnections();
    }

    Set<Connection> availableConnections(ConnectionType type){
        Objects.requireNonNull(type, "Node availableConnections() - null type");
        return outConnections.allConnections().stream()
                .filter(connection -> connection.connectionType().equals(type))
                .collect(Collectors.toSet());
    }

    Set<Connection> availableConnections(Cost nodeCost, ConnectionType type) {
        Objects.requireNonNull(nodeCost, "Node availableConnections() - null departureTime");
        Objects.requireNonNull(type, "Node availableConnections() - null ConnectionType");

        return outConnections.connectionsAtOrAfter(nodeCost).stream()
                .filter(connection -> connection.connectionType().equals(type))
                .collect(Collectors.<Connection>toSet());
    }

    // should we add in an availible connection that takes in 2 costs?




}
