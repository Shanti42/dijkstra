// public because that's the point of the package
// final because we don't want people to mess it up

// Caitlin

package dijkstra;

import java.util.Objects;
import java.util.Set;

/**
 * Finds the best path from node to a final destination
 */
public final class PathFinder {

    private final Set<Node> nodes;

    private PathFinder(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public static final PathFinder of(Set<Node> nodes) {
        Objects.requireNonNull(nodes, "Received null set of nodes");

        return new PathFinder(nodes);
    }


    public final PathNode bestPath(Node start, Node end) {
        return bestPath(start, end, null);
    }

    /**
     * Finds and returns tha last route node in the fastest route
     * from the departure node to final destination
     *
     * @param start          the departure node
     * @param end            the final destination
     * @param connectionType the connectionType of the passenger
     * @return
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

                } catch (Exception e) {
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

            if (connection.getCost().compare(destinationCost) < 0) {
                pathState.replaceNode(PathNode.of(connection, currentNode));
            }
        }
    }

    public static class TESTHOOK {
        public static void findShortestPathLocal_test(PathFinder finder, PathNode currentNode, ConnectionType connectionType, PathState pathState) {
            finder.findShortestPathLocal(currentNode, connectionType, pathState);
        }

        public static Set<Node> getNodes_test(PathFinder finder) {
            return finder.nodes;
        }
    }
}
