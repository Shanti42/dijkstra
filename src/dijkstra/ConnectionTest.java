package dijkstra;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConnectionTest {

    Connection connection;
    private Connection sameConnection;
    private Connection differentConnection;
    private Node bob = Node.of("BOB", Cost.of(SimpleAddable.of(BigInteger.valueOf(3))));
    private Node susan = Node.of("Susan", Cost.of(SimpleAddable.of(BigInteger.valueOf(3))));
    private Node tim = Node.of("tim", Cost.of(SimpleAddable.of(BigInteger.valueOf(3))));
    private Cost small = Cost.of(SimpleAddable.of(BigInteger.valueOf(0)));
    private Cost large = Cost.of(SimpleAddable.of(BigInteger.valueOf(4)));
    private ConnectionType bus = ConnectionType.of("BUS");

    public ConnectionTest() {
        connection = SimpleConnection.of(bob, susan, small, bus);
        sameConnection = SimpleConnection.of(bob, susan, small, bus);
        differentConnection = SimpleConnection.of(tim, bob, large, bus);
    }

    @Test
    public void testGetOriginID() {
        assertEquals(connection.originID(), bob.getID());
    }

    @Test
    public void testGetOrigin() {
        assertEquals(connection.getOrigin(), bob);
    }

    @Test
    public void testGetDestination() {
        assertEquals(connection.getDestination(), susan);
    }

    @Test
    public void testGetCost() {
        assertEquals(connection.getCost(), small);
    }

    @Test
    public void testGetConnectionType() {
        assertEquals(connection.getConnectionType(), bus);
    }


    @Test
    public void testHashCode() {
        assertEquals(connection.hashCode(), sameConnection.hashCode());
        assertNotEquals(connection.hashCode(), differentConnection.hashCode());
    }

}


