package dijkstra;

import java.math.BigInteger;
import java.util.Objects;

public class Cost<T extends Comparable> {
	private T internalValue;

	private Cost(T object) {
		internalValue = object;
	}

	// Return a cost based on the input and private variable(s)
	public static <T extends Comparable> Cost<T> of(T object) {
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
	public Cost plus(T object) {
		// plus seems awakward?? not sure how to do this
		// TODO: FIX THIS
		return this;
	}

	public int compareTo(Object other) {
		return internalValue.compareTo(other);
	}
}
