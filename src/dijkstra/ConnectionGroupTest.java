package dijkstra;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class ConnectionGroupTest {

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
