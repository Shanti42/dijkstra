// public because PathNode is returned by PathFinder, so people will use it

// Caitlin

package dijkstra;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a link along a path, containing a node and optionally the previous node on the path
 */
public final class PathNode implements Comparable<PathNode> {

    private final Node node;
    private final Cost cost; // represents the cost to reach this current node

    //Null previous denotes that this node is the original departure node
    private final PathNode previous;

    private PathNode(Node node, Cost cost, PathNode previous) {
        this.node = node;
        this.cost = cost;
        this.previous = previous;
    }

    public static final PathNode of(Connection connection, PathNode previous) {
        Objects.requireNonNull(connection, "connection received null");
        //Previous node can be null, so no null check

        return new PathNode(connection.getDestination(), connection.getCost().plus(previous.getCost()), previous);
    }

    public static final PathNode of(Node node) {
        Objects.requireNonNull(node, "received null node");

        return new PathNode(node, node.getNodeCost(), null);

    }

    public Node getNode() {
        return node;
    }

    public Cost getCost() {
        return cost;
    }

    public final PathNode getPrevious() {
        return previous;
    }

    final Set<Connection> availableConnections() {
        assert (isKnown());
        return node.availableConnections();
    }

    final Set<Connection> availableConnections(ConnectionType connectionType) {
        assert (isKnown());

        if (connectionType == null) {
            return availableConnections();
        }

        return node.availableConnections(cost, connectionType);
    }

    public final boolean isKnown() {
        return cost.isKnown();
    }

    @Override
    public int compareTo(PathNode other) {
        Objects.requireNonNull(other, "PathNode, compareTo() -> Null parameter for other PathNode");
        Cost otherCost = other.getCost();
        if (this.getCost().equals(otherCost)) {
            return this.getNode().compareTo(other.getNode());
        } else {
            return this.getCost().compare(otherCost);
        }
    }

    /*
     * AUTO GENERATED BY INTELLIJ
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathNode pathNode = (PathNode) o;
        return node.equals(pathNode.node) &&
                Objects.equals(cost, pathNode.cost) &&
                Objects.equals(previous, pathNode.previous);
    }

    // for testing purposes
    @Override
    public String toString() {
        return node.getID();
    }

}
