package dijkstra;

import java.util.Objects;

/**
 * Represents the fare class of a certain seat class
 */
class ConnectionType {

    //identifies the fare class
    private final int identifier;

    private ConnectionType(int identifier) {
        this.identifier = identifier;
    }

    static final ConnectionType of(int identifier) {
        Objects.requireNonNull(identifier, "ConnectionType - of(), null identifier parameter provided");

        return new ConnectionType(identifier);
    }

    int getIdentifier() {
        return identifier;
    }


    @Override
    public int hashCode() {
        return identifier;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof ConnectionType) {
            ConnectionType connectionType = (ConnectionType) object;
            return this.getIdentifier() == connectionType.getIdentifier();
        }
        return false;
    }
}
