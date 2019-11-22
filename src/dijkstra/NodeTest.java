package dijkstra;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class NodeTest {

	// test of
	public static class OfTest {
		String id = "123";
		Cost cost = Cost.ZERO;

		// bad data, id is null
		@Test(expected = NullPointerException.class)
		public void badData1() {
			Node.of(null, cost);
		}

		// bad data, cost is Null
		@Test(expected = NullPointerException.class)
		public void badData2() {
			Node.of(id, null);
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Node node = Node.of(id, cost);
			Assert.assertEquals("123", node.getID());
			Assert.assertEquals(Cost.ZERO, node.getNodeCost());
		}
	}

	// test equals
	public static class EqualsTest {
		String id1 = "123";
		String id2 = "321";
		Cost cost = Cost.ZERO;
		Node node1 = Node.of(id1, cost);
		Node node2 = Node.of(id1, cost);
		Node node3 = Node.of(id2, cost);

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Assert.assertTrue(node1.equals(node2));
		}

		// branch coverage, IDs are not equal
		@Test
		public void branchCoverage() {
			assertFalse(node1.equals(node3));
		}

		// bad data, branch coverage, when input is null
		@Test
		public void badData1() {
			assertFalse(node1.equals(null));
		}

		// bad data, branch coverage, when input is not an instance of node
		@Test
		public void badData2() {
			assertFalse(node1.equals("123"));
		}
	}

	// test hashCode
	public static class HashCodeTest {
		String id = "123";
		Cost cost = Cost.ZERO;
		Node node1 = Node.of(id, cost);
		Node node2 = Node.of(id, cost);

		// good data, codeCoverage
		@Test
		public void codeCoverage() {
			Assert.assertEquals(node1.hashCode(), node2.hashCode());
		}
	}

	// test compareTo
	public static class CompareToTest {
		String id1 = "123";
		String id2 = "321";
		Cost cost = Cost.ZERO;
		Node node1 = Node.of(id1, cost);
		Node node2 = Node.of(id1, cost);
		Node node3 = Node.of(id2, cost);

		// good data, code coverage, expected 0
		@Test
		public void codeCoverage1() {
			Assert.assertEquals(0, node1.compareTo(node2));
		}

		// code coverage, expected < 0
		@Test
		public void codeCoverage2() {
			Assert.assertTrue(node1.compareTo(node3) < 0);
		}

		// code coverage, expect > 0
		@Test
		public void codeCoverage3() {
			Assert.assertTrue(node3.compareTo(node1) > 0);
		}
	}

	// test toString
	public static class ToStringTest {
		String id = "123";
		Cost cost = Cost.ZERO;
		Node node = Node.of(id, cost);

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Assert.assertEquals("123", node.toString());
		}
	}

	// test addConnection
	public static class AddConnectionTest {
		String id1 = "123";
		String id2 = "321";
		Cost cost = Cost.ZERO;
		ConnectionType type = ConnectionType.of(id1);
		Node node1 = Node.of(id1, cost);
		Node node2 = Node.of(id2, cost);
		Connection connection1 = SimpleConnection.of(node1, node2, cost, type);
		Connection connection2 = SimpleConnection.of(node2, node1, cost, type);

		// bad data, connection is null
		@Test
		public void badData1() {
			assertFalse(node1.addConnection(null));
		}

		// bad data, origins are not the same
		@Test
		public void badData2() {
			assertFalse(node1.addConnection(connection2));
		}

		// good data, code coverage
		public void codeCoverage() {
			assertTrue(node1.addConnection(connection1));
			assertTrue(node1.availableConnections().contains(connection1));
		}

		// branch coverage, when connection is already there
		public void branchCoverage() {
			assertTrue(node1.addConnection(connection1));
			assertFalse(node1.addConnection(connection1));
		}
	}

	// test removeConnection
	public static class removeConnectionTest {
		String id1 = "123";
		String id2 = "321";
		Cost cost = Cost.ZERO;
		ConnectionType type = ConnectionType.of(id1);
		Node node1 = Node.of(id1, cost);
		Node node2 = Node.of(id2, cost);
		Connection connection1 = SimpleConnection.of(node1, node2, cost, type);
		Connection connection2 = SimpleConnection.of(node2, node1, cost, type);

		// bad data, connection is null
		@Test
		public void badData1() {
			assertFalse(node1.removeConnection(null));
		}

		// bad data, origins are not the same
		@Test
		public void badData2() {
			assertFalse(node1.removeConnection(connection2));
		}

		// good data, code coverage
		public void codeCoverage() {
			node1.addConnection(connection1);
			assertTrue(node1.availableConnections().contains(connection1));
			assertTrue(node1.removeConnection(connection1));
			assertFalse(node1.availableConnections().contains(connection1));
		}

		// branch coverage, when the connection is not there
		public void branchCoverage() {
			assertFalse(node1.removeConnection(connection1));
		}
	}

	// test availableConnection()
	public static class AvailableConnectionTest {
		String id1 = "123";
		String id2 = "321";
		Cost cost = Cost.ZERO;
		ConnectionType type1 = ConnectionType.of(id1);
		ConnectionType type2 = ConnectionType.of(id2);
		Node node1 = Node.of(id1, cost);
		Node node2 = Node.of(id2, cost);
		Connection connection = SimpleConnection.of(node1, node2, cost, type1);

		// good data, no parameters
		@Test
		public void codeCoverageNoParams() {
			node1.addConnection(connection);
			Assert.assertTrue(node1.availableConnections().contains(connection));
		}

		// good data, with 1 parameter
		@Test
		public void codeCoverageOneParam() {
			node1.addConnection(connection);
			Assert.assertTrue(node1.availableConnections(type1).contains(connection));
		}

		// branch coverage, with 1 parameter
		@Test
		public void branchCoverageOneParam() {
			node1.addConnection(connection);
			Assert.assertTrue(node1.availableConnections(type2).isEmpty());
		}

		// bad data, with 1 parameter
		@Test(expected = NullPointerException.class)
		public void badDataOneParam() {
			node1.availableConnections(null);
		}

		// good data, with 2 parameters
		@Test
		public void codeCoverageTwoParams() {
			node1.addConnection(connection);
			assertTrue(node1.availableConnections(cost, type1).contains(connection));
			assertFalse(node1.availableConnections(cost, type2).contains(connection));

		}

		// branch coverage, with 2 parameter
		@Test
		public void branchCoverageTwoParam1() {
			node1.addConnection(connection);
			Assert.assertTrue(node1.availableConnections(cost, type2).isEmpty());
		}

		// branch coverage, with 2 parameter
		@Test
		public void branchCoverageTwoParam2() {
			node1.addConnection(connection);
			Assert.assertTrue(node1.availableConnections(Cost.of(SimpleAddable.of(BigInteger.ONE)), type1).isEmpty());
		}

		// bad data, with 2 parameter
		@Test(expected = NullPointerException.class)
		public void badDataTwoParam1() {
			node1.availableConnections(cost, null);
		}

		// bad data, with 2 parameter
		@Test(expected = NullPointerException.class)
		public void badDataTwoParam2() {
			node1.availableConnections(null, type1);
		}
	}
}