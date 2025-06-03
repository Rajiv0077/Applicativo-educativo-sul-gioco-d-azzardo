package slot.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.*;
import fileManager.UserFileManager;
import gameChart.GameChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.*;
import menu.*;
import menu.LogInController;
import slot.MoneyManagerSlot;
import slot.SimulationSlot;
import slot.SlotMachine;

public class SlotMachineController extends SceneController {
	
    // Dichiarazioni FXML
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button backButton;
    @FXML private Button btnBegin;
    @FXML private Button btnInstruction;
    @FXML private Button btnRestart;
    @FXML private Button btnChangeSlots;
    @FXML private Button statsButton;
    @FXML private ImageView imgSlot1;
    @FXML private ImageView imgSlot2;
    @FXML private ImageView imgSlot3;
    @FXML private ImageView imgSlot1h;
    @FXML private ImageView imgSlot2h;
    @FXML private ImageView imgSlot3h;
    @FXML private ImageView imgSlot4h;
    @FXML private ImageView imgSlot5h;
    @FXML private ImageView imgSlot6h;
    @FXML private ImageView imgSlot7h;
    @FXML private ImageView imgSlot8h;
    @FXML private ImageView imgSlot9h;
    @FXML private ImageView botAvatar;
    @FXML private Label moneyLabel;
    @FXML private Label botDialogueLabel;
    @FXML private Label lblHowMuchSpend;
    @FXML private Label lblMIUCurrency;
    @FXML private Label lblMLCurrency;
    @FXML private Label lblMWCurrency;
    @FXML private Label lblMoneyInUse;
    @FXML private Label lblMoneyLeft;
    @FXML private Label lblMoneyWon;
    @FXML private Label lblSlot1;
    @FXML private Label lblSlot2;
    @FXML private Label lblSlot3;
    @FXML private Label lblSlot1h;
    @FXML private Label lblSlot3h;
    @FXML private Label lblSlot2h;
    @FXML private Label lblSlot4h;
    @FXML private Label lblSlot6h;
    @FXML private Label lblSlot5h;
    @FXML private Label lblSlot7h;
    @FXML private Label lblSlot8h;
    @FXML private Label lblSlot9h;
    @FXML private Label lblSlotMachine;
    @FXML private Label lblWinLose;
    @FXML private Label lblCounterGames;
    @FXML private Spinner<Integer> spnrMoneySpent;
    @FXML private ListView<Button> lvmodeSelection;
    @FXML private ChoiceBox<String> cbofButtons;
    @FXML private GridPane slotGrid;
    @FXML private Label resultLabel;

    SlotMachine slot = new SlotMachine(
    	    imgSlot1, imgSlot2, imgSlot3,
    	    imgSlot1h, imgSlot2h, imgSlot3h,
    	    imgSlot4h, imgSlot5h, imgSlot6h,
    	    imgSlot7h, imgSlot8h, imgSlot9h
    	);

    private Integer restartCount = Main.getPlayer().getGamesPlayed();
    Integer firstMoney = Main.player.getMoney();
    MoneyManagerSlot moneyManagerSlot = new MoneyManagerSlot();
    Boolean moneyIsZero = false , isGameWon = null;
    int gamePlayed = restartCount+1;
    Popup popup = new Popup(); 
    Label label = new Label("Istruzioni");
    AnchorPane instructionPane = new AnchorPane();
    int contListSetup = 0;
    private GameChart gameChart;
    Player player;
 
