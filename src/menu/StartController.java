package menu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.SceneController;

public class StartController extends SceneController{
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btLogIn;

    @FXML
    private Button btSignIn;

    @FXML
    void gosignInPage(ActionEvent event) {
    	switchScene(event, "fxml/SignIn.fxml");
    }

    @FXML
    void gologInPage(ActionEvent event) {
    	switchScene(event, "fxml/LogIn.fxml");
    }
    


    @FXML
    void initialize() {
        assert btLogIn != null : "fx:id=\"btLogIn\" was not injected: check your FXML file 'StartMenu.fxml'.";
        assert btSignIn != null : "fx:id=\"btSignIn\" was not injected: check your FXML file 'StartMenu.fxml'.";

    }

}
