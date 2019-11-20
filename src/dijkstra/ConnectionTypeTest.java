package dijkstra;

import org.junit.Test;

import static org.junit.Assert.*;

class ConnectionTypeTest{

    String identifier = "Stuff";
    ConnectionType connectionType = ConnectionType.of(identifier);
    ConnectionType sameConnectionType = ConnectionType.of(identifier);
    ConnectionType differConnectionType = ConnectionType.of(identifier);

    @Test
    public void testOf(){
        assertEquals("Test of equals", connectionType, ConnectionType.of(identifier));
    }

    @Test(expected = NullPointerException.class)
    public void testOfNullVal(){
        ConnectionType.of(null);
    }

    @Test
    public void testGetIdentifier(){
        assertEquals(connectionType.getIdentifier(), identifier);
    }

    @Test
    public void testHashCode(){
        assertEquals(sameConnectionType.hashCode(), connectionType.hashCode());
        assertNotEquals(differConnectionType.hashCode(), connectionType.hashCode());
    }

    @Test
    public void testEquals(){
        assertEquals("Equal object", sameConnectionType, connectionType);
        assertNotEquals("Not equal", connectionType, differConnectionType);
        assertNotEquals("null object", connectionType, null);
        assertNotEquals("not connection type", connectionType, new Object());
    }

}