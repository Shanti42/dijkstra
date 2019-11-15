package dijkstra;

import java.time.LocalTime;
import java.time.Duration;

public abstract class AbstractConnection implements Connection {

    abstract public String getCode(); //Returns the flight identifier

    abstract public FlightSchedule getFlightSchedule(); //Returns a FlightSchedule

    public Node origin()

    public Node destination();

    public LocalTime departureTime() {
        return this.getFlightSchedule().getDepartureTime();
    }

    public LocalTime arrivalTime() {
        return this.getFlightSchedule().getArrivalTime();
    }

    //Returns whether the flight is shorter then the given duration
    public boolean isShort(Duration durationMax) {
        return this.getFlightSchedule().isShort(durationMax);
    }


    @Override
    public int hashCode() { return getCode().hashCode(); }

}