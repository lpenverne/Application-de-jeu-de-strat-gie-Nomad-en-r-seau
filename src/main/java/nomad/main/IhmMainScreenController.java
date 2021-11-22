package nomad.main;

import nomad.common.ihm.IhmScreenController;
import nomad.common.MainApplication;
import nomad.common.interfaces.data.DataToIhmMainInterface;
import nomad.main.controller.ServerConnectionController;
import nomad.main.controller.MenuController;
import nomad.main.controller.LoginController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class IhmMainScreenController extends IhmScreenController {

    private Map<String, String> attributes;
    private DataToIhmMainInterface dataI;
    private final String stylesheet = "Poppins/style.css";

    public IhmMainScreenController(MainApplication app, DataToIhmMainInterface dataI) throws IOException {
        super(app);
        module = "MAIN";
        defaultStart = 0;
        attributes = new HashMap<>();
        initScenes();
        this.dataI = dataI;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public DataToIhmMainInterface getDataI() {
        return dataI;
    }

    public void initPaths() {
        listPaths.add("fxml/ihm_login_connection.fxml");
        listPaths.add("fxml/ihm_server_connection.fxml");
        listPaths.add("fxml/ihm_menu.fxml");
    }

    @Override
    public void initController() {
        dictController.put(0, new LoginController(this));
        dictController.put(1, new ServerConnectionController(this));
        dictController.put(2, new MenuController(this));

    }

    public void initStyles() {
        dictStyles.put(0, stylesheet);
        dictStyles.put(2, stylesheet);
        dictStyles.put(1, stylesheet);
    }
}
