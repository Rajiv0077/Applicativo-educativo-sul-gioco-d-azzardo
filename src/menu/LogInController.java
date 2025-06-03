package menu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.SceneController;
import javafx.scene.control.Label;

public class LogInController extends SceneController{

	private int shift = 3; //shift for encrypting and decrypting psw
	private GameAccess gameAccess = new GameAccess();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btBack;

    @FXML
    private Button btLogIn;

    @FXML
    private Label lbUserNotFound;
    
    @FXML
    private Label lbWrongPsw;
    
    @FXML
    private Label lbEmptyFields;
    
    @FXML
    private TextField inpPsw;

    @FXML
    private TextField inpUsername;

    @FXML
    void goBack(ActionEvent event) {
    	//go to StartMenu
    	switchScene(event, "fxml/StartMenu.fxml");
    }

    @FXML
    void logIn(ActionEvent event) {
    	if (gameAccess.logIn(this)) {
    		switchScene(event, "fxml/ModeMenu.fxml");
		}
    }
    
    
    //getter methods
    public TextField getInpPsw() {return inpPsw;}
    public TextField getInpUsername() {return inpUsername;}
    public Label getLbEmptyFields() {return lbEmptyFields;}
    public Label getLbUserNotFound() {return lbUserNotFound;}
    public Label getLbWrongPsw() {return lbWrongPsw;}
    public int getShift() {return shift;}

    @FXML
    void initialize() {
        assert btBack != null : "fx:id=\"btBack\" was not injected: check your FXML file 'LogIn.fxml'.";
        assert btLogIn != null : "fx:id=\"btLogIn\" was not injected: check your FXML file 'LogIn.fxml'.";
        assert inpPsw != null : "fx:id=\"inpPsw\" was not injected: check your FXML file 'LogIn.fxml'.";
        assert inpUsername != null : "fx:id=\"inpUsername\" was not injected: check your FXML file 'LogIn.fxml'.";
        assert lbUserNotFound != null : "fx:id=\"lbUserNotFound\" was not injected: check your FXML file 'LogIn.fxml'.";
        assert lbWrongPsw != null : "fx:id=\"lbWrongPsw\" was not injected: check your FXML file 'LogIn.fxml'.";
        assert lbEmptyFields != null : "fx:id=\"lbEmptyFields\" was not injected: check your FXML file 'LogIn.fxml'.";

    }

}
