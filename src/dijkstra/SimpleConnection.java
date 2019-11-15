package dijkstra;

import java.util.Objects;

public final class SimpleConnection extends Connection {

    private SimpleConnection(Node origin, Node destination, Cost cost, ConnectionType connectionType){
        super(origin, destination, cost, connectionType);
    }

    public static final SimpleConnection of(String code, Leg leg, FlightSchedule flightSchedule, SeatConfiguration seatsAvailable) {
        origin.addFlight(newFlight);
        return newFlight;
    }


    @Override
    boolean isLowerCost(Connection connection, Object obj) {
        return false;
    }
}