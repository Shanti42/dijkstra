//yian
// NOTE: this is public because Cost has a method that uses it (Cost.ZERO return a SimpleAddable)
package dijkstra;

import java.math.BigInteger;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a simple addable object which calculates cost by value of integers
 */
public final class SimpleAddable implements Addable {
    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final BigInteger value;

    /**
     * The private constructor for the SimpleAddable class
     *
     * @param value        represents (BigInteger) cost in this example  
     */
    private SimpleAddable(BigInteger value) {
        this.value = value;
    }

    /**
     * The getter for the internal value of the SimpleAddable instance.
     *
     * @return the internal value
     */
    public final BigInteger getValue() {
        return value;
    }

    /**
     * The public constructor for the SimpleAddable class
     *
     * @param value        represents (int) cost in this example  
     */
    public final static SimpleAddable of(int value) {
        return new SimpleAddable(BigInteger.valueOf(value));
    }

    /**
     * The public constructor for the SimpleAddable class
     *
     * @param value        represents (BigInteger) cost in this example  
     */
    public final static SimpleAddable of(BigInteger value) {
        Objects.requireNonNull(value, "SimpleAddable, of -> value null");
        return new SimpleAddable(value);
    }

    /**
    * Compares one simpleAddable to the other.
    * A simpleAddable can be compared by internal values it holds
    *
    * @param obj the other simpleAddable to be compared
    * @return -1, 0 or 1 if this simpleAddable's value is less than, equal to, or greater than the other's.
    */
    @Override
    public final int compareTo(Object obj) {
        Objects.requireNonNull(obj, "SimpleAddable, compareTo -> object null");
        if (obj instanceof SimpleAddable) {
            return value.compareTo(((SimpleAddable) obj).getValue());
        } else {
            LOGGER.log(Level.SEVERE, "SimpleAddable, compareTo: input type is invalid (doesn't match type)");
            throw new IllegalArgumentException("input's type is not correct");
        }
    }

    /**
     * Returns a new simpleAddable, which is the result of the addition of this simpleAddable and the given simpleAddable.
     * Two simpleAddables can be added by internal values they hold
     *
     * @param a the other simpleAddable to be added
     * @return the simpleAddable resulting from this simpleAddable and the other
     */
    @Override
    public final SimpleAddable plus(Addable a) {
        Objects.requireNonNull(a, "SimpleAddable, plus -> object null");
        if (a instanceof SimpleAddable) {
            return SimpleAddable.of(value.add(((SimpleAddable) a).value));
        } else {
            LOGGER.log(Level.SEVERE, "SimpleAddable, plus: input type is invalid (doesn't match type)");
            throw new IllegalArgumentException("input's type is not correct");
        }
    }

    /**
     * Check whether one simpleAddable and the other are equal.
     * A simpleAddable can be compared by internal values it holds
     *
     * @param other the other simpleAddable to be compared
     * @return true or false if this simpleAddable's value is equal to, or not equal to the other's.
     */
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
