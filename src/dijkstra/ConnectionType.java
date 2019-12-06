// public because people will need to create instances of them when they're creating connections
// final so that people can't extend it

package dijkstra;

import java.util.Objects;

/**
 * Represents the type of a connection
 */
public final class ConnectionType {

    //identifies the connection type
    private final String identifier;

    /**
     * Creates a new ConnectionType object with the given identifier
     *
     * @param identifier identifies the connection type
     */
    public ConnectionType(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Creates a new ConnectionType with the given identifier
     *
     * @param identifier
     * @return a new ConnectionType with the given identifier
     * @throws NullPointerException if the given identifier string is null
     */
    public static ConnectionType of(String identifier) {
        Objects.requireNonNull(identifier, "ConnectionType - of(), null identifier parameter provided");

        return new ConnectionType(identifier);
    }

    /**
     * Returns the identifier of the ConnectionType
     *
     * @return the identifier of the ConnectionType
     */
    String getIdentifier() {
        return identifier;
    }


    /**
     * Returns the hashcode of the ConnectionType
     *
     * @return the hashcode of the connection type
     */
    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    /**
     * Compares this ConnectionType to the given object
     *
     * @param object the object being compared to this ConnectionType
     * @return true if the ConnectionType is equal to the object, false otherwise
     */
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
