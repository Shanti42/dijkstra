package dijkstra;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ConnectionGroupTest {

    public static String connectionCode;
    public static String originCode;
    public static String destinationCode;

    public static Node origin;
    public static Node destination;
    public static Node other;

    public static Cost noCost = Cost.of(SimpleAddable.of(null));
    public static Cost cost2 = Cost.of(SimpleAddable.of(BigInteger.valueOf(2)));
    public static Cost cost3 = Cost.of(SimpleAddable.of(BigInteger.valueOf(3)));
    public static Cost cost4 = Cost.of(SimpleAddable.of(BigInteger.valueOf(4)));
    Connection nullConnection = null;

    public static Connection connection;


    public static ConnectionType busConnectionType = ConnectionType.of("BUS");
    public static ConnectionType trainConnectionType = ConnectionType.of("TRAIN");
    public static ConnectionType opticalConnectionTypeLow = ConnectionType.of("OPTICAL");
    public static ConnectionType attackConnectionType = ConnectionType.of("ATTACK");

    Connection badOriginConnection;
    Connection connectionLarge;
    Connection connectionSmall;

    @Before
    void initializeConnections() {
        connectionCode = "A112";
        originCode = "CLE";
        destinationCode = "LGA";


        origin = Node.of(originCode, noCost);
        destination = Node.of(destinationCode, noCost);
        other = Node.of("blank", noCost);


        connection = SimpleConnection.of(origin, destination, cost3, busConnectionType);


        connectionLarge = SimpleConnection.of(origin, destination, cost3, busConnectionType);
        connectionSmall = SimpleConnection.of(origin, destination, cost3, busConnectionType);
        badOriginConnection = SimpleConnection.of(other, destination, cost3, busConnectionType);
    }


    @Test
    void testSimpleConnectionIsLowerCost() {
        assertTrue(connection.isLowerCost(connectionLarge));
        assertFalse(connection.isLowerCost(connectionSmall));
        assertFalse(connection.isLowerCost(connection));
    }


    @Test
    void testConnectionGroupOf() {
        assertNotNull(ConnectionGroup.of(origin));
    }

    /**
     * ConnectionGroup add() method
     */
    @Test
    void testConnectionGroupAdd() {


        //Connection intentionally created with wrong origin for testing purposes


        ConnectionGroup connectionGroup = ConnectionGroup.of(origin);


        assertTrue(connectionGroup.add(connection));

        Set<Connection> connections = new HashSet<>();
        connections.add(connection);
        assertEquals(connections, connectionGroup.connectionsAtOrAfter(cost2));

        assertFalse(connectionGroup.add(connection));

    }

    @Test(expected = NullPointerException.class)
    public void testConnectionGroupNullConnect() {
        connectionGroup.add(nullConnection);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectionGroupBadOrigin() {
        connectionGroup.add(badOriginConnection);
    }



    /**
     * ConnectionGroup remove() method
     */
    @Test
    void testConnectionGroupRemove() {

        Connection nullConnection = null;
        //Connection intentionally created with wrong origin for testing purposes
        Connection badOriginConnection = SimpleConnection.of("Code", Leg.of(Node.of("JFK", Duration.ofHours(3)), destination), connectionSchedule, config1);

        ConnectionGroup connectionGroup = ConnectionGroup.of(origin);

        assertThrows(NullPointerException.class, () -> {
            connectionGroup.add(nullConnection);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            connectionGroup.add(badOriginConnection);
        });

        connectionGroup.add(connection);
        connectionGroup.add(connection1);

        assertTrue(connectionGroup.remove(connection));
        assertFalse(connectionGroup.remove(connection));

        Set<Connection> connections = new HashSet<>();
        connections.add(connection1);

        assertEquals(connections, connectionGroup.connectionsAtOrAfter(connection1.departureTime()));

    }

    /**
     * ConnectionGroup connectionsAtOrAfter() test
     */
    @Test
    void testConnectionGroupConnectionsAtOrAfter() {

        ConnectionGroup connectionGroup = ConnectionGroup.of(Node.of("CLE", Duration.ofHours(1)));

        connectionGroup.add(connection);
        connectionGroup.add(connection1);
        connectionGroup.add(connection2);
        assertNotNull(connectionGroup.connectionsAtOrAfter(connection.departureTime()));

        Set<Connection> connections = new HashSet<>();
        connections.add(connection);
        connections.add(connection1);
        connections.add(connection2);
        assertEquals(connections, connectionGroup.connectionsAtOrAfter(connection.departureTime()));

        assertEquals(new HashSet<>(), connectionGroup.connectionsAtOrAfter(LocalTime.of(20, 0)));
    }

    /**
     *      ---- ConnectionSchedule tests ---
     */
    /**
     * ConnectionSchedule build method test
     */
    @Test
    void testConnectionScheduleOf() {

        assertThrows(NullPointerException.class, () -> {
            ConnectionSchedule.of(null, null);
        });
        assertThrows(NullPointerException.class, () -> {
            ConnectionSchedule.of(depart, null);
        });
        assertThrows(NullPointerException.class, () -> {
            ConnectionSchedule.of(null, arrival);
        });

        assertNotNull(ConnectionSchedule.of(depart, arrival));
    }

    /**
     * ConnectionSchedule isLowerCost() test
     */
    @Test
    void testConnectionScheduleIsShort() {

        Duration oneHour = Duration.ofHours(1);
        Duration twoHour = Duration.ofHours(2);
        Duration min30 = Duration.ofMinutes(30);

        LocalTime oneHourLoc = LocalTime.of(1, 0);
        LocalTime min30Loc = LocalTime.of(0, 30);
        LocalTime twoHourLoc = LocalTime.of(2, 0);

        ConnectionSchedule hourConnection = ConnectionSchedule.of(oneHourLoc, twoHourLoc);

        assertThrows(NullPointerException.class, () -> {
            hourConnection.isShort(null);
        });
        assertTrue(hourConnection.isShort(oneHour), "Duration equal to connection");
        assertFalse(hourConnection.isShort(min30), "Duration shorter than connection");
        assertTrue(hourConnection.isShort(twoHour), "Duration longer than connection");
    }


    Node origin = Node.of("BOB", new SimpleCost(BigInteger.valueOf(0)));
    ConnectionGroup connectionGroup = ConnectionGroup.of(origin);

    public ConnectionGroupTest() {

    }

    @Test
    public void testOf() {
        assertEquals(connectionGroup.getOrigin(), ConnectionGroup.of(origin).getOrigin());
    }

    @Test(expected = NullPointerException.class)
    public void testOfNullVal() {
        ConnectionGroup.of(null);
    }


    @Test
    public void testAdd() {

    }

    @Test
    public void testRemove() {

    }

    @Test
    public void testConnectionsAtOrAfter() {

    }

    @Test
    public void testAllConnections() {

    }


    @Test
    public void testGetOrigin() {

    }

    @Test
    public void testValidateConnectionOrigin() {

    }
}