    LogInController logInController = new LogInController();
    int gameWonCounter=0, gameLossCounter = 0;
    UserFileManager userFile = new UserFileManager();
    int highestEarning=0;
    private Integer moneySpent = 0;
    private Integer finishMoney = 0;
    private Integer startMoney=firstMoney;
    private Boolean isGameStarted = false; 
    private Integer lastSpinnerValue = 0; 
    
    
    
    
    @FXML
    private void startGame(ActionEvent e) {
    	if(moneySpent > 0 && moneySpent <= startMoney) {
            isGameStarted = true;
            btnBegin.setDisable(true); // Disabilita immediatamente

            
            switch(cbofButtons.getValue()) {
                case "3X1":
                	this.finishMoney = slot.changeSlot3x1(startMoney,  this,
                			imgSlot1,  imgSlot2, imgSlot3,
                			imgSlot1h,  imgSlot2h, imgSlot3h,
                			imgSlot4h, imgSlot5h,  imgSlot6h,
                			imgSlot7h,  imgSlot8h, imgSlot9h,
                			lblSlot1,lblSlot2, lblSlot3,
                			lblSlot1h,  lblSlot2h,  lblSlot3h,
                			lblSlot4h, lblSlot5h,  lblSlot6h,
                			lblSlot7h,  lblSlot8h, lblSlot9h,
                			lblWinLose, moneySpent,  moneyIsZero,
                			lblMWCurrency,  e, lblMLCurrency);
                	
                	
                	
                	 // Salva in una variabile d'istanza
                	
                	endGame();
                	Main.player.setMoney(finishMoney);
                	
                	this.startMoney = finishMoney;
                	
                	break;
                case "3X3":
                	this.finishMoney = slot.changeSlot3x3(startMoney,  this,
                			imgSlot1,  imgSlot2, imgSlot3,
                			imgSlot1h,  imgSlot2h, imgSlot3h,
                			imgSlot4h, imgSlot5h,  imgSlot6h,
                			imgSlot7h,  imgSlot8h, imgSlot9h,
                			lblSlot1,lblSlot2, lblSlot3,
                			lblSlot1h,  lblSlot2h,  lblSlot3h,
                			lblSlot4h, lblSlot5h,  lblSlot6h,
                			lblSlot7h,  lblSlot8h, lblSlot9h,
                			lblWinLose, moneySpent,  moneyIsZero,
                			lblMWCurrency,  e, lblMLCurrency);
                	
                	endGame();
                	Main.player.setMoney(finishMoney);
                	
                	this.startMoney = finishMoney;
                	
                	
                	break;
                case "3X4":
                	this.finishMoney = slot.changeSlot3x4(startMoney,  this,
                			imgSlot1,  imgSlot2, imgSlot3,
                			imgSlot1h,  imgSlot2h, imgSlot3h,
                			imgSlot4h, imgSlot5h,  imgSlot6h,
                			imgSlot7h,  imgSlot8h, imgSlot9h,
                			lblSlot1,lblSlot2, lblSlot3,
                			lblSlot1h,  lblSlot2h,  lblSlot3h,
                			lblSlot4h, lblSlot5h,  lblSlot6h,
                			lblSlot7h,  lblSlot8h, lblSlot9h,
                			lblWinLose, moneySpent,  moneyIsZero,
                			lblMWCurrency,  e, lblMLCurrency);
                	
                	endGame();
                	Main.player.setMoney(finishMoney);
                	
                	this.startMoney = finishMoney;
                	
                	break;
            }
            
            slotGrid.setOpacity(60.00);
            new Timeline(new KeyFrame(
                    Duration.millis(700), 
                    event -> btnBegin.setDisable(false) // Riabilita dopo 700ms
                )).play();
            
            restartGame();
            
        } else  if(Main.player.getMoney()== 0) {
           	
            Stage currentStage = (Stage) btnBegin.getScene().getWindow();
           	moneyIsZero = true;
           	menu.MoneyLost.showPopupIfZero(moneyIsZero,currentStage );
           	
           	
           }else {
        	   
        	   
        	   showBetAlert();  
            System.out.println("Errore: Non puoi giocare con 0 soldi");
        }
    }

    
    public int setHighestEarning() {
        int validated = finishMoney.intValue();
        if(validated > highestEarning) {
            this.highestEarning = validated;
            
            return highestEarning;
        }  
        
        return validated;
    } 
    


    @FXML
	private Label botText;

	@FXML
	private Group messageGroup;

	@FXML
	private QuadCurve smile;

	private boolean isSmiling = true;

	
	private ArrayList<String> winMessages = new ArrayList<String>(Arrays.asList("You got lucky this time",
			"Try again, I'll take your money", "Luck,luck,luck, it won't last forever", "1 more game?",
			"Wow, you won... again", "The house always wins... except this time", "Don't get used to it, champ",
			"Even a broken clock is right twice a day", "The odds were 0.1%... today was your day, huh?",
			"Winner winner chicken dinner! …Too bad it's virtual.",
			"Alert: System error detected. Just kidding… or am I?"));

	private ArrayList<String> lossMessages = new ArrayList<String>(Arrays.asList(
			"Try again, maybe you'll win... or maybe not", "Congratulations! You lost $", "HAHAHAHA my money now",
			"If we had a scoreboard of the top losers you'll be the 1st",
			"Your wallet is crying, and so am I... from laughter", "At this rate, you'll need a second mortgage",
			"Was that your strategy? Bold choice!", "Pro tip: Quitting now saves you money. Just saying.",
			"I'd say 'better luck next time', but we both know the answer.",
			"Thanks for the donation. My yacht is almost paid off."));
	
