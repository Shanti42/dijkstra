package dijkstra;

import java.math.BigInteger;
import java.time.LocalTime;

interface Cost extends Comparable{


    //returns true if route time is known, false otherwise
   boolean isKnown();

    //Returns a cost based on private variables only
    LocalTime cost();

    //Return a cost based on the input and private variable(s)
    LocalTime cost(Object object);

    //returns a new cost after adding the cost that corresponds with the object
    PathTime plus(Object object);

    int compareTo(Object other);
}
