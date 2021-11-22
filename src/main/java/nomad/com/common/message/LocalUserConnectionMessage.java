package nomad.com.common.message;

import nomad.common.data_structure.User;

/**
 * Message sent to connect local user on the server
 */
public class LocalUserConnectionMessage implements ComMessage {
    public final User user;

    public LocalUserConnectionMessage(User user) {
        this.user = user;
    }
}
