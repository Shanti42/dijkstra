package dijkstra;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConnectionTypeTest {

    private String identifier = "Stuff";
    private ConnectionType connectionType = ConnectionType.of(identifier);
    private ConnectionType sameConnectionType = ConnectionType.of(identifier);
    private ConnectionType differConnectionType = ConnectionType.of("Stuff2");

    @Test
    public void testOf() {
        assertEquals("Test of equals", connectionType, ConnectionType.of(identifier));
    }

    @Test(expected = NullPointerException.class)
    public void testOfNullVal() {
        ConnectionType.of(null);
    }

    @Test
    public void testGetIdentifier() {
        assertEquals(connectionType.getIdentifier(), identifier);
    }

    @Test
    public void testHashCode() {
        assertEquals(sameConnectionType.hashCode(), connectionType.hashCode());
        assertNotEquals(differConnectionType.hashCode(), connectionType.hashCode());
    }

    @Test
    public void testEquals() {
        assertEquals("Equal object", sameConnectionType, connectionType);
        assertNotEquals("Not equal", connectionType, differConnectionType);
        assertNotEquals("null object", connectionType, null);
        assertNotEquals("not connection type", connectionType, new Object());
    }

}
