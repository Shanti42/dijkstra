package dijkstra;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class ConnectionTest {

    private Connection connection;
    private Connection sameConnection;
    private Connection differentConnection;
    private Node bob = Node.of("BOB", Cost.of(SimpleAddable.of(BigInteger.valueOf(3))));
    private Node susan = Node.of("Susan", Cost.of(SimpleAddable.of(BigInteger.valueOf(3))));
    private Cost small = Cost.of(SimpleAddable.of(BigInteger.valueOf(0)));
    private ConnectionType bus = ConnectionType.of("BUS");

    public ConnectionTest() {
        connection = SimpleConnection.of(bob, susan, small, bus);
        sameConnection = SimpleConnection.of(bob, susan, small, bus);
        differentConnection = SimpleConnection.of(bob, susan, small, ConnectionType.of("FLY"));
    }

    @Test
    public void testGetOriginID() {
        assertEquals(connection.originID(), bob.getID());
    }

    @Test
    public void testGetOrigin() {
        assertEquals(connection.origin(), bob);
    }

    @Test
    public void testGetDestination() {
        assertEquals(connection.destination(), susan);
    }

    @Test
    public void testGetCost() {
        assertEquals(connection.cost(), small);
    }

    @Test
    public void testGetConnectionType() {
        assertEquals(connection.connectionType(), bus);
    }


    @Test
    public void testHashCode() {
        assertEquals(connection.hashCode(), sameConnection.hashCode());
        assertNotEquals(connection.hashCode(), differentConnection.hashCode());
    }

}


