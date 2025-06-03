package menu;

import java.io.File;
import main.*;
import fileManager.*;


public class GameAccess {
	private UserFileManager userFile = new UserFileManager();
	private PswEncrypting pswManager = new PswEncrypting(userFile);
	
	
	
	public Boolean signIn(SignInController signInController) {
		//get infos from the fxml
    	String username = signInController.getInpUsername().getText();
		String psw1 = signInController.getInpPsw1().getText();
    	String psw2 = signInController.getInpPsw2().getText();
    	
    	if (username!="" && psw1!="" && psw2!="") {
    		//no empty fields
    		signInController.getLbEmptyFields().setVisible(false);
    		
    		userFile.setDirectoryPath(UserFileManager.getAccountPath() + "\\" + username);
        	File playerDirectory = new File(userFile.getUserPath());        	
        	
        	if (playerDirectory.exists()) {
        		//user name already used
        		signInController.getLbUsernameError().setVisible(true);
        		return false;
    		}else {
    			signInController.getLbUsernameError().setVisible(false);
    	    	if (psw1.equals(psw2)) {
    	    		if (isValidPassword(psw1, username)) {
    	    			//sign in done successfully
        	    		
        	    		signInController.getLbPswError().setVisible(false);
        	    		playerDirectory.mkdirs(); //create the directory for new user
        				pswManager.cifraCesare(psw1, signInController.getShift()); //encrypt the psw
        				
        				Main.player = new Player(username, 200, 0, 0, 0, 0); //assign values to Player attributes. Gives 200 $ as a starting bonus

        				//create all the necessary files for the new user. These are then used when logging in 
        				userFile.saveToFile("", "chart.txt"); //for the chart
        				userFile.saveToFile(Main.player.getMoney().toString(), "money.txt");
        				userFile.saveToFile("0", "gamesPlayed.txt"); //total
        				userFile.saveToFile("0", "gamesWon.txt"); 
        				userFile.saveToFile("0", "gamesLost.txt");
        				userFile.saveToFile("0", "highestEarning.txt"); //record for highest earning
        				userFile.saveToFile("username: " + username, "playerData.txt"); //general account data file
        				
        				return true;
					}else {
						
						signInController.getLbPswError().setText("Password does not meet the requirements: min 8 char; at least 1 number and 1 letter; at least 1 special character");
						//signInController.getInpPsw1().setPromptText("min 8 char; at least 1 number and 1 letter; at least 1 special character");
						//signInController.getLbEmptyFields().setText("min 8 char; at least 1 number and 1 letter; at least 1 special character");
						
			            signInController.getLbPswError().setVisible(true);
			            //signInController.getLbEmptyFields().setVisible(true);
			            return false;
					}
    	    		
    	    		
    			}else {
    				//non matching psw
    				signInController.getLbPswError().setText("Passwords do not match");
    				signInController.getLbPswError().setVisible(true);
    				return false;
    			}
    		}
		}else {
			signInController.getLbEmptyFields().setVisible(true);
			return false;
		}
    	
	}
	
	public Boolean logIn(LogInController logInController) {  
		//get infos from the fxml
    	String username = logInController.getInpUsername().getText();
    	String psw = logInController.getInpPsw().getText();

    	if (username!="" && psw!="") {
    		//no empty fields
    		logInController.getLbEmptyFields().setVisible(false);
    		logInController.getLbUserNotFound().setVisible(false);
			logInController.getLbWrongPsw().setVisible(false);
    		
    		userFile.setDirectoryPath(UserFileManager.getAccountPath() + "\\" + username);
        	File playerDirectory = new File(userFile.getUserPath());
        	
        	if (playerDirectory.exists()) {
        		logInController.getLbUserNotFound().setVisible(false);
        		if (psw.equals(pswManager.decifraCesare(logInController.getShift()))) {
        			//log-in completed successfully
        			logInController.getLbWrongPsw().setVisible(false);
        			System.out.println("log-in completed successfully");
        			
        			//assign values to Player attributes getting them from directory userFiles/username
        			Main.player = new Player(username,
        				    userFile.readFromFile("money.txt").matches("-?\\d+") ? Integer.parseInt(userFile.readFromFile("money.txt")) : 0,
        				    userFile.readFromFile("gamesPlayed.txt").matches("-?\\d+") ? Integer.parseInt(userFile.readFromFile("gamesPlayed.txt")) : 0,
        				    userFile.readFromFile("gamesWon.txt").matches("-?\\d+") ? Integer.parseInt(userFile.readFromFile("gamesWon.txt")) : 0,
        				    userFile.readFromFile("gamesLost.txt").matches("-?\\d+") ? Integer.parseInt(userFile.readFromFile("gamesLost.txt")) : 0,
        				    userFile.readFromFile("highestEarning.txt").matches("-?\\d+") ? Integer.parseInt(userFile.readFromFile("highestEarning.txt")) : 0);
        			
        			return true;
    			}else {
    				logInController.getLbWrongPsw().setVisible(true);
    				return false;
    			}    		
    		}else {
    			logInController.getLbUserNotFound().setVisible(true);
    			return false;
    		}
		}else {
			logInController.getLbEmptyFields().setVisible(true);
			return false;
		}
    	
	}
	
	private boolean isValidPassword(String password, String username) {
	    // Check password length (minimum 8 characters)
	    if (password.length() < 8) {
	        return false;
	    }
	    // Check if password contains at least one letter and one number
	    if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
	        return false;
	    }
	    // Check if password contains at least one special character
	    if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>_-].*")) {
	        return false;
	    }
	    // Check if password is not equal to the username
	    if (password.equalsIgnoreCase(username)) {
	        return false;
	    }
	    return true;
	}
}
