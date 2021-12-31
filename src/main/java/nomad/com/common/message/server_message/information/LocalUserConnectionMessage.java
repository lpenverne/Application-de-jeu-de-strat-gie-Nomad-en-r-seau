package nomad.com.common.message.server_message.information;

import nomad.com.common.message.client_message.information.LobbyInformationMessage;
import nomad.com.common.message.client_message.information.UserChangedMessage;
import nomad.com.common.message.server_message.BaseServerMessage;
import nomad.com.server.ServerController;
import nomad.common.data_structure.GameLight;
import nomad.common.data_structure.Player;
import nomad.common.data_structure.User;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Message sent to connect local user on the server
 */
public class LocalUserConnectionMessage extends BaseServerMessage {
    /**
     * Instance of user
     */
    public final User user;

    /**
     * Constructor of LocalUserConnectionMessage class
     * @param user Id of the user we want to send the message to.
     */
    public LocalUserConnectionMessage(User user) {
        this.user = user;
    }

    /**
     * Update the list of connected users
     * @param socket Socket of the client app
     * @param controller Instance of the server controller
     */
    @Override
    public void process(Socket socket, ServerController controller) {


            controller.broadcast(new UserChangedMessage(user, true));

            //Send information on games and users to newly connected client
            LobbyInformationMessage informationMessage = new LobbyInformationMessage(
                    (ArrayList<Player>) controller.getDataToCom().requestConnectedUserList(),
                    (ArrayList<GameLight>) controller.getDataToCom().requestGameListInLobby(),
                    (ArrayList<GameLight>) controller.getDataToCom().requestGameListInPlay()
            );
            controller.sendMessage(socket, informationMessage);


            //Register a new connected client on the server
            controller.getDataToCom().updateUserListAdd(user);
            controller.registerUser(socket, user);

    }
}
