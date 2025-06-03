package menu;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.SceneController;

public class SignInController extends SceneController{
	private int shift = 3; //shift for encrypting and decrypting psw
	private GameAccess gameAccess = new GameAccess(); 
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btBack;

    @FXML
    private Button btSignIn;

    @FXML
    private TextField inpPsw1;

    @FXML
    private TextField inpPsw2;

    @FXML
    private TextField inpUsername;

    @FXML
    private Label lbPswError;

    @FXML
    private Label lbUsernameError;

    @FXML
    private Label lbEmptyFields;

    @FXML
    void goBack(ActionEvent event) {
    	//go to start menu
    	switchScene(event, "fxml/StartMenu.fxml");

    }

    @FXML
    void signIn(ActionEvent event) {  
    	if (gameAccess.signIn(this)) {
    		switchScene(event, "fxml/ModeMenu.fxml"); // change into game mode choice
    	}
    }

    
    //getter methods
    public TextField getInpPsw1() {return inpPsw1;}
    public TextField getInpPsw2() {return inpPsw2;}
    public TextField getInpUsername() {return inpUsername;}
    public Label getLbEmptyFields() {return lbEmptyFields;}
    public Label getLbPswError() {return lbPswError;}
    public Label getLbUsernameError() {return lbUsernameError;}
    public int getShift() {return shift;}
    
    
    @FXML
    void initialize() {
        assert btBack != null : "fx:id=\"btBack\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert btSignIn != null : "fx:id=\"btSignIn\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert inpPsw1 != null : "fx:id=\"inpPsw1\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert inpPsw2 != null : "fx:id=\"inpPsw2\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert inpUsername != null : "fx:id=\"inpUsername\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert lbPswError != null : "fx:id=\"lbPswError\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert lbUsernameError != null : "fx:id=\"lbUsernameError\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert lbEmptyFields != null : "fx:id=\"lbEmptyFields\" was not injected: check your FXML file 'SignIn.fxml'.";

    }

}

