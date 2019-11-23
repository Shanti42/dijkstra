package dijkstra;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.math.BigInteger;

@RunWith(Enclosed.class)
public class SimpleConnectionTest {

    // test of
    public static class OfTest {
        String id1 = "123";
        String id2 = "321";
        Cost cost = Cost.ZERO;
        ConnectionType type = ConnectionType.of("000");
        Node node1 = Node.of(id1, cost);
        Node node2 = Node.of(id2, cost);

        // bad data, input is null
        @Test(expected = NullPointerException.class)
        public void badData1() {
            SimpleConnection.of(null, node2, cost, type);
        }

        // bad data, input is null
        @Test(expected = NullPointerException.class)
        public void badData2() {
            SimpleConnection.of(node1, null, cost, type);
        }

        // bad data, input is null
        @Test(expected = NullPointerException.class)
        public void badData3() {
            SimpleConnection.of(node1, node2, null, type);
        }

        // bad data, input is null
        @Test(expected = NullPointerException.class)
        public void badData4() {
            SimpleConnection.of(node1, node2, cost, null);
        }

        // good data, code coverage
        @Test
        public void codeCoverage() {
            Connection conn = SimpleConnection.of(node1, node2, cost, type);
            Assert.assertEquals(node1, conn.getOrigin());
            Assert.assertEquals(node2, conn.getDestination());
            Assert.assertEquals(cost, conn.getCost());
            Assert.assertEquals(type, conn.getConnectionType());
        }
    }

    // test isLowerCost
    String id1 = "123";
    String id2 = "321";
    Cost cost1 = Cost.ZERO;
    Cost cost2 = Cost.of(SimpleAddable.of(BigInteger.ONE));
    Cost cost3 = Cost.of(SimpleAddable.of(BigInteger.TEN));
    ConnectionType type = ConnectionType.of("000");
    Node node1 = Node.of(id1, cost1);
    Node node2 = Node.of(id2, cost1);
    Connection conn1 = SimpleConnection.of(node1, node2, cost1, type);
    Connection conn2 = SimpleConnection.of(node1, node2, cost2, type);
    Connection conn3 = SimpleConnection.of(node1, node2, cost2, type);
    Connection conn4 = SimpleConnection.of(node1, node2, cost3, type);

    // bad data, input is null
    @Test(expected = NullPointerException.class)
    public void badData() {
        conn2.isLowerCost(null);
    }

    // good data, code coverage
    @Test
    public void codeCoverage() {
        Assert.assertTrue(conn2.isLowerCost(conn4));
    }

    // boundary test
    @Test
    public void boundaryTest1() {
        Assert.assertFalse(conn2.isLowerCost(conn3));
    }

    // boundary test
    @Test
    public void boundaryTest2() {
        Assert.assertFalse(conn2.isLowerCost(conn1));
    }
}