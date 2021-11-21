package nomad.com.common;

import nomad.common.data_structure.User;

/**
 * Message broadcasted by the server to all users to update a change of user
 */
public class UserChangedMessage extends ComMessage {
    /**
     * The user concerned by changes
     */
    public final User user;

    /**
     * True for new user, false for logged out user
     */
    public final boolean isConnected;

    /**
     * Constructor
     * @param user The user (dis)connecting
     * @param isConnected True for a connection, false for a disconnection
     */
    public UserChangedMessage(User user, boolean isConnected) {
        this.user = user;
        this.isConnected = isConnected;
    }
}