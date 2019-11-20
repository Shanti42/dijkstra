package dijkstra;

import org.junit.Test;

import java.lang.annotation.Target;


class ConnectionTest{
    Connection connection;
    Node node = Node.of("BOB", );


    public ConnectionTest(){
       connection = new Connection(){
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

