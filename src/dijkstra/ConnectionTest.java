package dijkstra;

import org.junit.Test;

import java.lang.annotation.Target;
import java.math.BigInteger;


class ConnectionTest{
    Connection connection;
    Node bob = Node.of("BOB", new SimpleCost(BigInteger.valueOf(0)));
    Node susan = Node.of("SUSAN", new SimpleCost(BigInteger.valueOf(0)));
    Cost small = new SimpleCost(BigInteger.valueOf(10));
    ConnectionType bus = ConnectionType.of(3);

    public ConnectionTest(){
       connection = new Connection(bob, susan, small, bus){
           @Override
           boolean isLowerCost(Connection connection, Object obj) {
               return false;
           }
       };
    }

    @Test
    public void testConnection(){

    }

    @Test
    public void testGetOriginID(){

    }

    @Test
    public void testGetOrigin(){

    }

    @Test
    public void testGetDestination(){

    }

    @Test
    public void testGetCost(){

    }

    @Test
    public void testGetConnectionType(){

    }

    @Test
    public void testIsLowerCost(){

    }

    @Test
    public void testHashCode(){

    }

}

