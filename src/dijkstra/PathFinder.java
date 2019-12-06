// public because that's the point of the package
// final because we don't want people to mess it up

// Caitlin -- done!

package dijkstra;

import java.util.Objects;
import java.util.Set;

/**
 * Finds the best path from node to a final destination.
 *
 * Represents a set of nodes that can be searched.
 */
public final class PathFinder {

    private final Set<Node> nodes;

    private PathFinder(Set<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Finds and returns the last route node in the fastest route
     * from the departure node to final destination, with no ConnectionType specified
     *
     * @param nodes the nodes in the graph to be traversed
     *
     * @return a new PathFinder with the internal set of nodes
     *
     * @throws NullPointerException if the set of Nodes is null
     */
    public static final PathFinder of(Set<Node> nodes) {
        Objects.requireNonNull(nodes, "Received null set of nodes");

        return new PathFinder(nodes);
    }

    /**
     * Finds and returns the last route node in the fastest route
     * from the departure node to final destination, with no ConnectionType specified
     *
     * @param start          the departure node
     * @param end            the final destination
     * @return the last PathNode on the path to get from start to end, or null if none found
     */
    public final PathNode bestPath(Node start, Node end) {
        return bestPath(start, end, null);
    }

    /**
     * Finds and returns the last route node in the fastest route
     * from the departure node to final destination
     *
     * @param start          the departure node
     * @param end            the final destination
     * @param connectionType the connectionType of the passenger
     * @return the last PathNode on the path to get from start to end, or null if none found
     *
     * @throws IllegalStateException if there was an issue while searching
     * @throws NullPointerException if start or end are null
     */
    public final PathNode bestPath(Node start, Node end, ConnectionType connectionType) {
        //check for null values
        Objects.requireNonNull(start, "PathFinder, bestPath() -> origin null");
        Objects.requireNonNull(end, "PathFinder, bestPath() -> destination null");
        // connection type can be null

        PathState pathState = PathState.of(nodes, start);

        Search_Loop:
        {
            while (!pathState.allReached()) {
                try {
                    PathNode currentNode = pathState.closestUnreached();

                    if (currentNode == null) {
                        break Search_Loop;
                    }

                    if (currentNode.getNode().equals(end)) {
                        return currentNode;
                    }

                    /*
                     * for all available paths from “currentNode” with proper connectionType:
                     if that (destination node’s total cost via the path) < (previous cost of the node)
                     replace that node with a node that has “currentNode” as it’s “previous”
                     * */
                    findShortestPathLocal(currentNode, connectionType, pathState);

                } catch (RuntimeException e) {
                    throw new IllegalStateException("Error while searching for best path", e);
                }
            }
        }
        //no route found
        return null;
    }

    private void findShortestPathLocal(PathNode currentNode, ConnectionType connectionType, PathState pathState) {
        for (Connection connection : currentNode.availableConnections(connectionType)) {

            Cost<Addable> destinationCost = pathState.pathNode(connection.getDestination()).getCost();

            if (connection.getCost().compareTo(destinationCost) < 0) {
                pathState.replaceNode(PathNode.of(connection, currentNode));
            }
        }
    }

    static class TESTHOOK {
        public static void findShortestPathLocal_test(PathFinder finder, PathNode currentNode, ConnectionType connectionType, PathState pathState) {
            finder.findShortestPathLocal(currentNode, connectionType, pathState);
        }

        public static Set<Node> getNodes_test(PathFinder finder) {
            return finder.nodes;
        }
    }
}
