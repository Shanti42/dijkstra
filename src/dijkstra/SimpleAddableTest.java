package dijkstra;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class SimpleAddableTest {

	// test of
	public static class OfTest {

		// bad data, value is null
		@Test(expected = NullPointerException.class)
		public void badData() {
			SimpleAddable.of(null);
		}

		// good data, code coverage
		@Test()
		public void codeCoverageBigInteger() {
			SimpleAddable a = SimpleAddable.of(BigInteger.ONE);
			Assert.assertEquals(BigInteger.ONE, a.getValue());
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			SimpleAddable a = SimpleAddable.of(1);
			Assert.assertEquals(BigInteger.ONE, a.getValue());
		}
	}

	// test compareTo
	public static class CompareToTest {
		SimpleAddable a = SimpleAddable.of(1);
		SimpleAddable b = SimpleAddable.of(1);
		SimpleAddable c = SimpleAddable.of(2);
		SimpleAddable d = SimpleAddable.of(0);

		// bad data, input is null
		@Test(expected = NullPointerException.class)
		public void badData1() {
			a.compareTo(null);
		}

		// bad data, input is not SimpleAddable
		@Test(expected = IllegalArgumentException.class)
		public void badData2() {
			a.compareTo(1);
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Assert.assertTrue(a.compareTo(b) == 0);
		}

		// branch coverage
		@Test
		public void branchCoverage1() {
			Assert.assertTrue(a.compareTo(c) < 0);
		}

		// branch coverage
		@Test
		public void branchCoverage2() {
			Assert.assertTrue(a.compareTo(d) > 0);
		}
	}

	// test plus
	public static class PlusTest {
		SimpleAddable a = SimpleAddable.of(5);

		// bad data, input is null
		@Test(expected = NullPointerException.class)
		public void badData() {
			a.plus(null);
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Assert.assertEquals(BigInteger.TEN, a.plus(a).getValue());
		}
	}

	// test equals
	public static class EqualsTest {
		SimpleAddable a = SimpleAddable.of(1);
		SimpleAddable b = SimpleAddable.of(1);
		SimpleAddable c = SimpleAddable.of(2);

		// bad data, input is null
		@Test(expected = NullPointerException.class)
		public void badData1() {
			a.equals(null);
		}

		// bad data, input is not SimpleAddable
		@Test
		public void badData2() {
			Assert.assertFalse(a.equals(1));
		}

		// good data, code coverage
		@Test
		public void codeCoverage() {
			Assert.assertTrue(a.equals(b));
		}

		// branch coverage
		@Test
		public void branchCoverage() {
			Assert.assertFalse(a.equals(c));
		}
	}

}
