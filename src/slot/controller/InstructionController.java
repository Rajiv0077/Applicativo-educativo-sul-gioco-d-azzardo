package slot.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InstructionController {
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblInstruction;
    
    @FXML
    public void closeInstruction(ActionEvent e) {
    	
    	 Stage s = (Stage) (((Node) e.getSource()).getScene().getWindow()); 
    	
    	
    		          s.close();
    		
    	
    	
    }
    

    @FXML
    void initialize() {
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'Instruction.fxml'.";
        assert lblInstruction != null : "fx:id=\"lblInstruction\" was not injected: check your FXML file 'Instruction.fxml'.";

    }
}