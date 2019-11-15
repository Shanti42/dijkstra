package dijkstra;

import java.util.Objects;

public final class SimpleConnection extends AbstractConnection {

    //Connection identifier
    private final String code;
    //Connection leg
    private final Leg leg;
    //Connection Schedule
    private final FlightSchedule flightSchedule;
    //Seats available in each seat class on the flight
    private final SeatConfiguration seatsAvailable;

    private SimpleConnection(String code, Leg leg, FlightSchedule flightSchedule, SeatConfiguration seatsAvailable) {
        this.code = code;
        this.leg = leg;
        this.flightSchedule = flightSchedule;
        this.seatsAvailable = SeatConfiguration.clone(seatsAvailable);
    }

    public static final SimpleConnection of(String code, Leg leg, FlightSchedule flightSchedule, SeatConfiguration seatsAvailable) {
        Objects.requireNonNull(code, "SimpleConnection - of() Received null code parameter");
        Objects.requireNonNull(leg, "SimpleConnection - of() Received null leg parameter");
        Objects.requireNonNull(flightSchedule, "SimpleConnection - of() Received null flight Schedule parameter");
        Objects.requireNonNull(seatsAvailable, "SimpleConnection - of() Received null seats available parameter");
        SimpleConnection newFlight = new SimpleConnection(code, leg, flightSchedule, seatsAvailable);
        leg.getOrigin().addFlight(newFlight);
        return newFlight;
    }

    //Returns a copy of all seats available, regardless of fare class
    @Override
    public SeatConfiguration seatsAvailable(ConnectionType connectionType) {
        Objects.requireNonNull(connectionType, "SimpleConnection - seatsAvailable() Received null fare class parameter");
        return SeatConfiguration.clone(seatsAvailable);
    }

    public String getCode() {
        return code;
    }

    public Leg getLeg() {
        return leg;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

}