package dijkstra.tests;

import dijkstra.Node;

import dijkstra.Flight;
import dijkstra.Leg;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static dijkstra.tests.AirportCodes.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Node and Leg classes
 */
class dijkstraTest extends FlightTest{

    /**
     *  --- Node Tests ---
     */
    /**
     * Node build method tests
     */
    @Test
    void testAirportOf() {

        //test error handling for method
        assertThrows(NullPointerException.class, () -> { Node.of(null, Duration.ofHours(2)); });
        assertThrows(NullPointerException.class, () -> { Node.of(CLE.name(), null); });
        assertThrows(NullPointerException.class, () -> { Node.of(null, null); });
        Node node = Node.of("CLE", Duration.ofHours(1));
        assertEquals(Node.of(CLE.name(), Duration.ofHours(1)), node, "Test build method creates valid object");
    }

    /**
     * Node equals() method test
     */
    @Test
    void testAirportEquals() {

        Node node = Node.of("CLE", Duration.ofHours(5));
        Node sameNode = Node.of("CLE", Duration.ofHours(5));
        Node differentNode = Node.of("LGA", Duration.ofHours(5));
        Integer notAirport = new Integer(1);

        assertTrue(node.equals(sameNode), "Test two equal nodes");
        assertFalse(node.equals(differentNode), "Test two non-equals nodes");
        assertFalse(node.equals(notAirport), "Test with non node object");
    }

    /**
     * Node compareTo() method test
     */
    @Test
    void testCompareTo() {

        Node node = Node.of("CLE", Duration.ofHours(5));
        Node sameNode = Node.of("CLE", Duration.ofHours(5));
        Node differentNode = Node.of("LGA", Duration.ofHours(5));

        assertTrue(node.compareTo(differentNode) < 0, "Test less than");
        assertTrue(differentNode.compareTo(node) > 0, "Test greater than");
        assertTrue(node.compareTo(sameNode) == 0, "Test equal to");
    }

    /**
     * Node availableConnections() method test
     */
    @Test
    void testAirportAvailableFlights() {
        Set<Flight> flights = new HashSet<Flight>();
        flights.add(flight);
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        flights.add(flight4);

        assertEquals(flights.toString(), origin.availableConnections(LocalTime.MIN, econFareClass).toString());
    }

    /**
     *      --- Leg Tests ---
     */
    /**
     * Leg build method test
     */
    @Test
    void testLegOf(){

        Node origin = Node.of("CLE", Duration.ofHours(5));
        Node dest = Node.of("LGA", Duration.ofHours(1));

        assertThrows(NullPointerException.class, () -> { Leg.of(null, dest); });
        assertThrows(NullPointerException.class, () -> { Leg.of(origin, null); });
        assertThrows(NullPointerException.class, () -> { Leg.of(null, null); });

        Leg leg1 = Leg.of(origin, dest);
        assertTrue(leg1.getOrigin().equals(origin), "Test Origin added to Leg");
        assertTrue(leg1.getDestination().equals(dest), "Test Destination added to Leg");
    }

}