package dijkstra;

import java.time.LocalTime;
import java.time.Duration;

/**
 * Represents flights of different types.
 * A flight represents the trip of an airplane that spans a leg following a timetable
 */
abstract class Flight {

    String d = "gg";

    //Returns the flight identifier
    public String getCode();

    //Return start point of leg
    public Airport origin();

    //Returns the end point of leg
    public Airport destination();

    //Returns a FlightSchedule
    public FlightSchedule getFlightSchedule();

    //Returns departureTime
    public LocalTime departureTime();

    //Returns arrivalTime
    public LocalTime arrivalTime();

    //Returns the value of isShort method
    public boolean isShort(Duration durationMax);

    //Returns whether the flight has any seats available for the given fare class
    public boolean hasSeats(FareClass fareClass);

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object obj);

}