	private ArrayList<String> noProfitMessages = new ArrayList<String>(Arrays.asList(
		    "Wow. Such excitement. Much thrill. Zero profit.", // Riferimento al meme Doge
		    "The universe is in balance... and so is your wallet.", 
		    "Not a win, not a loss... just pure existential dread.",
		    "Congratulations! You've achieved the financial equivalent of watching paint dry.",
		    "The house edge remains intact. You? Meh.","Really? All of that for nothing..."
		));

	
	@FXML
	private void playerWon() {
		turnSmile(false);
		showMessage();
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), messageGroup);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(1);

		fadeTransition.setOnFinished(evnt -> {
			hideMessageGroup();
		});
		fadeTransition.play();

	}
	private void playerDraw() {
		showDrawMessage();
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), messageGroup);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(1);
		
		fadeTransition.setOnFinished(evnt -> {
			hideMessageGroup();
		});
		fadeTransition.play();
		
	}

	private void playerLost() {
		turnSmile(true);
		showMessage();
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), messageGroup);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(1);

		fadeTransition.setOnFinished(evnt -> {
			hideMessageGroup();
		});
		fadeTransition.play();
	}
	
	@FXML
	private void turnSmile(Boolean smiling) {
		if (isSmiling != smiling) {
			RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.01), smile);
			rotateTransition.setByAngle(180);
			rotateTransition.setCycleCount(1);
			rotateTransition.setAutoReverse(false);
			rotateTransition.play();
			isSmiling = smiling;
		}
	}

	private String getMessage() {
		String message;
		if (isSmiling) {
			message = lossMessages.get((int) (Math.random() * lossMessages.size() - 1));
		} else {
			message = winMessages.get((int) (Math.random() * winMessages.size() - 1));
		}
		return message;
	}

	private void showMessage() {
		botText.setText(getMessage());
		messageGroup.setVisible(true);
		messageGroup.setOpacity(1);
	}
	
	private void showDrawMessage() {
		botText.setText(noProfitMessages.get((int) (Math.random() * noProfitMessages.size() - 1)));
		messageGroup.setVisible(true);
		messageGroup.setOpacity(1);
	}

	@FXML
	private void hideMessageGroup() {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), messageGroup);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished(evnt -> messageGroup.setVisible(false));
		fadeTransition.play();
	}
    
    
    @FXML
    private void instruction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/slot/fxml/Instruction.fxml"));
        Stage stage = new Stage();
        
        label.setStyle(" -fx-background-color: white;"); 
        if(popup.getContent().size() == 0) {
            popup.getContent().add(label); 
        }
        label.setMinWidth(80); 
        label.setMinHeight(50); 
        
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) { 
                if (!popup.isShowing()) 
                    popup.show(stage); 
                else
                    popup.hide(); 
            } 
        };
        
        Scene sceneInstruction = new Scene(root);
        stage.setScene(sceneInstruction);       
        stage.show();
    }

   
    private void restartGame() {
    	 if(isGameStarted) {
             this.restartCount++;
             isGameStarted = false; // Resetta il flag
         }
    	  
    	 
    	 
         initialize();
        lblCounterGames.setText("N. Game: " + restartCount);
        btnBegin.setDisable(false);
        slotGrid.setOpacity(100.00);
           }
    

    @FXML
    private void slotsListSetup() {
        ArrayList<String> buttonList = new ArrayList<>();
        buttonList.add("3X1");
        buttonList.add("3X3");
        buttonList.add("3X4");
        
        this.cbofButtons.getItems().addAll(buttonList);
        this.cbofButtons.setValue("3X1");
        
        this.cbofButtons.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            changeLayout(newVal);
        });
        
        changeLayout("3X1");
    }

    private void changeLayout(String layoutType) {
        slotGrid.getChildren().clear();
        slotGrid.getColumnConstraints().clear();
        slotGrid.getRowConstraints().clear();

        switch(layoutType) {
            case "3X1":
                setup3x1Layout();
                break;
            case "3X3":
                setup3x3Layout();
                break;
            case "3X4":
                setup3x4Layout();
                break;
        }
    }
    
    
    

    private void setup3x1Layout() {
        for (int i = 0; i < 3; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPrefWidth(180);
            slotGrid.getColumnConstraints().add(col);
        }
        
        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.ALWAYS);
        row.setPrefHeight(180);
        slotGrid.getRowConstraints().add(row);
        
        addSlotToGrid(imgSlot1, lblSlot1, 0, 0);
        addSlotToGrid(imgSlot2, lblSlot2, 1, 0);
        addSlotToGrid(imgSlot3, lblSlot3, 2, 0);
        
        setSlotsVisibility(false, imgSlot1h, imgSlot2h, imgSlot3h, imgSlot4h, imgSlot5h, 
                         imgSlot6h, imgSlot7h, imgSlot8h, imgSlot9h);
    }

    private void setup3x3Layout() {
        for (int i = 0; i < 3; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPrefWidth(180);
            slotGrid.getColumnConstraints().add(col);
            
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            row.setPrefHeight(180);
            slotGrid.getRowConstraints().add(row);
        }
        
        addSlotToGrid(imgSlot1h, lblSlot1h, 0, 0);
        addSlotToGrid(imgSlot2h, lblSlot2h, 1, 0);
        addSlotToGrid(imgSlot3h, lblSlot3h, 2, 0);
        
        addSlotToGrid(imgSlot1, lblSlot1, 0, 1);
        addSlotToGrid(imgSlot2, lblSlot2, 1, 1);
        addSlotToGrid(imgSlot3, lblSlot3, 2, 1);
        
        addSlotToGrid(imgSlot4h, lblSlot4h, 0, 2);
        addSlotToGrid(imgSlot5h, lblSlot5h, 1, 2);
        addSlotToGrid(imgSlot6h, lblSlot6h, 2, 2);
        
        setSlotsVisibility(false, imgSlot7h, imgSlot8h, imgSlot9h);
    }

    private void setup3x4Layout() {
        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPrefWidth(180);
            slotGrid.getColumnConstraints().add(col);
        }
        
        for (int i = 0; i < 3; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            row.setPrefHeight(180);
            slotGrid.getRowConstraints().add(row);
        }
        
        addSlotToGrid(imgSlot1h, lblSlot1h, 0, 0);
        addSlotToGrid(imgSlot2h, lblSlot2h, 1, 0);
        addSlotToGrid(imgSlot3h, lblSlot3h, 2, 0);
        addSlotToGrid(imgSlot7h, lblSlot7h, 3, 0);
        
        addSlotToGrid(imgSlot1, lblSlot1, 0, 1);
        addSlotToGrid(imgSlot2, lblSlot2, 1, 1);
        addSlotToGrid(imgSlot3, lblSlot3, 2, 1);
        addSlotToGrid(imgSlot8h, lblSlot8h, 3, 1);
        
        addSlotToGrid(imgSlot4h, lblSlot4h, 0, 2);
        addSlotToGrid(imgSlot5h, lblSlot5h, 1, 2);
        addSlotToGrid(imgSlot6h, lblSlot6h, 2, 2);
        addSlotToGrid(imgSlot9h, lblSlot9h, 3, 2);
    }
    
    
    
    

    private void addSlotToGrid(ImageView image, Label label, int col, int row) {
        StackPane stack = new StackPane();
        stack.setStyle("-fx-background-color: #1a1a1a;");
        
        image.setVisible(true);
        image.setManaged(true);
        label.setVisible(true);
        label.setManaged(true);
        
        stack.getChildren().addAll(image, label);
        slotGrid.add(stack, col, row);
    }

    private void setSlotsVisibility(boolean visible, ImageView... slots) {
        for (ImageView slot : slots) {
            slot.setVisible(visible);
            slot.setManaged(visible);
        }
    }

    public void turnToSwitch(ActionEvent event) {
        switchScene(event, "/menu/fxml/GameChoiceMenu.fxml");
    }

    private void changeMoneySpentValue() {
        moneySpent = moneyManagerSlot.moneySelection(startMoney, spnrMoneySpent, lblMIUCurrency,lastSpinnerValue);
        System.out.println("Valore di moneySpent : " + moneySpent);
    }

    @FXML
    public void showStats(ActionEvent event) {
        if (gameChart != null && gameChart.isWindowOpen()) {
            gameChart.showGraphAlreadyOpenAlert();
        } else {
            gameChart = new GameChart();
            gameChart.showChartWindow();
        }
    }

    
    
    
    
    private void showBetAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Set the Bet");
        alert.setHeaderText(null);
        alert.setContentText("You can't play without money!");
        
        // Personalizza lo stile del popup
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
            getClass().getResource("/slot/application.css").toExternalForm()
        );
        
        alert.showAndWait();
    }
    
    

    public void showAnimatedResult(Label resultLabel, Integer amount) {
        // Imposta il testo
        if (amount >= 0) {
            resultLabel.setText("+" + amount );
            resultLabel.setTextFill(Color.GREEN);
        } else {
            resultLabel.setText(amount.toString()); // amount è già negativo
            resultLabel.setTextFill(Color.RED);
        }

        // Posizione iniziale (in basso)
        resultLabel.setTranslateY(20);
        resultLabel.setOpacity(0);

        // Animazione di salita + fade in
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(1), resultLabel);
        moveUp.setToY(-20); // si alza di 40px

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), resultLabel);
        fadeIn.setToValue(1.0);

        // Dopo un attimo, fade out
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), resultLabel);
        fadeOut.setDelay(Duration.seconds(2)); // aspetta un attimo
        fadeOut.setToValue(0);

        // Timeline combinata
        ParallelTransition showUp = new ParallelTransition(moveUp, fadeIn);
        SequentialTransition sequence = new SequentialTransition(showUp, fadeOut);
        sequence.play();
    }
    
    

   
    
    
    @FXML
    void initialize() {
    	
    	
        startMoney = Main.player.getMoney();
        changeMoneySpentValue();
        
        lblCounterGames.setText("N. Games: " + Main.getPlayer().getGamesPlayed());
        if (gameChart == null) {
            gameChart = new GameChart();
        }
            // Inizializza con l'ultimo valore usato invece di 0
            SpinnerValueFactory.IntegerSpinnerValueFactory factory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, startMoney, lastSpinnerValue);
            spnrMoneySpent.setValueFactory(factory);
            
            // Listener per salvare i cambiamenti
            spnrMoneySpent.valueProperty().addListener((obs, oldVal, newVal) -> {
                lastSpinnerValue = newVal;
                moneySpent = newVal;
            });
            
            // Abilita l'input testuale
            spnrMoneySpent.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
                try {
                    if (!newVal.isEmpty()) {
                        lastSpinnerValue = Integer.parseInt(newVal);
                    }
                } catch (NumberFormatException e) {
                    spnrMoneySpent.getEditor().setText(oldVal);
                }
            });
        
     
        
        // Aggiorna la formattazione delle label
        lblMLCurrency.setText(startMoney + " $");
        
        spnrMoneySpent.valueProperty().addListener((observable, oldValue, newValue) -> {
            moneySpent = newValue;
        });
        
        if(contListSetup == 0) {
            slotsListSetup();
        }
        contListSetup++;
    }
    
    
    public void endGame() {
    	
  	  Main.player.incrementGamesPlayed();
  	    
  	    // Calcola la differenza netta
  	    int differenzaNetta = finishMoney - startMoney;
  	    slot.updateMoneyAnimation(
  	            startMoney,          // Soldi iniziali pre-spin
  	            finishMoney,         // Soldi finali post-spin
  	            lblMLCurrency        // Label da aggiornare
  	        );

  	        // Aggiorna il modello DOPO l'animazione
  	        new Timeline(new KeyFrame(
  	            Duration.millis(700),
  	            e -> {
  	                Main.player.setMoney(finishMoney);
  	                if(gameChart != null) {
  	                    //gameChart.addData(restartCount + 1, finishMoney);
  	                	gameChart.addData(Main.getPlayer().getGamesPlayed(), finishMoney);
  	                }
  	 
  	                
  	                
  	                if(differenzaNetta > 0) {
  	                    playerWon();
  	                    System.out.println("PlayerWon");
  	                    isGameWon = true;
  	                    this.gameWonCounter++;
  	                    Main.player.incrementGamesWon();
  	                    
  	                    
  	                } else if(differenzaNetta < 0) {
  	                    playerLost();
  	                    System.out.println("PlayerLost");
  	                    isGameWon = false;
  	                    this.gameLossCounter++;
  	                    Main.player.incrementGamesLost();
  	                } else {
  	                    playerDraw();
  	                    isGameWon = null;
  	                    System.out.println("Equal");
  	                    
  	                }
  	            }
  	        )).play();
              
  	                
      
     
      if (!Objects.equals(isGameWon, null)) {
       if (isGameWon) {
        System.out.println("PlayerWon");
        Main.player.incrementGamesWon();
        playerWon();
        showAnimatedResult( resultLabel, -(startMoney - finishMoney));
      
       } else {
        System.out.println("PlayerLost");
        Main.player.incrementGamesLost();
        playerLost();
        showAnimatedResult( resultLabel, -(startMoney- finishMoney));
        
       }
       
      }
      

      }
    
    
}



