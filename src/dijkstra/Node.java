package dijkstra;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The Node on a route, including the identification ID and the connection time of the airport
 */
class Node implements Comparable<Node> {

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

    /**
     * Finds flights that leave at or after the departure time that have seats for the fare class
     *
     * @param departureTime the time flights must leave at or after
     * @param fareclass     the fare class seats are checked for
     * @return A set of flights that leave at or after the departure time that have seats for the fare class
     */
    Set<Connection> availableConnections(LocalTime departureTime, ConnectionType fareclass) {
        Objects.requireNonNull(departureTime, "Node availableConnections() - null departureTime");
        Objects.requireNonNull(fareclass, "Node availableConnections() - null ConnectionType");

        return outConnections.flightsAtOrAfter(departureTime).stream()
                .filter(fl -> fl.hasSeats(fareclass))
                .collect(Collectors.<Connection>toSet());
    }
}
