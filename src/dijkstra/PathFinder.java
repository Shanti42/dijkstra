package dijkstra;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

/**
 * Re-routes passengers from an airport to a final destination
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

    /**
     * Finds and returns tha last route node in the fastest route
     * from the departure aiprot to final destination
     *
     * @param origin        the departure airport
     * @param destination   the final destination
     * @param departureTime the time of departure
     * @param connectionType     the fareclass of the passenger
     * @return
     */
    public final PathNode bestPath(Node start, Node end, ConnectionType connectionType) {
        //check for null values
        Objects.requireNonNull(start, "PathFinder, bestPath() -> origin null");
        Objects.requireNonNull(end, "PathFinder, bestPath() -> destination null");
        Objects.requireNonNull(connectionType, "PathFinder bestPath() -> connectionType null");

        PathState pathState = PathState.of(nodes, origin, departureTime);
        loop Search_Loop:
        while (!pathState.allReached()) {
            PathNode currentNode = pathState.closestUnreached();
            if(!currentNode.isKnown()) {
                break Search_Loop;
            }
        }
        //no route found
        return null;
    }
}
