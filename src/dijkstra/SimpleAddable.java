package dijkstra;

import java.math.BigInteger;
import java.util.Objects;

public final class SimpleAddable implements Addable {
	private final BigInteger value;

	private SimpleAddable(BigInteger value) {
		this.value = value;
	}

	public BigInteger getValue() {
		return value;
	}

	public final static SimpleAddable of(int value) {
		return new SimpleAddable(BigInteger.valueOf(value));
	}

	public final static SimpleAddable of(BigInteger value) {
		Objects.requireNonNull(value, "SimpleAddable, of -> value null");
		return new SimpleAddable(value);
	}

	@Override
	public final int compareTo(Object obj) {
		Objects.requireNonNull(obj, "SimpleAddable, compareTo -> object null");
		if (obj instanceof SimpleAddable) {
			return value.compareTo(((SimpleAddable) obj).getValue());
		} else {
			throw new IllegalArgumentException("input's type is not correct");
		}
	}

	@Override
	public final SimpleAddable plus(Addable a) {
		Objects.requireNonNull(a, "SimpleAddable, plus -> object null");
		if (a instanceof SimpleAddable) {
			return SimpleAddable.of(value.add(((SimpleAddable) a).value));
		} else {
			throw new IllegalArgumentException("input's type is not correct");
		}
	}

	@Override
	public final boolean equals(Object other) {
		if (other == null || getClass() != other.getClass())
			return false;
		return value.equals(((SimpleAddable) other).value);
	}

	// for testing
	@Override
	public final String toString() {
		return value.toString();
	}
}
