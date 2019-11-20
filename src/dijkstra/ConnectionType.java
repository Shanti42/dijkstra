package dijkstra;

import java.util.Objects;

/**
 * Represents the fare class of a certain seat class
 */
class ConnectionType {

    //identifies the fare class
    private final String identifier;

    private ConnectionType(String identifier) {
        this.identifier = identifier;
    }

    static final ConnectionType of(String identifier) {
        Objects.requireNonNull(identifier, "ConnectionType - of(), null identifier parameter provided");

        return new ConnectionType(identifier);
    }

    String getIdentifier() {
        return identifier;
    }


    @Override
    public int hashCode() {
        return identifier.hashCode();
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
