package nomad.main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import nomad.common.data_structure.Game;
import nomad.common.data_structure.UserException;
import nomad.common.ihm.IhmControllerComponent;
import nomad.main.IhmMainScreenController;
import java.awt.image.BufferedImage;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;


public class ModifyProfileController extends IhmControllerComponent {
    private final IhmMainScreenController ihmController;
    @FXML
    public ListView<Game> savedGamesView;
    private ObservableList<Game> savedGames;
    DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    public Label win;
    @FXML
    public Label draws;
    @FXML
    public Label total;
    @FXML
    public Label loses;
    @FXML
    public Label ratio;
    @FXML
    public PasswordField password;
    @FXML
    public TextField name;
    @FXML
    public Label labelLogIn;
    @FXML
    public Label idPlayer;
    @FXML
    public TextField serverID;
    @FXML
    public TextField portID;
    @FXML
    public TextField login;
    @FXML
    public DatePicker birthDate;
    @FXML
    public ImageView profilePicture;


    /**
     * Constructor that link the screen controller to the component controller
     *
     * @param screen
     */
    public ModifyProfileController(IhmMainScreenController screen) {
        super(screen);
        this.ihmController = screen;
    }

    public void start() {
        int winInf = this.ihmController.getDataI().getUser().getProfileStat().getGamesWon();
        int lostInf = this.ihmController.getDataI().getUser().getProfileStat().getGamesLost();
        int totalInf = this.ihmController.getDataI().getUser().getProfileStat().getGamesPlayed();
        int drawInf = totalInf - lostInf - winInf;

        byte[] byteArray = decodeImage(this.ihmController.getDataI().getUser().getProfilePicture());

        if(byteArray != null && byteArray.length > 0) {

            ByteArrayInputStream bis = new ByteArrayInputStream(decodeImage(this.ihmController.getDataI().getUser().getProfilePicture()));
            BufferedImage img = null;

            try {
                img = ImageIO.read(bis);
                //ImageIcon im = new ImageIcon(decodeImage(this.ihmController.getDataI().getUser().getProfilePicture()));
                // java.awt.Image image =  im.getImage();
                profilePicture.setImage(convertToFxImage(img));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        win.setText("" + winInf);

        loses.setText("" + lostInf);
        draws.setText("" + drawInf);
        if (totalInf > 0) {
            ratio.setText("" + winInf / totalInf);
        } else {
            ratio.setText("1.00");
        }
        total.setText("" + totalInf);
        displayInfoUser();
        displaySavedGames();
    }

    public static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public void displayInfoUser() {
        labelLogIn.setText(this.ihmController.getDataI().getUser().getLogin());
        idPlayer.setText("#" + this.ihmController.getDataI().getUser().getUserId());
        login.setText(this.ihmController.getDataI().getUser().getLogin());
        name.setText(this.ihmController.getDataI().getUser().getName());
        serverID.setText("" + ihmController.getDataI().getUser().getLastServer().getIpAddress().toString().substring(1));
        portID.setText("" + this.ihmController.getDataI().getUser().getLastServer().getPort());
        if (this.ihmController.getDataI().getUser().getBirthDate() != null) {
            LocalDate localDateBirth = this.ihmController.getDataI().getUser().getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            birthDate.setValue(localDateBirth);
        }
    }

    public void displaySavedGames() {
        savedGames = FXCollections.observableArrayList(this.ihmController.getDataI().getUser().getSavedGames());
        if (savedGamesView!=null) savedGamesView.setItems(savedGames);
    }

    public void onClickReturnMenu() {
        this.ihmController.changeScreen(MenuController.class);
    }

    public void onClickDeleteProfile() {
       // this.ihmController.getDataI().deleteAccount();
    }

    public void onClickSignOut() {
        this.ihmController.getDataI().logout();
        this.ihmController.changeScreen(LoginController.class);
    }


    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

    private String profilePictureStr;

    public String getProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(this.ihmController.getStage());
        try (FileInputStream fs = new FileInputStream(file)) {
            byte[] imageData = new byte[(int) file.length()];

            if (fs.read(imageData) > 0) {
                Image img = new Image(file.toURI().toString());
                profilePicture.setImage(img);
                profilePictureStr = encodeImage(imageData);
                return profilePictureStr;
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
        return "";
    }

    public void onClickModifyProfile() throws IOException {
        //TODO Finish to implement
        // tout les champs vides il faut recuperer ont passe null
        String passwordTest;
        if (password.getText().equals("")) {
            passwordTest = null;
        } else {
            passwordTest = password.getText();
        }
        if (birthDate.getValue() == null) {
            DialogController.display("Erreur", "La date d'anniversaire ne doit pas être nulle", DialogController.DialogStatus.ERROR, ihmController);
            return;
        }
        Date dateBirth = Date.from(birthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(!ServerConnectionController.ipFormatIsValid(serverID.getText()) || !ServerConnectionController.portFormatIsValid(portID.getText())){
            DialogController.display("Erreur", "Le format IP/Port n'est pas valide", DialogController.DialogStatus.ERROR, ihmController);
            return;
        }

        try {
            this.ihmController.getDataI().modifyAccount(login.getText(), passwordTest, name.getText(), profilePictureStr, dateBirth);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
            DialogController.display("Modify User", "Le profil n'a pas bien été modifié", DialogController.DialogStatus.ERROR, this.ihmController);
        }
        DialogController.display("Modify User", "Le profil a bien été modifié", DialogController.DialogStatus.SUCCESS, this.ihmController);
    }

    public void onClickExportProfile() {
        //TODO Waiting for Data Implementation of exportPlayer
        directoryChooser.setTitle("Export profile");
        File file = directoryChooser.showDialog(this.ihmController.getStage());
        try {
            ihmController.getDataI().exportProfile(file.getPath());
            DialogController.display("Export profil", "Le profil a bien été exporté", DialogController.DialogStatus.SUCCESS, this.ihmController);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
    }
}
