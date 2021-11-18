package nomad.com.server.controller;

import nomad.com.client.ComClient;
import nomad.com.message.ComMessage;
import nomad.com.server.ComServer;
import nomad.com.server.ComServerListener;
import nomad.com.server.ComServerSender;
import nomad.common.interfaces.data.DataToComServeurInterface;

import java.io.Serializable;
import java.net.Socket;
import java.util.UUID;

public class ComServerController implements Serializable {
    public ComServer server;
    private final DataToComServeurInterface dataToCom;

    public ComServerController(int port, DataToComServeurInterface dataToCom) {
        server = new ComServer(port);
        this.dataToCom = dataToCom;
    }

    public DataToComServeurInterface getDataToCom() {
        return dataToCom;
    }

    public void SendClientMessage(Socket client, ComMessage message) {
        ComServerSender sender = new ComServerSender(client, message);
        sender.start();
    }

    public void requestConnection(ComClient client, ComServerListener listener) {
        UUID userId = client.user.getUserId();
        server.clientList.put(userId, client);
        server.listenerList.put(userId, listener);
    }

    public void requestDisconnection(UUID userId) {
        server.clientList.remove(userId);
        server.listenerList.get(userId).stop();
        server.listenerList.remove(userId);
    }

    public void startServer() {
        server.start();
    }
}
