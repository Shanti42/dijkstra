package dijkstra;

import java.util.Objects;
import java.util.Set;

public final class PathNode implements Comparable<PathNode> {

    private final Node node;
    private final Cost cost;

    //Null previous denotes that this node is the original departure node
    private final PathNode previous;

    public Node getNode() {
        return node;
    }

    public Cost getCost() {
        return cost;
    }

    public final PathNode getPrevious() {
        return previous;
    }

    private PathNode(Node node, Cost cost, PathNode previous) {
        this.node = node;
        this.cost = cost;
        this.previous = previous;
    }

    public static final PathNode of(Node node, Cost cost, PathNode previous) {
        Objects.requireNonNull(node, "Received null node");
        Objects.requireNonNull(cost, "Received null cost");
        //Previous node can be null, so no null check

        return new PathNode(node, cost, previous);
    }

    public static final PathNode of(Connection connection, PathNode previous) {
        Objects.requireNonNull(connection, "connection received null");
        Objects.requireNonNull(previous, "previous node received is null");
        assert (connection.getOrigin().equals(previous.node));

        return new PathNode(connection.getDestination(), connection.getCost(), previous);
    }

    public static final PathNode of(Node node) {
        Objects.requireNonNull(node, "received null node");

        return new PathNode(node, Cost.UNKNOWN, null);

    }

    public final Boolean isArrivalTimeKnown() {
        return getArrivalTime().isKnown();
    }

    public final PathTime departureTime() {
        return getArrivalTime().plus(getNode().getNodeCost());
    }

    //Assumes arrival time is known as per instructions
    final Set<Connection> availableNodes(ConnectionType connectionType) {
        assert (arrivalTime.isKnown());
        Objects.requireNonNull(connectionType, "PathNode, availableConnections() -> Null parameter for connectionType");
        return node.availableConnections(this.departureTime().getTime(), connectionType);
    }

    @Override
    public int compareTo(PathNode other) {
        Objects.requireNonNull(other, "PathNode, compareTo() -> Null parameter for other PathNode");
        PathTime otherArrivalTime = other.getArrivalTime();
        if (this.getArrivalTime().compareTo(otherArrivalTime) == 0) {
            return this.getNode().compareTo(other.getNode());
        } else {
            return this.getArrivalTime().compareTo(otherArrivalTime);
        }

    }

}
