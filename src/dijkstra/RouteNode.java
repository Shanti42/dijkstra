package dijkstra;

import java.util.Objects;
import java.util.Set;

public final class RouteNode implements Comparable<RouteNode> {

    private final Node node;
    private final RouteTime arrivalTime;

    //Null previous denotes that this node is the original departure node
    private final RouteNode previous;

    public Node getNode() {
        return node;
    }

    public RouteTime getArrivalTime() {
        return arrivalTime;
    }

    public final RouteNode getPrevious() {
        return previous;
    }

    private RouteNode(Node node, RouteTime arrivalTime, RouteNode previous) {
        this.node = node;
        this.arrivalTime = arrivalTime;
        this.previous = previous;
    }

    public static final RouteNode of(Node node, RouteTime arrivalTime, RouteNode previous) {
        Objects.requireNonNull(node, "Received null node");
        Objects.requireNonNull(arrivalTime, "Received null arrivalTime");
        //Previous node can be null, so no null check

        return new RouteNode(node, arrivalTime, previous);
    }

    public static final RouteNode of(Connection connection, RouteNode previous) {
        Objects.requireNonNull(connection, "connection received null");
        Objects.requireNonNull(previous, "previous node received is null");
        assert (connection.getOrigin().equals(previous.node));

        return new RouteNode(connection.getDestination(), new RouteTime(connection.getCost()), previous);
    }

    public static final RouteNode of(Node node) {
        Objects.requireNonNull(node, "received null node");

        return new RouteNode(node, RouteTime.UNKNOWN, null);

    }

    public final Boolean isArrivalTimeKnown() {
        return getArrivalTime().isKnown();
    }

    public final RouteTime departureTime() {
        return getArrivalTime().plus(getNode().getNodeCost());
    }

    //Assumes arrival time is known as per instructions
    public Set<Connection> availableFlights(ConnectionType connectionType) {
        assert (arrivalTime.isKnown());
        Objects.requireNonNull(connectionType, "RouteNode, availableConnections() -> Null parameter for connectionType");
        return node.availableConnections(this.departureTime().getTime(), connectionType);
    }

    @Override
    public int compareTo(RouteNode other) {
        Objects.requireNonNull(other, "RouteNode, compareTo() -> Null parameter for other RouteNode");
        RouteTime otherArrivalTime = other.getArrivalTime();
        if (this.getArrivalTime().compareTo(otherArrivalTime) == 0) {
            return this.getNode().compareTo(other.getNode());
        } else {
            return this.getArrivalTime().compareTo(otherArrivalTime);
        }

    }

}
