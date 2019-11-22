package dijkstra;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class CostTest {

	// test of
	public static class OfTest {

		// bad data, input is null
		@Test(expected = NullPointerException.class)
		public void badData() {
			Cost.of(null);
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Cost c = Cost.of(SimpleAddable.of(BigInteger.ONE));
			assertEquals(SimpleAddable.of(BigInteger.ONE), c.cost());
		}
	}

	// test isKnown
	public static class IsKnownTest {
		// good data, code coverage
		@Test
		public void codeCoverage() {
			Cost c = Cost.of(SimpleAddable.of(BigInteger.ONE));
			Assert.assertTrue(c.isKnown());
		}

		// branch coverage
		@Test
		public void branchCoverage() {
			Cost c = Cost.UNKNOWN;
			Assert.assertFalse(c.isKnown());
		}
	}

	// test plus
	public static class PlusTest {
		// bad data, input is null
		@Test(expected = NullPointerException.class)
		public void badData() {
			Cost c = Cost.ZERO;
			c.plus(null);
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Cost c1 = Cost.ZERO;
			Cost c2 = Cost.of(SimpleAddable.of(BigInteger.ONE));
			assertEquals(SimpleAddable.of(BigInteger.ONE), c1.plus(c2).cost());
		}
	}

	// test compareTo
	public static class CompareToTest {
		Cost c1 = Cost.ZERO;
		Cost c2 = Cost.of(SimpleAddable.of(BigInteger.ONE));
		Cost c3 = Cost.of(SimpleAddable.of(BigInteger.ONE));
		Cost c4 = Cost.of(SimpleAddable.of(BigInteger.TEN));

		// bad data, input is null
		@Test(expected = NullPointerException.class)
		public void badData1() {
			c2.compareTo(null);
		}

		// bad data, input is not SimpleAddable
		@Test(expected = IllegalArgumentException.class)
		public void badData2() {
			c2.compareTo(1);
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Assert.assertTrue(c2.compareTo(c3) == 0);
		}

		// branch coverage
		@Test
		public void branchCoverage1() {
			Assert.assertTrue(c2.compareTo(c4) < 0);
		}

		// branch coverage
		@Test
		public void branchCoverage2() {
			Assert.assertTrue(c2.compareTo(c1) > 0);
		}
	}



	// test equals
	public static class EqualsTest {
		Cost c1 = Cost.ZERO;
		Cost c2 = Cost.of(SimpleAddable.of(BigInteger.ONE));
		Cost c3 = Cost.of(SimpleAddable.of(BigInteger.ONE));

		// bad data, input is null
		@Test
		public void badData1() {
			Assert.assertFalse(c2.equals(null));
		}

		// bad data, input is not SimpleAddable
		@Test
		public void badData2() {
			Assert.assertFalse(c2.equals(1));
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Assert.assertTrue(c2.equals(c3));
		}

		// branch coverage
		@Test
		public void branchCoverage() {
			Assert.assertFalse(c2.equals(c1));
		}

		@Test(expected = IllegalArgumentException.class)
		public void simpleAddablePlusBadType(){
			SimpleAddable addable = SimpleAddable.of(BigInteger.valueOf(2));
			addable.plus(new Addable() {
				@Override
				public int compareTo(Object o) {
					return 0;
				}

				@Override
				public Addable plus(Addable a) {
					return null;
				}
			});
		}

		@Test
		public void simpleAddableToString(){
			SimpleAddable addable = SimpleAddable.of(BigInteger.valueOf(2));
			assertEquals(addable.toString(), "2");
		}
	}
}
