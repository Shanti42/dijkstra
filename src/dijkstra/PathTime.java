package dijkstra;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents the arrival time of a passenger at an airport
 */
public final class PathTime implements Comparable<PathTime> {


    private final LocalTime time;

    public static final PathTime UNKNOWN = new PathTime(null);

    public PathTime(LocalTime time) {
        //allow null pathTime
        this.time = time;
    }

    //returns true if path time is known, false otherwise
    public boolean isKnown() {
        return time != null;
    }

    public LocalTime getTime() {
        if (time == null) {
            throw new IllegalStateException("Path time is unknown");
        } else {
            return time;
        }
    }

    public PathTime plus(Duration duration) {
        Objects.requireNonNull(duration, "PathTime, plus() -> Null duration parameter");

        return isKnown() ? new PathTime((LocalTime) time.plus(duration)) : UNKNOWN;
    }

    @Override
    public int compareTo(PathTime other) {
        Objects.requireNonNull(other, "PathTime, compareTo() -> Null parameter for other PathTime");
        if (!this.isKnown() || !other.isKnown()) {
            return Boolean.compare(!this.isKnown(), !other.isKnown());
        } else {
            return time.compareTo(other.getTime());
        }
    }

}
