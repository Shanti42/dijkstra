package dijkstra;

import java.math.BigInteger;

public class SimpleCost implements Cost{
	
	private final BigInteger cost;
	public static final Cost UNKNOWN = new SimpleCost(null);

	public SimpleCost(BigInteger cost) {
		this.cost = cost;
	}

	@Override
	public boolean isKnown() {
		return cost != null;
	}

	@Override
	public Cost cost() {
		return null;
	}

	@Override
	public Cost cost(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cost plus(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Object other) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
