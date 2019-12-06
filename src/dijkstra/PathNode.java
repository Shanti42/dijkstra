// public because PathNode is returned by PathFinder, so people will use it
// non-final because then people can extend it to work with the networks example

// Caitlin -- done!

package dijkstra;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a link along a path, containing a node and optionally the previous node on the path
 */
public class PathNode implements Comparable<PathNode> {

    private final Node node;
    private final Cost cost; // represents the cost to reach this current node

    //Null previous denotes that this node is the original departure node
    private final PathNode previous;


    private PathNode(Node node, Cost cost, PathNode previous) {
        this.node = node;
        this.cost = cost;
        this.previous = previous;
    }

    /**
     * Builder method that takes in a connection and a previous node
     *
     * @param connection a connection that represents the path from the origin to the destination
     * @param previous   a PathNode that represents the origin of the connection
     * @return a new PathNode with connection's destination as its node and previous node "previous"
     * @throws NullPointerException if connection or previous is null
     */
    public static final PathNode of(Connection connection, PathNode previous) {
        Objects.requireNonNull(connection, "connection received null");
        Objects.requireNonNull(connection, "previous received null");

        Cost<Addable> cost = connection.getCost().plus(connection.getDestination().getNodeCost());

        return new PathNode(connection.getDestination(), cost.plus(previous.getCost()), previous);
    }

    /**
     * Builder method that takes in a node and a previous node
     *
     * @param node     the destination node
     * @param previous a PathNode that represents previous node in the path
     * @return a new PathNode of "node" with previous "previous"
     * @throws NullPointerException if node is null
     */
    public static final PathNode of(Node node, PathNode previous) {
        Objects.requireNonNull(node, "received null node");
        //Previous node can be null, so no null check

        return new PathNode(node, node.getNodeCost(), previous);
    }

    /**
     * Builder method that takes in a node.
     *
     * @param node the destination node
     * @return a new PathNode of "node" with previous PathNode as null
     * @throws NullPointerException if node is null
     */
    public static final PathNode of(Node node) {
        Objects.requireNonNull(node, "received null node");

        return new PathNode(node, node.getNodeCost(), null);
    }

    /**
     * Builder method that takes in a node.
     *
     * @param node the destination node
     * @param cost the cost associated with this PathNode
     * @param previous the path node that came before this path node
     * @return a new PathNode of "node" with previous PathNode as null
     * @throws NullPointerException if node is null (cost and previous can be null)
     */
    public static final PathNode of(Node node, Cost<Addable> cost, PathNode previous) {
        Objects.requireNonNull(node, "received null node");

        return new PathNode(node, cost, previous);
    }

    /**
     * A getter method for the internal node
     *
     * @return the node that this PathNode represents
     */
    public final Node getNode() {
        return node;
    }

    /**
     * A getter method for the cost to get to this point on the path
     *
     * @return the cost to get to this point on the PathNode
     */
    public final Cost getCost() {
        return cost;
    }

    /**
     * A getter method for the previous PathNode on the path
     * <p>
     * Overridable in case people want to have multiple previous nodes.
     *
     * @return the previous PathNode in this path
     */
    public PathNode getPrevious() {
        return previous;
    }

    /**
     * Returns all available outgoing connections associated with this node
     *
     * @return a set of availible connections outgoing from this node
     * @throws AssertionError if we don't know the cost (we should only run this method if the cost is known)
     */
    Set<Connection> availableConnections() {
        assert (isKnown());
        return getNode().availableConnections();
    }

    /**
     * Returns all available outgoing connections associated with this node with the specified connection type
     *
     * @param connectionType the type of connection that the outgoing connections should have
     * @return a set of availible connections outgoing from this node of the specified connection type
     * @throws AssertionError if we don't know the cost (we should only run this method if the cost is known)
     */
    Set<Connection> availableConnections(ConnectionType connectionType) {
        assert (isKnown());

        if (connectionType == null) {
            return availableConnections();
        }

        return node.availableConnections(cost, connectionType);
    }

    /**
     * Determines if the cost of this PathNode is known or not.
     *
     * @return boolean that is true if the PathNode's cost is known, and false otherwise
     */
    public final boolean isKnown() {
        return cost != null;
    }

    /**
     * Compares this object to another PathNode. First compares their cost, and if their costs are equal,
     * compares their nodes.
     *
     * @param other the PathNode to be compared
     * @throws NullPointerException if other is null
     * @return an integer based on which object comes first.
     */
    @Override
    public int compareTo(PathNode other) {
        Objects.requireNonNull(other, "PathNode, compareTo() -> Null parameter for other PathNode");

        if (compareCost(this.getCost(), other.getCost()) == 0) {
            return this.getNode().compareTo(other.getNode());
        } else {
            return compareCost(this.getCost(), other.getCost());
        }
    }

    private static int compareCost(Cost<Addable> cost1, Cost<Addable> cost2) {
        if (cost1 == null && cost2 == null) {
            return 0;
        }

        if (cost1 == null) {
            return 1;
        }

        return cost1.compareTo(cost2);
    }

    /**
     * An equals method that compares all internal values. If all internal values are equal, then they're equal
     * (same cost, node, and previous node)
     *
     * @param o the Object to be compared
     * @return a boolean that is true if o is equal to this object, and false otherwise
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

    // for testing purposes ONLY: delete later
    @Override
    public String toString() {
        return node.getID();
    }

}
