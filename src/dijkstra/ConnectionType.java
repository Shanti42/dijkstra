//shanti

package dijkstra;

import java.util.Objects;

/**
 * Represents the type of a connection
 */
public class ConnectionType {

    //identifies the connection type
    private final String identifier;

    public ConnectionType(String identifier) {
        this.identifier = identifier;
    }

    public static ConnectionType of(String identifier) {
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
        if (object instanceof ConnectionType) {
            ConnectionType connectionType = (ConnectionType) object;
            return this.getIdentifier() == connectionType.getIdentifier();
        } else {
            return false;
        }
    }
}
