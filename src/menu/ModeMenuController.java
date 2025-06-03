package menu;

import java.net.URL;
import fileManager.UserFileManager;
import java.util.ResourceBundle;
import main.*;
import fileManager.UserFileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.SceneController;

public class ModeMenuController extends SceneController{
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btAutomaticMode;

    @FXML
    private Button btConfirmDeposit;

    @FXML
    private Button btManualMode;
    
    @FXML
    private Button btLogOut;
    
    @FXML
    private Button btClose;

    @FXML
    private TextField inpCardNumber;

    @FXML
    private TextField inpCardOwner;

    @FXML
    private TextField inpCvv;

    @FXML
    private TextField inpDepositBalance;

    @FXML
    private Label lbCurrentBalance;

    @FXML
    private Label lbEmptyFields;

    @FXML
    private Label lbNonValidData;

    @FXML
    private Label lbOperationConfirmed;
    
    @FXML
    private Label lbUsername;
    
    UserFileManager fileManager = new UserFileManager();

    @FXML
    void ConfirmDeposit(ActionEvent event) {
    	lbOperationConfirmed.setVisible(false);
    	if (inpCardNumber.getText()!="" && inpCardOwner.getText()!="" && inpCvv.getText()!="" && inpDepositBalance.getText()!="") {
    		//all fields compiled
    		lbEmptyFields.setVisible(false);
    		
    		try {
    			lbNonValidData.setVisible(false);
    			UserFileManager manageFiles = new UserFileManager();
    			if (isValidCreditCard(inpCardNumber.getText()) && isValidCVV(inpCvv.getText())) {
    				Integer.parseInt(inpDepositBalance.getText());
        			
        			//if we have all the correct data
        			manageFiles.appendTextToFile("Credit card infos: ", "playerData.txt");
        			manageFiles.appendTextToFile("card number: " + inpCardNumber.getText(), "playerData.txt");
        			manageFiles.appendTextToFile("card owner: " + inpCardOwner.getText(), "playerData.txt");
        			manageFiles.appendTextToFile("last beposit: $ " + inpDepositBalance.getText(), "playerData.txt");
        			
        			//add money to player's balance
        			Main.player.setMoney((Main.player.getMoney() + Integer.parseInt(inpDepositBalance.getText())));
        			lbCurrentBalance.setText("$ " + Main.player.getMoney().toString()); //update current balance label 
        			
        			//update money file
        			manageFiles.saveToFile(Main.player.getMoney().toString(), "money.txt");
        			
        			//reset all input fields
        			inpCardNumber.setText("");
        			inpCardOwner.setText("");
        			inpCvv.setText("");
        			inpDepositBalance.setText("");
        			
    				lbOperationConfirmed.setVisible(true);		
				}else {
					lbNonValidData.setVisible(true);
				}
    			
			} catch (Exception e) {
				lbNonValidData.setVisible(true);
			}
    		
		}else {
			lbEmptyFields.setVisible(true);
		}
    }

    // Method to validate the credit card number
    public static boolean isValidCreditCard(String cardNumber) {
        // Check if the string is null or empty
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        // Remove any spaces or non-numeric characters
        cardNumber = cardNumber.replaceAll("[^0-9]", "");

        // Check if the string contains only digits and has a valid length (13 to 19 digits)
        if (!cardNumber.matches("\\d{13,19}")) {
            return false;
        }

        // Exclude American Express cards (start with 34 or 37 and are 15 digits long)
        if (cardNumber.length() == 15 && (cardNumber.startsWith("34") || cardNumber.startsWith("37"))) {
            return false;
        }

        // Perform Luhn algorithm check
        return luhnCheck(cardNumber);
    }
    
    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }
    
    // Method to check if the given string is a valid CVV
    public static boolean isValidCVV(String cvv) {
        // Check if the string is null or empty
        if (cvv == null || cvv.isEmpty()) {
            return false;
        }
        // Remove any spaces or non-numeric characters
        cvv = cvv.replaceAll("[^0-9]", "");
        // Check if the CVV consists only of digits
        if (!cvv.matches("\\d+")) return false;
        // Validate CVV length (only mastercard)
        return cvv.length() == 3;
    }
    
    // Method to check if a num is valid
    
    
    @FXML
    void playAutomaticMode(ActionEvent event) {
    	switchScene(event, "/autoMode/fxml/AutoGame.fxml");
    }

    @FXML
    void playManualMode(ActionEvent event) {
    	//go to game choice menu
    	switchScene(event, "fxml/GameChoiceMenu.fxml"); // change into game mode choice
    }
    
    @FXML
    void logOut(ActionEvent event) {
    	//save all data
    	fileManager.updateFileContent(Main.player.getMoney().toString(), "money.txt");
        fileManager.updateFileContent(Main.player.getGamesPlayed().toString(), "gamesPlayed.txt");
        fileManager.updateFileContent(Main.player.getGamesWon().toString(), "gamesWon.txt");
        fileManager.updateFileContent(Main.player.getGamesLost().toString(), "gamesLost.txt");
        fileManager.updateFileContent(Main.player.getHighestEarning().toString(), "highestEarning.txt");
    	
    	
    	//go to start menu
    	switchScene(event, "fxml/StartMenu.fxml"); 
    }
    
    @FXML
    void close(ActionEvent event) {
    	//save all data
    	fileManager.updateFileContent(Main.player.getMoney().toString(), "money.txt");
        fileManager.updateFileContent(Main.player.getGamesPlayed().toString(), "gamesPlayed.txt");
        fileManager.updateFileContent(Main.player.getGamesWon().toString(), "gamesWon.txt");
        fileManager.updateFileContent(Main.player.getGamesLost().toString(), "gamesLost.txt");
        fileManager.updateFileContent(Main.player.getHighestEarning().toString(), "highestEarning.txt");
    	
    	javafx.application.Platform.exit();
    }
    
    //getter methods
    public TextField getInpCardNumber() {return inpCardNumber;}
    public TextField getInpCardOwner() {return inpCardOwner;}
    public TextField getInpCvv() {return inpCvv;}
    public TextField getInpDepositBalance() {return inpDepositBalance;}
    public Label getLbCurrentBalance() {return lbCurrentBalance;}
    public Label getLbEmptyFields() {return lbEmptyFields;}
    public Label getLbNonValidData() {return lbNonValidData;}
    public Label getLbOperationConfirmed() {return lbOperationConfirmed;}
    

    @FXML
    void initialize() {
    	lbCurrentBalance.setText("$ " + Main.player.getMoney().toString());
    	lbUsername.setText(Main.player.getName());
        assert btAutomaticMode != null : "fx:id=\"btAutomaticMode\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert btClose != null : "fx:id=\"btClose\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert btConfirmDeposit != null : "fx:id=\"btConfirmDeposit\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert btManualMode != null : "fx:id=\"btManualMode\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert btLogOut != null : "fx:id=\"btLogOut\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert inpCardNumber != null : "fx:id=\"inpCardNumber\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert inpCardOwner != null : "fx:id=\"inpCardOwner\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert inpCvv != null : "fx:id=\"inpCvv\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert inpDepositBalance != null : "fx:id=\"inpDepositBalance\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert lbCurrentBalance != null : "fx:id=\"lbCurrentBalance\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert lbEmptyFields != null : "fx:id=\"lbEmptyFields\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert lbNonValidData != null : "fx:id=\"lbNonValidData\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert lbOperationConfirmed != null : "fx:id=\"lbOperationConfirmed\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        assert lbUsername != null : "fx:id=\"lbUusername\" was not injected: check your FXML file 'ModeMenu.fxml'.";
        
    }

}
