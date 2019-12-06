// this is public because it's an interface

package dijkstra;

/**
 * Represents an object that can be added and compared
 */
public interface Addable extends Comparable {


    /**
     * Compares an addable to another addable.
     * An addable can be compared by internal values it holds
     *
     * @param other the other addable being compared
     * @return -1, 0 or 1 if this addable is less than, equal to, or greater than the other addable
     */
    @Override
    int compareTo(Object other);

    /**
     * Adds this addable to the given addable and returns a new addable, which is the result of the addition.
     *
     * @param addable the other addable being added
     * @return the addable resulting from this addable and the given addable
     */
    Addable plus(Addable addable);
}
