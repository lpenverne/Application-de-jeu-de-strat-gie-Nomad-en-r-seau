package nomad.com.client.concrete;

import nomad.com.client.controller.ComClientController;
import nomad.com.client.message.UserConnectedMessage;
import nomad.common.data_structure.User;
import nomad.common.interfaces.com.ComToDataInterface;

import java.util.UUID;

public class ComClientToDataConcrete implements ComToDataInterface {
    public ComClientController clientController;

    public void setController(ComClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * addConnectedUser inform the server of the connection of the local user.
     * This method is called by data upon completion of the login form.
     * It must initialize the socket, it is the first communication client/server.
     *
     * @param user The local connected user
     * */
    @Override
    public void addConnectedUser(User user) {
        clientController.setCurrentUser(user);
        clientController.initSocket();
        clientController.sendServerMessage(
                new UserConnectedMessage(user)
        );
    }

    @Override
    public void logout(User user) {
        //envoie (via le controller) un message de deconnexion au serveur
    }

    @Override
    public void askForSave(UUID game) {
        //TODO
    }


    @Override
    public void getProfile(UUID idUser) {
        //TODO
    }
}
