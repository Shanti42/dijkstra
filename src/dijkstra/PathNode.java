// CAITLIN

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

    public Cost getTotalCost() {
        if(previous == null) {
            return this.getCost();
        }
        return cost.plus(previous.getTotalCost());
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
        assert (connection.origin().equals(previous.node));

        return new PathNode(connection.destination(), connection.cost(), previous);
    }

    public static final PathNode of(Node node) {
        Objects.requireNonNull(node, "received null node");

        return new PathNode(node, Cost.UNKNOWN, null);

    }

    public static final PathNode of(Node node, Cost cost) {
        Objects.requireNonNull(node, "received null node");

        return new PathNode(node, cost, null);

    }

    //Assumes arrival time is known as per instructions
    final Set<Connection> availableNodes(ConnectionType connectionType) {
        assert (isKnown());
        Objects.requireNonNull(connectionType, "PathNode, availableConnections() -> Null parameter for connectionType");
        return node.availableConnections(cost, connectionType);
    }

    public final boolean isKnown() {
        return cost.isKnown();
    }

    @Override
    public int compareTo(PathNode other) {
        Objects.requireNonNull(other, "PathNode, compareTo() -> Null parameter for other PathNode");
        Cost otherArrivalTime = other.getCost();
        if (this.getCost().equals(otherArrivalTime)) {
            return this.getNode().compareTo(other.getNode());
        } else {
            return this.getCost().compareTo(otherArrivalTime.cost());
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof PathNode) {
            PathNode p = (PathNode) o;
            return node.equals(p.getNode());
        }
        return false;
    }

}
