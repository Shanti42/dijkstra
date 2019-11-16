package dijkstra;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public interface Cost extends Comparable{


    //returns true if route time is known, false otherwise
    boolean isKnown();

    LocalTime cost(Object object);

    RouteTime plus(Object object);


    int compareTo(Object other);

    static Cost of(LocalTime time){
        return null;
    }
}
