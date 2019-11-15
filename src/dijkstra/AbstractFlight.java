package dijkstra;

import java.time.LocalTime;
import java.time.Duration;
import java.util.Objects;

public abstract class AbstractFlight implements Flight {

    abstract public String getCode(); //Returns the flight identifier

    abstract public FlightSchedule getFlightSchedule(); //Returns a FlightSchedule

    public Airport origin()

    public Airport destination();

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