package dijkstra;

import java.util.Objects;

/**
 * Represents the fare class of a certain seat class
 */
public final class FareClass {

    //identifies the fare class
    private final int identifier;

    private FareClass(int identifier) {
        this.identifier = identifier;
    }

    public static final FareClass of(int identifier) {
        Objects.requireNonNull(identifier, "FareClass - of(), null identifier parameter provided");

        return new FareClass(identifier);
    }

    public int getIdentifier() {
        return identifier;
    }


    @Override
    public int hashCode() {
        return identifier;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof FareClass) {
            FareClass fareClass = (FareClass) object;
            return this.getIdentifier() == fareClass.getIdentifier();
        }
        return false;
    }
}
