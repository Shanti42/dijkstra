// Caitlin

package dijkstra;

import java.math.BigInteger;
import java.util.Objects;

public class Cost<T extends Addable> implements Comparable{
	private T internalValue;

	public static final Cost UNKNOWN = new Cost(null);
	public static final Cost ZERO = new Cost(SimpleAddable.of(BigInteger.ZERO));

	private Cost(T object) {
		internalValue = object;
	}

	// Return a cost based on the input and private variable(s)
	public static <T extends Addable> Cost<T> of(T object) {
		Objects.requireNonNull(object);
		return new Cost<T>(object);
	}


	// returns true if route time is known, false otherwise
	public boolean isKnown() {
		return internalValue != null;
	}

	// Returns a cost based on private variables only
	public T cost() {
		assert isKnown();
		return internalValue;
	}


	// returns a new cost after adding the cost that corresponds with the object
	public Cost plus(Cost c) {
		Objects.requireNonNull(c);
		// plus seems awakward?? not sure how to do this
		return new Cost(internalValue.plus(c.cost()));
	}

	public int compareTo(Object other) {
		Objects.requireNonNull(other);
		if (other instanceof Cost) {
			return internalValue.compareTo(((Cost) other).internalValue);
		} else {
			throw new IllegalArgumentException("input's type is not correct");
		}
	}

	/* AUTOGENERATED BY INTELLIJ */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cost<?> cost = (Cost<?>) o;
		return Objects.equals(internalValue, cost.internalValue);
	}
}
