package nomad.main.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import nomad.common.data_structure.UserLight;
import nomad.common.ihm.IhmControllerComponent;
import nomad.main.IhmMainScreenController;

import java.util.Observable;
import java.util.Observer;


public class CreateGameController extends IhmControllerComponent {

    @FXML
    public CheckBox random;
    @FXML
    public CheckBox white;
    @FXML
    public CheckBox red;
    @FXML
    public TextField gameName;
    @FXML
    public Slider towerNumber;
    @FXML
    public CheckBox allowViewers;
    @FXML
    public CheckBox allowViewersChat;




    private enum Color {
        RED,
        WHITE,
        RANDOM,
        NULL, //if no color is clicked
    }
    private Color color = Color.NULL;
    /*todo replace boolean with custom enum*/

    //Todo CUstom textfiled for only numbers ( towernumber )


    private IhmMainScreenController ihmMainScreenController;

    /**
     * Constructor that link the screen controller to the component controller
     *
     * @param screen
     */
    public CreateGameController(IhmMainScreenController screen) {
        super(screen);
        this.ihmMainScreenController = screen;


    }



    public void onClickBack() {
        screenControl.changeScreen(2);
    }

    private void resetCheckBoxes() {
        red.setSelected(false);
        white.setSelected(false);
        random.setSelected(false);

    }

    public void onCheckColor(ActionEvent e)
    {
        CheckBox checkbox = (CheckBox) e.getSource();
        String id = checkbox.getId();
        boolean checked = checkbox.isSelected();
        resetCheckBoxes();
        checkbox.setSelected(checked);
        if(!checked)
        {
            color = Color.NULL;
            return;
        }
        switch (id)
        {
            //Todo Uncheck all boxes except the selected
            case "red" :
                color = Color.RED;
                break;

            case "white":
                color = Color.WHITE;
                break;

            case "random" :
                color = Color.RANDOM;
                break;
            default:
                log("Error");
                /*todo throw exception*/

        }

    }

    private void log(String data)
    {

        System.out.println(data);
    }


    public void onCheckAllow() {
        if(allowViewers.isSelected()) {
            allowViewersChat.setDisable(false);
            return;
        }
        allowViewersChat.setDisable(true);
        allowViewersChat.setSelected(false);


    }




    public boolean validate(){
        return color != Color.NULL && !gameName.getText().equals("");
    }

    public void displayWaitingRoom()
    {

        DialogController.display("Partie créée", "Votre partie a bien été créée", DialogController.DialogStatus.SUCCESS, this.ihmMainScreenController);

        this.ihmMainScreenController.changeScreen(4);
    }

    public void onClickCreateGame() {
       if(!validate()) {
            DialogController.display("Formulaire incomplet", "Veuillez renseigner tous les champs", DialogController.DialogStatus.WARNING, this.ihmMainScreenController);
            return;
       }
            log(gameName.getText());
            log(String.valueOf((int)towerNumber.getValue()));
            log(color.name());
            UserLight user = ihmMainScreenController.getDataI().getUserLight();

            //ihmMainScreenController.getComI().newGame(gameName.getText(),user,(int)towerNumber.getValue(),allowViewers.isSelected(),allowViewersChat.isSelected(),color);




    }
    


}
