package dijkstra;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.annotation.Target;
import java.math.BigInteger;


class ConnectionTest{
    Connection connection;
    Connection sameConnection;
    Connection differentConnection;
    Node bob = Node.of("BOB", new SimpleCost(BigInteger.valueOf(0)));
    Node susan = Node.of("SUSAN", new SimpleCost(BigInteger.valueOf(0)));
    Cost small = new SimpleCost(BigInteger.valueOf(10));
    ConnectionType bus = ConnectionType.of("BUS");

    public ConnectionTest(){
        connection = new Connection(bob, susan, small, bus){
            @Override
            boolean isLowerCost(Connection connection, Object obj) {
                return false;
            }
        };
        sameConnection = new Connection(bob, susan, small, bus){
            @Override
            boolean isLowerCost(Connection connection, Object obj) {
                return false;
            }
        };
        differentConnection = new Connection(bob, susan, small, bus) {
            @Override
            boolean isLowerCost(Connection connection, Object obj) {
                return false;
            }
        };
    }


    @Test
    public void testGetOriginID(){
        assertEquals(connection.originID(), bob.getID());
    }

    @Test
    public void testGetOrigin(){
        assertEquals(connection.getOrigin(), bob);
    }

    @Test
    public void testGetDestination(){
        assertEquals(connection.getDestination(), susan);
    }

    @Test
    public void testGetCost(){
        assertEquals(connection.getCost(), small);
    }

    @Test
    public void testGetConnectionType(){
        assertEquals(connection.getConnectionType(), bus);
    }

    @Test
    public void testIsLowerCost(){
        assertFalse(connection.isLowerCost(null, null));
    }

    @Test
    public void testHashCode(){
        assertEquals(connection.hashCode(), sameConnection.hashCode());
        assertNotEquals(connection.hashCode(), differentConnection.hashCode());
    }

}

