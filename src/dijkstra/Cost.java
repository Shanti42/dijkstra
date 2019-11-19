package dijkstra;

import java.math.BigInteger;
import java.time.LocalTime;

public abstract class Cost extends Comparable{

    public static final Cost UNKNOWN = null;
    BigInteger costValue;

    public Cost(LocalTime time){
        costValue = new BigInteger(time.getHour());
    }

    //returns true if route time is known, false otherwise
    abstract boolean isKnown();

    //Returns a cost based on private variables only
    abstract LocalTime cost();

    //Return a cost based on the input and private variable(s)
    abstract LocalTime cost(Object object);

    //returns a new cost after adding the cost that corresponds with the object
    abstract PathTime plus(Object object);

    public abstract int compareTo(Object other);
}
