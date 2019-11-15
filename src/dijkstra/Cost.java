package dijkstra;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public interface Cost extends Comparable{


    //returns true if route time is known, false otherwise
    boolean isKnown();

    LocalTime cost(Object object);

    RouteTime plus(Object object);

    @Override
    public int compareTo(RouteTime other) {
        Objects.requireNonNull(other, "RouteTime, compareTo() -> Null parameter for other RouteTime");
        if (!this.isKnown() || !other.isKnown()) {
            return Boolean.compare(!this.isKnown(), !other.isKnown());
        } else {
            return routeTime.compareTo(other.getTime());
        }
    }

}




    Cost of(LocalTime time);
}
