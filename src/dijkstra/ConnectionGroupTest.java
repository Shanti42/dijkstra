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
    public static Cost costBig = Cost.of(SimpleAddable.of(BigInteger.valueOf(10)));
    Connection nullConnection = null;

    public static Connection connection;



    Node origin = Node.of("BOB", new SimpleCost(BigInteger.valueOf(0)));
    ConnectionGroup connectionGroup = ConnectionGroup.of(origin);

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


        connectionLarge = SimpleConnection.of(origin, destination, cost4, busConnectionType);
        connectionSmall = SimpleConnection.of(origin, destination, cost2, busConnectionType);
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
    public void testConnectionGroupAddNullConnect() {
        connectionGroup.add(nullConnection);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectionGroupAddBadOrigin() {
        connectionGroup.add(badOriginConnection);
    }



    /**
     * ConnectionGroup remove() method
     */
    @Test
    void testConnectionGroupRemove() {

        ConnectionGroup connectionGroup = ConnectionGroup.of(origin);


        connectionGroup.add(connection);

        assertTrue(connectionGroup.remove(connection));
        assertFalse(connectionGroup.remove(connection));

        Set<Connection> connections = new HashSet<>();
        connections.add(connection);

        assertEquals(connections, connectionGroup.connectionsAtOrAfter(cost2));

    }

    @Test(expected = NullPointerException.class)
    public void testConnectionGroupRemoveNullConnect() {
        connectionGroup.remove(nullConnection);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConnectionGroupRemoveBadOrigin() {
        connectionGroup.remove(badOriginConnection);
    }


    /**
     * ConnectionGroup connectionsAtOrAfter() test
     */
    @Test
    void testConnectionGroupConnectionsAtOrAfter() {

        ConnectionGroup connectionGroup = ConnectionGroup.of(origin);

        connectionGroup.add(connection);
        connectionGroup.add(connectionSmall);
        connectionGroup.add(connectionLarge);
        assertNotNull(connectionGroup.connectionsAtOrAfter(cost2);

        Set<Connection> connections = new HashSet<>();
        connections.add(connection);
        connections.add(connectionSmall);
        connections.add(connectionLarge);
        assertEquals(connections, connectionGroup.connectionsAtOrAfter(cost3));

        assertEquals(new HashSet<>(), connectionGroup.connectionsAtOrAfter(costBig));
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
    public void testGetOrigin() {
        assertEquals(connectionGroup.getOrigin(), origin);
    }

    @Test
    public void testAllConnections() {

    }

}
