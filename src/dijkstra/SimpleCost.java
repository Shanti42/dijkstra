package dijkstra;

import java.math.BigInteger;
import java.util.Objects;

public class SimpleCost implements Cost{
	
	private final BigInteger cost;

	public SimpleCost(BigInteger cost) {
		Objects.requireNonNull(cost, "cost cannot be null");
		this.cost = cost;
	}

	@Override
	public boolean isKnown() {
		return cost != null;
	}

	@Override
	public BigInteger cost() {
		return cost;
	}

	// receives another BigInteger healthPoint
	// the healthier the player is, the low cost there will be
	@Override
	public BigInteger cost(Object healthPoint) {
		Objects.requireNonNull(cost, "health point cannot be null");
		if (healthPoint instanceof BigInteger) {
			return cost.divide((BigInteger) healthPoint);
		}
		else {
			throw new IllegalArgumentException("health point is not valid");
		}
	}

	// plus another cost
	@Override
	public Cost plus(Object cost) {
		Objects.requireNonNull(cost, "cost cannot be null");
		if (cost instanceof Cost) {
			return new SimpleCost(((Cost) cost).cost().add(this.cost));
		}
		else {
			throw new IllegalArgumentException("cost is not in valid type");
		}
	}

	@Override
	public int compareTo(Object other) {
		Objects.requireNonNull(other, "input cannot be null");
		if (other instanceof Cost) {
			return cost.compareTo(((Cost) other).cost());
		}
		else {
			throw new IllegalArgumentException("input is not valid");
		}
	}
	
}
