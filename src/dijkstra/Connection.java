package dijkstra;

import java.time.LocalTime;
import java.time.Duration;

/**
 * Represents flights of different types.
 * A flight represents the trip of an airplane that spans a leg following a timetable
 */
abstract class Connection {

    private final Node origin;
    private final Node destination;
    private final Cost cost;
    private final ConnectionType connectionType;


    private Connection(Node origin, Node destination, Cost cost, ConnectionType connectionType){
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.connectionType = connectionType;

    }


    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }

    public Cost getCost() {
        return cost;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    //Returns the value of isShort method
    public boolean isShort(Duration durationMax);

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object obj);

}