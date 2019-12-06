// public because users need to create nodes to use PathFinder
// final because people don't need to modify it

package dijkstra;

/**
 * Represents a node on a path
 */

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class Node implements Comparable<Node> {

    // short string identifier for the Node
    private final String ID;
    // the shortest length of time for a passenger to transfer planes
    private final Cost<Addable> nodeCost;

    //New additions based on ConnectionGroup section of Assignment
    /**
     * A group of connections with this node as the origin
     */
    private final ConnectionGroup outConnections = ConnectionGroup.of(this);

    private Node(String ID, Cost<Addable> nodeCost) {
        this.ID = ID;
        this.nodeCost = nodeCost;
    }

    /**
     * The constructor for the Node class
     *
     * @param ID       the unique identification of a node
     * @param nodeCost the cost of a node
     * @return a new node class
     * @throws NullPointerException if the input code or nodeCost is null
     */
    static final Node of(String code, Cost<Addable> nodeCost) {
        Objects.requireNonNull(code, "Node identifier ID is null");
        Objects.requireNonNull(nodeCost, "Connection time parameter is null");
        return new Node(code, nodeCost);
    }

    /**
     * Get node's ID
     *
     * @return ID
     */
    String getID() {
        return ID;
    }

    /**
     * Get node's cost
     *
     * @return nodeCost
     */
    Cost getNodeCost() {
        return nodeCost;
    }

    /**
     * Compare whether this node is equal to the other object
     *
     * @param object the other node to be compared
     * @return true if two nodes have the same ID, else return false
     */
    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Node) {
            Node otherNode = (Node) object;
            return getID().equals(otherNode.ID);
        }
        return false;
    }

    /**
     * Get node's id's hashCode
     *
     * @return node's id's hashCode
     */
    @Override
    public int hashCode() {
        return getID().hashCode();
    }

    /**
     * Compare this node with the other object
     *
     * @param otherNode the other node to be compared
     * @return -1, 0, or 1 if this node's ID is smaller than, equal to, or larger than the other node's ID
     */
    @Override
    public int compareTo(Node otherNode) {
        return getID().compareTo(otherNode.getID());
    }

    /**
     * Get node's string representation
     *
     * @return node's id
     */
    @Override
    public String toString() {
        return getID();
    }

    /**
     * Add a connection to the group of connections originating from this node
     *
     * @param connection
     * @return whether the connection is added successfully
     */
    final boolean addConnection(Connection connection) {
        return outConnections.add(connection);
    }

    /**
     * Remove a connection from the group of connections originating from this node
     *
     * @param connection
     * @return whether the connection is removed successfully
     */
    final boolean removeConnection(Connection connection) {
        return outConnections.remove(connection);
    }


    /**
     * Returns all available connections originating
     *
     * @return all connections originating from this node
     */
    Set<Connection> availableConnections() {
        return outConnections.allConnections();
    }

    /**
     * Returns all connections originating from this node with the input type
     *
     * @param type connectionType used to filter
     * @return all connections originating from this node with the input type
     * @throws NullPointerException if the parameter type is null
     */
    Set<Connection> availableConnections(ConnectionType type) {
        Objects.requireNonNull(type, "Node availableConnections() - null type");
        return filterByType(outConnections.allConnections(), type);
    }

    /**
     * Returns all connections originating from this node with cost less than the input
     *
     * @param nodeCost cost used to filter
     * @return all connections originating from this node with cost less than the input
     * @throws NullPointerException if the given nodeCost is null
     */
    Set<Connection> availableConnections(Cost<Addable> nodeCost) {
        Objects.requireNonNull(nodeCost, "Node availableConnections() - null cost");
        return outConnections.connectionsAtOrAfter(nodeCost);
    }

    /**
     * Returns all connections originating from this node with cost less than the input
     * and with the given connection type
     *
     * @param nodeCost cost used to filter
     * @param type     connectionType used to filter
     * @return all connections originating from this node with cost less than the input and with the given connection type
     * @throws NullPointerException if the given cost or ConnectionType is null
     */
    Set<Connection> availableConnections(Cost<Addable> nodeCost, ConnectionType type) {
        Objects.requireNonNull(nodeCost, "Node availableConnections() - null nodeCose");
        Objects.requireNonNull(type, "Node availableConnections() - null ConnectionType");

        return filterByType(outConnections.connectionsAtOrAfter(nodeCost), type);

    }

    private Set<Connection> filterByType(Set<Connection> toModify, ConnectionType type) {
        return toModify.stream()
                .filter(connection -> connection.getConnectionType().equals(type))
                .collect(Collectors.<Connection>toSet());
    }

    // should we add in an available connection that takes in 2 costs?


}
