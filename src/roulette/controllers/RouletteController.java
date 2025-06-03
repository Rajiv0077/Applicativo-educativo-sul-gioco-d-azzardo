package roulette.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import gameChart.GameChart;
import main.Main;
import main.SceneController;
import roulette.BetOption;
import roulette.BetOptionCellFactory;
import roulette.InputBet;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import gameChart.GameChart;

public class RouletteController extends SceneController {
	private ArrayList<Integer> firstRow = new ArrayList<>(Arrays.asList(1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34));
	private ArrayList<Integer> secondRow = new ArrayList<>(Arrays.asList(2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35));
	private ArrayList<Integer> thirdRow = new ArrayList<>(Arrays.asList(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36));
	private ArrayList<Integer> redNumbers = new ArrayList<>(
			Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36));
	private ArrayList<Integer> blackNumbers = new ArrayList<>(
			Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35));
	private ArrayList<Integer> currentRow = new ArrayList<>();
	private ArrayList<Integer> currentTwelve = new ArrayList<>();
	private ArrayList<Integer> currentEighteen = new ArrayList<>();
	private ArrayList<Integer> selectedNums = new ArrayList<>();
	private ArrayList<InputBet> inputBets = new ArrayList<>();
	private Integer num = null;
	private int selectedPrice;
	private Boolean rowGotRight = false;
	private Boolean allInIsClicked = false;
	private Boolean allInIsPositioned = false;
	private Integer allInQuantity;
	private Image selectedImage; // Variabile per salvare l'immagine selezionata
	private ArrayList<String> numeriUsciti = new ArrayList<>(20); // Lista per memorizzare i numeri usciti
//	public Integer money = 750;
	private Integer inputBetAmount = 0;
//	private Boolean inputBetSelected = false;
	private Boolean inputBetIsPositioned = true;
	private Boolean isGameWon = false;
	private Timeline allInTimeline;
	private boolean isAnimationRunning = false; // Variabile per controllare lo stato dell'animazione
	ImageView image1 = new ImageView(new Image("/resources/img/singleChip.png"));
	ImageView image2 = new ImageView(new Image("/resources/img/greenChip25.png"));
	ImageView image3 = new ImageView(new Image("/resources/img/blueChip.png"));
	ImageView image4 = new ImageView(new Image("/resources/img/blackChip.png"));
	ImageView image5 = new ImageView(new Image("/resources/img/whiteChip.png"));
	ImageView image6 = new ImageView(new Image("/resources/img/InputChip.png"));
	ImageView image7 = new ImageView(new Image("/resources/img/allIn.png"));
	Image allIn = new Image("/resources/img/allIn.png");
	Image inputChip = new Image("/resources/img/InputChip.png");
	@FXML
	private Group sezioniGroup; // Group che contiene le sezioni e i numeri
	@FXML
	private GridPane gridPaneTable;
	@FXML
	private StackPane roulettePane;
	@FXML
	private ComboBox<BetOption> betComboBox;
	@FXML
	private Label lblMoney;
	@FXML
	private Label resultLabel;
	@FXML
	private Label lblNumeroUscito;
	@FXML
	private Label lblPremi1;
	@FXML
	private HBox ruotaBox;
	@FXML
	private VBox inputBox;
	@FXML
	private VBox tableBox;
	@FXML
	private Label lblNU;
	@FXML
	private Button allInButton;
	@FXML
	private Button inviaButton;
	@FXML
	private TextField inputBet;
	@FXML
	private Group pallinaGroup;
	@FXML
	private Circle pallina;
	@FXML
	private GridPane ultimiNumeri;
	@FXML
	private Rectangle r00;
	@FXML
	private Rectangle r0;
	@FXML
	private Rectangle r1;
	@FXML
	private Rectangle r2;
	@FXML
	private Rectangle r3;
	@FXML
	private Rectangle r4;
	@FXML
	private Rectangle r5;
	@FXML
	private Rectangle r6;
	@FXML
	private Rectangle r7;
	@FXML
	private Rectangle r8;
	@FXML
	private Rectangle r9;
	@FXML
	private Rectangle r10;
	@FXML
	private Rectangle r11;
	@FXML
	private Rectangle r12;
	@FXML
	private Rectangle r13;
	@FXML
	private Rectangle r14;
	@FXML
	private Rectangle r15;
	@FXML
	private Rectangle r16;
	@FXML
	private Rectangle r17;
	@FXML
	private Rectangle r18;
	@FXML
	private Rectangle r19;
	@FXML
	private Rectangle r20;
	@FXML
	private Rectangle r21;
	@FXML
	private Rectangle r22;
	@FXML
	private Rectangle r23;
	@FXML
	private Rectangle r24;
	@FXML
	private Rectangle r25;
	@FXML
	private Rectangle r26;
	@FXML
	private Rectangle r27;
	@FXML
	private Rectangle r28;
	@FXML
	private Rectangle r29;
	@FXML
	private Rectangle r30;
	@FXML
	private Rectangle r31;
	@FXML
	private Rectangle r32;
	@FXML
	private Rectangle r33;
	@FXML
	private Rectangle r34;
	@FXML
	private Rectangle r35;
	@FXML
	private Rectangle r36;
	@FXML
	private Button showStats;
	@FXML
	private Button goBackBtn;

	private List<Rectangle> hitboxes = new ArrayList<>();
//	private Boolean cTimerRunning = false;
	private String numeroColpito;
	private  GameChart gameChart = new GameChart();
	private MediaPlayer mediaPlayer;
	
	// AUTOMATIC --------------------------------------------------------------------
	 
	  private static final Set<Integer> RED_NUMBERS = new HashSet<>(Arrays.asList(
	          1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
	      ));

	      private enum BetType {
	          EVEN_MONEY, DOZEN, COLUMN, STREET, CORNER, LINE, SPLIT, STRAIGHT
	      }

	 
	 public static int rouletteResult(Integer randBet) {
	  int money = 0;
	        if (randBet == null || randBet <= 0) return money;
	        
	        Random random = new Random();
	        BetType betType = determineBetType(random);
	        int betAmount = randBet;
	        int winningNumber = random.nextInt(37);
	        
	        boolean win = checkWin(betType, winningNumber, random);
	        int payout = getPayout(betType);
	        
	        money = win ? (betAmount * payout) : -betAmount;
	        return money;
	    }

	    private static BetType determineBetType(Random random) {
	        int choice = random.nextInt(100);
	        if (choice < 50) return BetType.EVEN_MONEY;
	        if (choice < 65) return BetType.DOZEN;
	        if (choice < 80) return BetType.COLUMN;
	        if (choice < 85) return BetType.STREET;
	        if (choice < 90) return BetType.CORNER;
	        if (choice < 95) return BetType.LINE;
	        if (choice < 98) return BetType.SPLIT;
	        return BetType.STRAIGHT;
	    }

	    private static boolean checkWin(BetType type, int winningNumber, Random random) {
	        switch (type) {
	            case EVEN_MONEY:
	                return checkEvenMoney(winningNumber, random);
	            case DOZEN:
	                return checkDozen(winningNumber, random);
	            case COLUMN:
	                return checkColumn(winningNumber, random);
	            case STREET:
	                return checkStreet(winningNumber, random);
	            case STRAIGHT:
	                return checkStraight(winningNumber, random);
	            default:
	                return false;
	        }
	    }

	    private static boolean checkEvenMoney(int winningNumber, Random random) {
	        int subType = random.nextInt(6);
	        switch (subType) {
	            case 0: // Red
	                return RED_NUMBERS.contains(winningNumber);
	            case 1: // Black
	                return winningNumber != 0 && !RED_NUMBERS.contains(winningNumber);
	            case 2: // Even
	                return winningNumber != 0 && winningNumber % 2 == 0;
	            case 3: // Odd
	                return winningNumber % 2 == 1;
	            case 4: // High (19-36)
	                return winningNumber >= 19 && winningNumber <= 36;
	            case 5: // Low (1-18)
	                return winningNumber >= 1 && winningNumber <= 18;
	            default:
	                return false;
	        }
	    }

	    private static boolean checkDozen(int winningNumber, Random random) {
	        if (winningNumber == 0) return false;
	        int dozen = random.nextInt(3);
	        return winningNumber > dozen * 12 && winningNumber <= (dozen + 1) * 12;
	    }

	    private static boolean checkColumn(int winningNumber, Random random) {
	        if (winningNumber == 0) return false;
	        int column = random.nextInt(3);
	        return (winningNumber - 1) % 3 == column;
	    }

	    private static boolean checkStreet(int winningNumber, Random random) {
	        int street = random.nextInt(12);
	        int start = street * 3 + 1;
	        return winningNumber >= start && winningNumber <= start + 2;
	    }

	    private static boolean checkStraight(int winningNumber, Random random) {
	        return winningNumber == random.nextInt(37);
	    }

	    private static int getPayout(BetType type) {
	        switch (type) {
	            case EVEN_MONEY: return 1;
	            case DOZEN: case COLUMN: return 2;
	            case STREET: return 11;
	            case CORNER: return 8;
	            case LINE: return 5;
	            case SPLIT: return 17;
	            case STRAIGHT: return 35;
	            default: return 0;
	        }
	    }
	// _____________________________________________________________________________________________________________
	//
	// Chat BOT

	@FXML
	private Label botText;

	@FXML
	private Group messageGroup;

	@FXML
	private QuadCurve smile;

	private boolean isSmiling = true;

	private Integer startMoney = Main.player.getMoney();
	private Integer spinningMoney;
	private Integer finishMoney;

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

	private void hideMessageGroup() {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), messageGroup);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished(evnt -> messageGroup.setVisible(false));
		fadeTransition.play();
	}
	
	public void aggiornaSaldoAnimato() {
        int saldo = Main.player.getMoney();

        Timeline timeline = new Timeline();
        int saldoAttuale = Integer.parseInt(lblMoney.getText()); // ricavato dalla label
        int fine = saldo;

        int steps = Math.abs(fine - saldoAttuale);
        if (steps == 0) return;

        Duration durata = Duration.millis(800);
        for (int i = 0; i <= steps; i++) {
            int valore = (saldoAttuale < fine) ? saldoAttuale + i : saldoAttuale - i;

            KeyFrame frame = new KeyFrame(
                durata.multiply((double) i / steps),
                e -> lblMoney.setText("$" + valore)
            );

            timeline.getKeyFrames().add(frame);
        }

        timeline.play();
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
        moveUp.setToY(-10); // si alza di 20px

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
//________________________________________________________________________________________________________
	// _____________________________________________________________________________________________________________
	//
	public void initialize() {
		refreshMoney();
		// Inizializza la lista con 20 valori null
		for (int i = 0; i < 20; i++) {
			numeriUsciti.add(null);
		}

		for (Node node : sezioniGroup.getChildren()) {
			if (node instanceof Rectangle) {
				hitboxes.add((Rectangle) node);
			}
		}

		// Aggiorna la GridPane con i valori iniziali (tutti null)
		aggiornaGridPane();
		// Imposta il testo predefinito
		betComboBox.setPromptText("Choose bet");
		// Configura la cell factory personalizzata
		BetOptionCellFactory cellFactory = new BetOptionCellFactory();
		betComboBox.setCellFactory(cellFactory);
		// Imposta la ButtonCell personalizzata
		betComboBox.setButtonCell(cellFactory.call(null));
		// Ridimensiona le immagini
		image1.setFitWidth(20);
		image1.setFitHeight(20);
		image2.setFitWidth(20);
		image2.setFitHeight(20);
		image3.setFitWidth(20);
		image3.setFitHeight(20);
		image4.setFitWidth(20);
		image4.setFitHeight(20);
		image5.setFitWidth(20);
		image5.setFitHeight(20);
		image6.setFitWidth(20);
		image6.setFitHeight(20);
		image7.setFitWidth(25);
		image7.setFitHeight(25);
		allInButton.setGraphic(image7);
		allInButton.getStyleClass().add(".ui");
		allInButton.setOnMouseEntered(event -> applyGlitchEffect());
		allInButton.setOnMouseExited(event -> resetGlitchEffect());
		allInButton.setStyle("-fx-text-fill: #b98f10;");
		// Listener per la selezione
		betComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				selectedPrice = (int) newValue.getPrice();
				selectedImage = newValue.getImage().getImage(); // Salva l'immagine selezionata
				// Aggiorna la ButtonCell per visualizzare l'immagine selezionata
				betComboBox.setButtonCell(cellFactory.call(null));
				betComboBox.setValue(newValue); // Imposta il valore selezionato
			}
		});
		betComboBox.getItems().addAll(new BetOption(image1, 10), new BetOption(image2, 25), new BetOption(image3, 50),
				new BetOption(image4, 100), new BetOption(image5, 200));
		// Aggiungi un listener per attendere che ruotaBox sia aggiunto alla scena
		ruotaBox.sceneProperty().addListener((observable, oldScene, newScene) -> {
			if (newScene != null) {
				newScene.setOnKeyPressed(event -> {
					if (event.getCode() == KeyCode.DIGIT1 && !isAnimationRunning) { // Verifica se è stato premuto Enter
						giraLaRuota(null); // Chiama il metodo giraLaRuota
						lblPremi1.setVisible(false);
					}
				});
			}
		});

	}

	private String calcolaNumeroColpito() {
		// Ottieni la posizione finale della pallina
		Bounds pallinaBounds = pallina.localToScene(pallina.getBoundsInLocal());
		double pallinaX = pallinaBounds.getMinX() + pallinaBounds.getWidth() / 2;
		double pallinaY = pallinaBounds.getMinY() + pallinaBounds.getHeight() / 2;

		// Trova il rettangolo più vicino alla posizione della pallina
		Rectangle rettangoloPiuVicino = null;
		double distanzaMinima = Double.MAX_VALUE;

		for (Rectangle rect : hitboxes) {
			Bounds rectBounds = rect.localToScene(rect.getBoundsInLocal());
			double rectX = rectBounds.getMinX() + rectBounds.getWidth() / 2;
			double rectY = rectBounds.getMinY() + rectBounds.getHeight() / 2;

			// Calcola la distanza tra la pallina e il rettangolo
			double distanza = Math.sqrt(Math.pow(pallinaX - rectX, 2) + Math.pow(pallinaY - rectY, 2));

			// Trova il rettangolo più vicino
			if (distanza < distanzaMinima) {
				distanzaMinima = distanza;
				rettangoloPiuVicino = rect;
			}
		}

		// Ottieni l'ID del rettangolo più vicino
		if (rettangoloPiuVicino != null) {
			String id = rettangoloPiuVicino.getId();
			if (id != null && id.startsWith("r")) {
				String numeroStr = id.substring(1); // Rimuove il prefisso "r"
				try {
					return numeroStr; // Restituisce il numero colpito
				} catch (NumberFormatException e) {
					System.err.println("Errore nella conversione dell'ID: " + id);
				}
			}
		}

		return "0";
	}

	public void aggiungiNumeroUscito(String numero) {
		// Se la lista è piena, rimuovi l'ultimo elemento
		if (numeriUsciti.size() >= 20) {
			numeriUsciti.remove(numeriUsciti.size() - 1);
		}
		// Aggiungi il nuovo numero all'inizio della lista
		numeriUsciti.add(0, numero);
		// Aggiorna la GridPane
		aggiornaGridPane();
	}

	private void aggiornaGridPane() {
		// Itera attraverso i 20 bottoni nella GridPane
		for (int i = 0; i < 20; i++) {
			// Calcola riga e colonna in base all'indice
			int riga = i < 10 ? 0 : 2; // Riga 0 per i primi 10 bottoni, riga 2 per i successivi
			int colonna = i < 10 ? i : i - 10; // Colonna: 0-9 per la prima riga, 0-9 per la seconda riga
			// Ottieni il bottone dalla GridPane utilizzando riga e colonna
			Button bottone = getButtonAt(riga, colonna);
			// Se il bottone non esiste, passa al prossimo
			if (bottone == null) {
				continue;
			}
			// Imposta il testo del bottone con il numero corrispondente
			String numero = i < numeriUsciti.size() ? numeriUsciti.get(i) : null;
			bottone.setText(numero != null ? numero : ""); // Se null, lascia vuoto
			// Imposta il colore del bottone in base al numero
			if (numero != null) {
				if (numero.equals("00") || numero.equals("0")) {
					bottone.setStyle("-fx-background-color: green; -fx-text-fill: white;");
				} else {
					try {
						int num = Integer.parseInt(numero);
						if ((num >= 1 && num <= 10) || (num >= 19 && num <= 28)) {
							if (num % 2 == 0) {
								bottone.setStyle("-fx-background-color: black; -fx-text-fill: white;");
							} else {
								bottone.setStyle("-fx-background-color: red; -fx-text-fill: white;");
							}
						} else {
							if (num % 2 == 0) {
								bottone.setStyle("-fx-background-color: red; -fx-text-fill: white;");
							} else {
								bottone.setStyle("-fx-background-color: black; -fx-text-fill: white;");
							}
						}
					} catch (NumberFormatException e) {
						bottone.setStyle("-fx-background-color: black; -fx-border-color: black; -fx-text-fill: white;");
					}
				}
			} else {
				// Se il numero è null, resetta lo stile
				bottone.setStyle("-fx-background-color: black; -fx-border-color: black; -fx-text-fill: white;");
			}
			// Imposta lo stile per i bottoni disabilitati
			bottone.setStyle(bottone.getStyle() + " -fx-opacity: 1;");
		}
	}

	private Button getButtonAt(int riga, int colonna) {
		for (Node node : ultimiNumeri.getChildren()) {
			Integer nodeRiga = GridPane.getRowIndex(node);
			Integer nodeColonna = GridPane.getColumnIndex(node);
			// Se rowIndex o columnIndex sono null, assumi che siano 0
			if (nodeRiga == null) {
				nodeRiga = 0;
			}
			if (nodeColonna == null) {
				nodeColonna = 0;
			}
			if (nodeRiga == riga && nodeColonna == colonna) {
				return (Button) node;
			}
		}
		return null; // Restituisce null se non trova il bottone
	}

	@FXML
	public void allInButton(ActionEvent event) {
		allInIsClicked = true;
		allInQuantity = Main.player.getMoney();
		allInButton.setVisible(false);
		betComboBox.setVisible(false);
		inputBox.setVisible(false);
	}

	@FXML
	public void inviaButton(ActionEvent event) {
		try {
			inputBetAmount = Integer.parseInt(inputBet.getText());
			if (inputBetAmount > Main.player.getMoney() || inputBetAmount <= 0) {
				inputBetAmount = 0;
				inputBet.setText("Invalid Input");
				inputBet.setStyle("-fx-text-fill: red;");
				PauseTransition pause = new PauseTransition(Duration.millis(600));
				pause.setOnFinished(ev -> {
					inputBet.setStyle("-fx-text-fill: black;");
				    inputBet.setText("0");
				});
				pause.play();
				return;
			}
			inputBetIsPositioned = false;
			allInButton.setVisible(false);
			betComboBox.setVisible(false);
			inputBox.setVisible(false);
		} catch (Exception e) {
			inputBet.setText("Write a NUMBER");
			inputBet.setStyle("-fx-text-fill: red;");
			PauseTransition pause = new PauseTransition(Duration.millis(600));
			pause.setOnFinished(ev -> {
			    inputBet.setText("0");
			    inputBet.setStyle("-fx-text-fill: black;");
			});
			pause.play();
			inputBetAmount = 0;
		}
	}

	@FXML
	private void applyGlitchEffect() {
		allInButton.setGraphic(null);
		allInTimeline = new Timeline(new KeyFrame(Duration.millis(0), event -> updateButtonText("$$$$$")),
				new KeyFrame(Duration.millis(50), event -> updateButtonText(".#]")),
				new KeyFrame(Duration.millis(100), event -> updateButtonText("^{")),
				new KeyFrame(Duration.millis(150), event -> updateButtonText(Main.player.getMoney().toString())),
				new KeyFrame(Duration.millis(200), event -> updateButtonText("#$_")),
				new KeyFrame(Duration.millis(250), event -> updateButtonText("№:0")),
				new KeyFrame(Duration.millis(300), event -> updateButtonText("#{+.")),
				new KeyFrame(Duration.millis(350), event -> updateButtonText("@}-?")),
				new KeyFrame(Duration.millis(400), event -> updateButtonText("?{4@%")),
				new KeyFrame(Duration.millis(450), event -> updateButtonText("=.,^!")),
				new KeyFrame(Duration.millis(500), event -> updateButtonText(Main.player.getMoney().toString())),
				new KeyFrame(Duration.millis(550), event -> updateButtonText("/;1}]")),
				new KeyFrame(Duration.millis(600), event -> updateButtonText("?{%:%")),
				new KeyFrame(Duration.millis(650), event -> updateButtonText("$$$$$$")),
				new KeyFrame(Duration.millis(700), event -> updateButtonText("#§?!$")));
		allInTimeline.setCycleCount(Timeline.INDEFINITE);
		allInTimeline.play();
	}

	private void updateButtonText(String text) {
		allInButton.setText(text);
		allInButton.setStyle("-fx-text-fill: #b98f10;");
	}

	private void resetGlitchEffect() {
		if (allInTimeline != null) {
			allInTimeline.stop();
		}
		allInButton.setText("All IN");
		allInButton.setGraphic(image7);
	}
	
	 private void playSound() {
	        if (mediaPlayer != null) {
	            mediaPlayer.stop();
	            mediaPlayer.dispose();
	        }

	        String soundFile = getClass().getResource("/sounds/RouletteSound.mp4").toExternalForm();
	        Media sound = new Media(soundFile);
	        mediaPlayer = new MediaPlayer(sound);
	        mediaPlayer.play();
	    }

	@FXML
	void giraLaRuota(ActionEvent e) {
		if (isAnimationRunning) {
			return; // Ignora la chiamata se l'animazione è già in corso
		}
		playSound();
		spinningMoney = Integer.parseInt(lblMoney.getText());
		resetWheelAndBall();

		isAnimationRunning = true; // Imposta il flag prima di avviare l'animazione
		lblNU.setVisible(false);
		lblNumeroUscito.setVisible(false);

		// Ruota parte subito
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(4.5), sezioniGroup);
		int randomRotations = 3; // Numero di rotazioni complete
		int randomAngle = (int) (Math.random() * 360); // Angolo casuale tra 0 e 359 gradi
		int totalRotation = randomRotations * 360 + randomAngle; // Rotazione totale
		rotateTransition.setByAngle(totalRotation); // Ruota per un numero casuale di rotazioni complete più un angolo
													// casuale
		rotateTransition.setCycleCount(1);
		rotateTransition.setAutoReverse(false);
		rotateTransition.play(); // La ruota inizia immediatamente

		// Animazione di ingrandimento della pallina
		ScaleTransition scaleUp = new ScaleTransition(Duration.millis(250), pallina);
		scaleUp.setToX(1.4);
		scaleUp.setToY(1.4);
		ScaleTransition scaleDown = new ScaleTransition(Duration.millis(250), pallina);
		scaleDown.setToX(1.0);
		scaleDown.setToY(1.0);
		ScaleTransition scaleUp2 = new ScaleTransition(Duration.millis(250), pallina);
		scaleUp2.setToX(1.4);
		scaleUp2.setToY(1.4);
		ScaleTransition scaleDown2 = new ScaleTransition(Duration.millis(250), pallina);
		scaleDown2.setToX(1.0);
		scaleDown2.setToY(1.0);
		ScaleTransition scaleUp3 = new ScaleTransition(Duration.millis(250), pallina);
		scaleUp3.setToX(1.4);
		scaleUp3.setToY(1.4);
		ScaleTransition scaleDown3 = new ScaleTransition(Duration.millis(250), pallina);
		scaleDown3.setToX(1.0);
		scaleDown3.setToY(1.0);

		// Dopo la scala, la pallina inizia a girare
		RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(2.5), pallinaGroup);
		rotateTransition2.setByAngle(-((int) (Math.random() * 360) + (90 * 5))); // Ruota 5 volte in senso opposto
		rotateTransition2.setCycleCount(1);
		rotateTransition2.setAutoReverse(false);
		ruotaBox.setVisible(true);

		// Prima fa la scala, poi parte la rotazione della pallina
		SequentialTransition sequence = new SequentialTransition(scaleUp, scaleDown, scaleUp2, scaleDown2, scaleUp3,
				scaleDown3, rotateTransition2);
		sequence.play();

		// Quando tutto è finito, mostra il numero uscito
		sequence.setOnFinished(event -> {
			

			// Ritarda il rilevamento delle collisioni di 500 millisecondi (1.7 secondi)
			Timeline delayTimeline = new Timeline(new KeyFrame(Duration.millis(1700), ev -> {
				// Calcola il numero colpito in base alla posizione finale della pallina

				numeroColpito = calcolaNumeroColpito();

				isAnimationRunning = false; // Resetta il flag dopo la fine dell'animazione
				Integer nUscito;
				if (numeroColpito.equals("00")) {
					nUscito = 37;
				} else {
					nUscito = Integer.parseInt(numeroColpito);
				}

				if (nUscito == 37) {
					aggiungiNumeroUscito("00");
				} else {
					aggiungiNumeroUscito(nUscito.toString());
				}
				aggiornaGridPane();
				lblNU.setVisible(true);
				if (nUscito == 37) {
					lblNumeroUscito.setText("00");
					lblNumeroUscito.setStyle("-fx-text-fill: green;");
				} else {
					if (nUscito == 0) {
						lblNumeroUscito.setText(nUscito.toString());
						lblNumeroUscito.setStyle("-fx-text-fill: green;");
					}
					if (redNumbers.contains(nUscito)) {
						lblNumeroUscito.setText(nUscito.toString());
						lblNumeroUscito.setStyle("-fx-text-fill: red;");
					} else {
						lblNumeroUscito.setText(nUscito.toString());
						lblNumeroUscito.setStyle("-fx-text-fill: darkgrey;");
					}
				}
				lblNumeroUscito.setVisible(true);

				// Controlla le vincite
				for (Node node : gridPaneTable.getChildren()) {
					checkNodeForBets(node, nUscito);
				}
				resetTable();
				finishMoney = Integer.parseInt(lblMoney.getText());
				System.out.println(startMoney + " " + finishMoney);
		
				if (finishMoney.compareTo(startMoney) == 0) {
					aggiornaSaldoAnimato();
					showAnimatedResult(resultLabel,0);
					isGameWon = null;
					System.out.println("Equal");
					playerDraw();
				} else {
					if (finishMoney > startMoney) {
						aggiornaSaldoAnimato();
						showAnimatedResult(resultLabel,-(startMoney-finishMoney));
						System.out.println("PlayerWon");
						isGameWon = true;
						startMoney = finishMoney;
					} else {
						System.out.println("PlayerLost");
						aggiornaSaldoAnimato();
						showAnimatedResult(resultLabel,-(startMoney-finishMoney));
						isGameWon = false;
						startMoney = finishMoney;
					}
				}
				if (!Objects.equals(isGameWon, null)) {
					if (isGameWon) {
						System.out.println("PlayerWon");
						Main.player.incrementGamesWon();
						playerWon();
					} else {
						System.out.println("PlayerLost");
						Main.player.incrementGamesLost();
						playerLost();
					}
				}
				Main.player.incrementGamesPlayed();
				gameChart.addData(Main.player.getGamesPlayed(), Main.player.getMoney());
			}));
			delayTimeline.play(); // Avvia il ritardo
		});
	}

	@FXML
	private void resetWheelAndBall() {
		// Riporta la ruota alla posizione iniziale
		sezioniGroup.setRotate(0); // Azzera l'angolo di rotazione della ruota
		// Riporta la pallina alla sua posizione e scala iniziali
		pallina.setScaleX(1.0); // Reimposta la scala X
		pallina.setScaleY(1.0); // Reimposta la scala Y
		pallinaGroup.setRotate(0); // Azzera l'angolo di rotazione del gruppo della pallina
	}

	private void checkNodeForBets(Node node, Integer nUscito) {
		if (node instanceof Button) {
			Button button = (Button) node;
			if (button.getGraphic() != null) {
				ImageView imageView = (ImageView) button.getGraphic();
				int chipValue;
				// Controlla se è una scommessa "All In"
				if (allInIsClicked && imageView.getImage() == allIn) {
					chipValue = allInQuantity; // Usa il valore di "All In"
				}
				// Controlla se è una scommessa "Input Bet"
				else if (imageView.getImage() == inputChip) {
					// Trova la scommessa corrispondente nel list inputBets
					int buttonId = getButtonIdFromButton(button); // Funzione per ottenere l'ID del bottone
					InputBet inputBet = findInputBetByButtonId(buttonId);
					if (inputBet != null) {
						chipValue = inputBet.getBetAmount(); // Usa il valore di "Input Bet"
					} else {
						chipValue = 0; // Se non trova la scommessa, non applica moltiplicatori
					}
				}
				// Altrimenti, usa il valore normale della chip
				else {
					chipValue = getPriceForImage(imageView.getImage());
				}
				String buttonText = button.getText();
				// Numeri
				if (buttonText.equals(nUscito.toString())) {
					Main.player.addMoney(chipValue * 35); // Moltiplicatore x35 per i numeri
					animateButton(button); // Animazione per il bottone vincente
				}
				// Colori
				if (buttonText.equalsIgnoreCase("RED") && redNumbers.contains(nUscito)) {
					Main.player.addMoney(chipValue * 2); // Moltiplicatore x2 per il rosso
					animateButton(button); // Animazione per il bottone vincente
				} else if (buttonText.equalsIgnoreCase("BLACK") && blackNumbers.contains(nUscito)) {
					Main.player.addMoney(chipValue * 2); // Moltiplicatore x2 per il nero
					animateButton(button); // Animazione per il bottone vincente
				}
				// Pari/Dispari
				if (buttonText.equalsIgnoreCase("ODD") && nUscito % 2 == 1) {
					Main.player.addMoney(chipValue * 2); // Moltiplicatore x2 per il dispari
					animateButton(button); // Animazione per il bottone vincente
				} else if (buttonText.equalsIgnoreCase("EVEN") && nUscito % 2 == 0) {
					Main.player.addMoney(chipValue * 2); // Moltiplicatore x2 per il pari
					animateButton(button); // Animazione per il bottone vincente
				}
				// Dozzine
				if (buttonText.equalsIgnoreCase("1st 12") && nUscito >= 1 && nUscito <= 12) {
					Main.player.addMoney(chipValue * 3); // Moltiplicatore x3 per la prima dozzina
					animateButton(button); // Animazione per il bottone vincente
				} else if (buttonText.equalsIgnoreCase("2nd 12") && nUscito >= 13 && nUscito <= 24) {
					Main.player.addMoney(chipValue * 3); // Moltiplicatore x3 per la seconda dozzina
					animateButton(button); // Animazione per il bottone vincente
				} else if (buttonText.equalsIgnoreCase("3rd 12") && nUscito >= 25 && nUscito <= 36) {
					Main.player.addMoney(chipValue * 3); // Moltiplicatore x3 per la terza dozzina
					animateButton(button); // Animazione per il bottone vincente
				}
				// Colonne (righe)
				if (currentRow.contains(nUscito) && !rowGotRight) {
					System.out.println("Row got Right");
					rowGotRight = true;
					Main.player.addMoney(chipValue * 3); // Moltiplicatore x3 per la riga
					animateButton(button); // Animazione per il bottone vincente
				}
				// 1-18 / 19-36
				if (buttonText.equalsIgnoreCase("1 to 18") && nUscito >= 1 && nUscito <= 18) {
					Main.player.addMoney(chipValue * 2); // Moltiplicatore x2 per 1-18
					animateButton(button); // Animazione per il bottone vincente
				} else if (buttonText.equalsIgnoreCase("19 to 36") && nUscito >= 19 && nUscito <= 36) {
					Main.player.addMoney(chipValue * 2); // Moltiplicatore x2 per 19-36
					animateButton(button); // Animazione per il bottone vincente
				}
			}
		} else if (node instanceof Parent) {
			for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
				checkNodeForBets(child, nUscito);
			}
		}
	}

	private void animateButton(Button button) {
		// Salva lo stile originale del bottone
		String originalStyle = button.getStyle();
		// Crea una Timeline per far lampeggiare il bottone
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0), event -> button.setStyle("-fx-background-color: yellow;")), // Inizia
																												// con
																												// giallo
				new KeyFrame(Duration.seconds(0.5), event -> button.setStyle(originalStyle)), // Torna allo stile
																								// originale dopo 0.5
																								// secondi
				new KeyFrame(Duration.seconds(1), event -> button.setStyle("-fx-background-color: yellow;")), // Giallo
																												// dopo
																												// 1
																												// secondo
				new KeyFrame(Duration.seconds(1.5), event -> button.setStyle(originalStyle)), // Torna allo stile
																								// originale dopo 1.5
																								// secondi
				new KeyFrame(Duration.seconds(2), event -> button.setStyle("-fx-background-color: yellow;")), // Giallo
																												// dopo
																												// 2
																												// secondi
				new KeyFrame(Duration.seconds(2.5), event -> button.setStyle(originalStyle)), // Torna allo stile
																								// originale dopo 2.5
																								// secondi
				new KeyFrame(Duration.seconds(3), event -> button.setStyle(originalStyle)) // Alla fine, assicurati che
																							// sia ripristinato
		);
		timeline.setCycleCount(1); // Esegui l'animazione una sola volta
		timeline.play();
	}

	private int getButtonIdFromButton(Button button) {
		String buttonText = button.getText();
		switch (buttonText) {
		case "1st 12":
			return 42;
		case "2nd 12":
			return 43;
		case "3rd 12":
			return 44;
		case "ODD":
			return 45;
		case "EVEN":
			return 46;
		case "RED":
			return 47;
		case "BLACK":
			return 48;
		case "1 to 18":
			return 49;
		case "19 to 36":
			return 50;
		default:
			try {
				return Integer.parseInt(buttonText); // Numeri da 1 a 36
			} catch (NumberFormatException e) {
				if (buttonText.equals("0"))
					return 37;
				if (buttonText.equals("00"))
					return 38;
				return -1; // Caso non gestito
			}
		}
	}

	private InputBet findInputBetByButtonId(int buttonId) {
		for (InputBet inputBet : inputBets) {
			if (inputBet.getButtonId() == buttonId) {
				return inputBet;
			}
		}
		return null; // Se non trova la scommessa
	}

	private int getPriceForImage(Image image) {
		// Ritorna il prezzo in base all'immagine
		if (image == image1.getImage()) {
			return 10;
		} else if (image == image2.getImage()) {
			return 25;
		} else if (image == image3.getImage()) {
			return 50;
		} else if (image == image4.getImage()) {
			return 100;
		} else if (image == image5.getImage()) {
			return 200;
		}
		return 0; // Nel caso in cui l'immagine non corrisponda a nessuna chip
	}

	public void resetTable() {
		refreshMoney();
		// Reset delle variabili di stato
		selectedNums.clear(); // Pulisce l'elenco dei numeri selezionati
		currentRow.clear(); // Pulisce la selezione della riga
		currentTwelve.clear(); // Pulisce la selezione della dozzina
		currentEighteen.clear(); // Pulisce la selezione dei numeri da 1 a 18 / 19 a 36
		num = null; // Reset del numero selezionato
		// Resetta lo stato di "All In"
		allInIsClicked = false;
		allInIsPositioned = false;
		allInQuantity = 0;
		// inputbet reset
		inputBetAmount = 0;
		inputBetIsPositioned = true;
		inputBets.clear();
		if (Main.player.getMoney() != 0) {
			allInButton.setVisible(true);
			betComboBox.setVisible(true);
			inputBox.setVisible(true);
		} else {
			allInButton.setVisible(false);
			betComboBox.setVisible(false);
			inputBox.setVisible(false);
			mostraPopup();
		}
		for (Node node : gridPaneTable.getChildren()) {
			// Se il nodo è un HBox o VBox
			if (node instanceof HBox || node instanceof VBox) {
				// Ottieni i figli di HBox o VBox
				for (Node childNode : ((Parent) node).getChildrenUnmodifiable()) {
					// Se il figlio è un bottone, rimuovi l'immagine
					if (childNode instanceof Button) {
						Button btn = (Button) childNode;
						btn.setGraphic(null); // Rimuovi l'immagine dal bottone
					}
				}
			}
			// Se il nodo è direttamente un bottone (nel caso sia direttamente nella
			// GridPane)
			else if (node instanceof Button) {
				Button btn = (Button) node;
				btn.setGraphic(null); // Rimuovi l'immagine dal bottone
			}
		}
	}

	@FXML
	private void mostraPopup() {
		switchScene(ruotaBox, "/roulette/fxml/popUp.fxml");
	}

	@FXML
	public void handleNum(ActionEvent event) {
		if (isAnimationRunning) {
			return;
		}
		Button clickedButton = (Button) event.getSource();
		int num = Integer.parseInt(clickedButton.getText());
		// Verifica se c'è già un'immagine nel bottone
		if (clickedButton.getGraphic() != null) {
			// Se c'è già un'immagine, dobbiamo determinare quale tipo di scommessa è stata
			// piazzata
			ImageView currentImageView = (ImageView) clickedButton.getGraphic();
			Image currentImage = currentImageView.getImage();
			// Controlla se è una scommessa "All In"
			if (currentImage == allIn) {
				// Ripristina il saldo con il valore di "All In"
				Main.player.addMoney(allInQuantity);
				refreshMoney();
				// Resetta le variabili di "All In"
				allInIsClicked = false;
				allInIsPositioned = false;
				allInQuantity = 0;
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
				// Rimuovi l'immagine dal bottone
				clickedButton.setGraphic(null);
			}
			// Controlla se è una scommessa "Input Bet"
			else if (currentImage == inputChip) {
				// Trova la scommessa corrispondente nel list inputBets
				int buttonId = getButtonIdFromButton(clickedButton); // Funzione per ottenere l'ID del bottone
				InputBet inputBet = findInputBetByButtonId(buttonId);
				if (inputBet != null) {
					// Ripristina il saldo con il valore della scommessa
					Main.player.addMoney(inputBet.getBetAmount());
					refreshMoney();
					// Rimuovi la scommessa dalla lista inputBets
					inputBets.remove(inputBet);
					// Rimuovi l'immagine dal bottone
					clickedButton.setGraphic(null);
					// Resetta le variabili di "Input Bet"
					inputBetIsPositioned = false;
					inputBetAmount = 0;
				}
			}
			// Altrimenti, è una scommessa normale (chip standard)
			else {
				// Recupera il prezzo corrispondente all'immagine
				int priceToAdd = getPriceForImage(currentImage);
				// Rimuovi l'immagine
				clickedButton.setGraphic(null);
				// Aggiungi il prezzo del numero al denaro (corrispondente alla rimozione)
				Main.player.addMoney(priceToAdd);
				refreshMoney(); // Ricalcola e ggiorna il saldo
				selectedNums.remove(Integer.valueOf(num)); // Rimuovi il numero dalla lista
			}
		} else {
			// Se non c'è un'immagine, gestisci la nuova scommessa
			if (allInIsClicked && !allInIsPositioned) {
				// Aggiungi l'immagine solo se il saldo è sufficiente
				ImageView imageView = new ImageView(allIn);
				imageView.setFitWidth(25);
				imageView.setFitHeight(25);
				clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
				selectedNums.add(num); // Aggiungi il numero alla lista
				allInIsPositioned = true;
				Main.player.setMoney(0);
				refreshMoney();
			} else {
				if (!inputBetIsPositioned) {
					ImageView imageView = new ImageView(inputChip);
					imageView.setFitWidth(25);
					imageView.setFitHeight(25);
					clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
					selectedNums.add(num); // Aggiungi il numero alla lista (obsoleto)
					inputBetIsPositioned = true;
					inputBets.add(new InputBet(inputBetAmount, num)); // Aggiungi la scommessa alla lista
					Main.player.subtractMoney(inputBetAmount);
					if (Main.player.getMoney() != 0) {
						allInButton.setVisible(true);
						betComboBox.setVisible(true);
						inputBox.setVisible(true);
					}
					refreshMoney();
				} else {
					// Se l'utente ha abbastanza denaro e c'è una chip selezionata
					if (selectedImage != null && Main.player.getMoney() >= selectedPrice) {
						// Aggiungi l'immagine solo se il saldo è sufficiente
						ImageView imageView = new ImageView(selectedImage);
						imageView.setFitWidth(20);
						imageView.setFitHeight(20);
						clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
						// Sottrai il prezzo del numero
						Main.player.subtractMoney(selectedPrice);
						refreshMoney(); // Ricalcola e aggiorna il saldo
						selectedNums.add(num); // Aggiungi il numero alla lista
					}
				}
			}
		}
	}

	@FXML
	public void handleColor(ActionEvent event) {
		if (isAnimationRunning) {
			return;
		}
		Button clickedButton = (Button) event.getSource();
		// Verifica se c'è già un'immagine nel bottone
		if (clickedButton.getGraphic() != null) {
			// Se c'è già un'immagine, dobbiamo determinare quale tipo di scommessa è stata
			// piazzata
			ImageView currentImageView = (ImageView) clickedButton.getGraphic();
			Image currentImage = currentImageView.getImage();
			// Controlla se è una scommessa "All In"
			if (currentImage == allIn) {
				// Ripristina il saldo con il valore di "All In"
				Main.player.addMoney(allInQuantity);
				refreshMoney();
				// Resetta le variabili di "All In"
				allInIsClicked = false;
				allInIsPositioned = false;
				allInQuantity = 0;
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
				// Rimuovi l'immagine dal bottone
				clickedButton.setGraphic(null);
			}
			// Controlla se è una scommessa "Input Bet"
			else if (currentImage == inputChip) {
				// Trova la scommessa corrispondente nel list inputBets
				int buttonId = getButtonIdFromButton(clickedButton); // Funzione per ottenere l'ID del bottone
				InputBet inputBet = findInputBetByButtonId(buttonId);
				if (inputBet != null) {
					// Ripristina il saldo con il valore della scommessa
					Main.player.addMoney(inputBet.getBetAmount());
					refreshMoney();
					// Rimuovi la scommessa dalla lista inputBets
					inputBets.remove(inputBet);
					// Rimuovi l'immagine dal bottone
					clickedButton.setGraphic(null);
					// Resetta le variabili di "Input Bet"
					inputBetIsPositioned = false;
					inputBetAmount = 0;
				}
			}
			// Altrimenti, è una scommessa normale (chip standard)
			else {
				// Recupera il prezzo corrispondente all'immagine
				int priceToAdd = getPriceForImage(currentImage);
				// Rimuovi l'immagine
				clickedButton.setGraphic(null);
				// Aggiungi il prezzo del numero al denaro (corrispondente alla rimozione)
				Main.player.addMoney(priceToAdd);
				refreshMoney(); // Ricalcola e aggiorna il saldo
			}
		} else {
			if (allInIsClicked && !allInIsPositioned) {
				// Aggiungi l'immagine solo se il saldo è sufficiente
				ImageView imageView = new ImageView(allIn);
				imageView.setFitWidth(20);
				imageView.setFitHeight(20);
				clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
				allInIsPositioned = true;
				Main.player.setMoney(0);
				refreshMoney();
			} else {
				if (!inputBetIsPositioned) {
					ImageView imageView = new ImageView(inputChip);
					imageView.setFitWidth(25);
					imageView.setFitHeight(25);
					clickedButton.setGraphic(imageView);
					inputBetIsPositioned = true;
					Main.player.subtractMoney(inputBetAmount);
					if (clickedButton.getText().equalsIgnoreCase("RED")) {
						inputBets.add(new InputBet(inputBetAmount, 47)); // 47 è id per rosso
					} else {
						inputBets.add(new InputBet(inputBetAmount, 48)); // 48 è id per nero
					}
					if (Main.player.getMoney() != 0) {
						allInButton.setVisible(true);
						betComboBox.setVisible(true);
						inputBox.setVisible(true);
					}
					refreshMoney();
				} else {
					// Aggiungi l'immagine selezionata solo se il saldo è sufficiente
					if (selectedImage != null && Main.player.getMoney() >= selectedPrice) {
						ImageView imageView = new ImageView(selectedImage);
						imageView.setFitWidth(20);
						imageView.setFitHeight(20);
						clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
						// Sottrai il prezzo della chip
						Main.player.subtractMoney(selectedPrice);
						refreshMoney();
					}
				}
			}
		}
	}

	@FXML
	public void handleOddEven(ActionEvent event) {
		if (isAnimationRunning) {
			return;
		}
		Button clickedButton = (Button) event.getSource();
		// Verifica se c'è già un'immagine nel bottone
		if (clickedButton.getGraphic() != null) {
			// Se c'è già un'immagine, dobbiamo determinare quale tipo di scommessa è stata
			// piazzata
			ImageView currentImageView = (ImageView) clickedButton.getGraphic();
			Image currentImage = currentImageView.getImage();
			// Controlla se è una scommessa "All In"
			if (currentImage == allIn) {
				// Ripristina il saldo con il valore di "All In"
				Main.player.addMoney(allInQuantity);
				refreshMoney();
				// Resetta le variabili di "All In"
				allInIsClicked = false;
				allInIsPositioned = false;
				allInQuantity = 0;
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
				// Rimuovi l'immagine dal bottone
				clickedButton.setGraphic(null);
			}
			// Controlla se è una scommessa "Input Bet"
			else if (currentImage == inputChip) {
				// Trova la scommessa corrispondente nel list inputBets
				int buttonId = getButtonIdFromButton(clickedButton); // Funzione per ottenere l'ID del bottone
				InputBet inputBet = findInputBetByButtonId(buttonId);
				if (inputBet != null) {
					// Ripristina il saldo con il valore della scommessa
					Main.player.addMoney(inputBet.getBetAmount());
					refreshMoney();
					// Rimuovi la scommessa dalla lista inputBets
					inputBets.remove(inputBet);
					// Rimuovi l'immagine dal bottone
					clickedButton.setGraphic(null);
					// Resetta le variabili di "Input Bet"
					inputBetIsPositioned = false;
					inputBetAmount = 0;
				}
			}
			// Altrimenti, è una scommessa normale (chip standard)
			else {
				// Recupera il prezzo corrispondente all'immagine
				int priceToAdd = getPriceForImage(currentImage);
				// Rimuovi l'immagine
				clickedButton.setGraphic(null);
				// Aggiungi il prezzo del numero al denaro (corrispondente alla rimozione)
				Main.player.addMoney(priceToAdd);
				refreshMoney(); // Ricalcola e aggiorna il saldo
			}
		} else {
			if (allInIsClicked && !allInIsPositioned) {
				// Aggiungi l'immagine solo se il saldo è sufficiente
				ImageView imageView = new ImageView(allIn);
				imageView.setFitWidth(20);
				imageView.setFitHeight(20);
				clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
				allInIsPositioned = true;
				Main.player.setMoney(0);
				refreshMoney();
			} else {
				if (!inputBetIsPositioned) {
					ImageView imageView = new ImageView(inputChip);
					imageView.setFitWidth(25);
					imageView.setFitHeight(25);
					clickedButton.setGraphic(imageView);
					inputBetIsPositioned = true;
					if (clickedButton.getText().equalsIgnoreCase("ODD")) {
						inputBets.add(new InputBet(inputBetAmount, 45)); // 45 è id per odd
					} else {
						inputBets.add(new InputBet(inputBetAmount, 46)); // 46 è id per even
					}
					Main.player.subtractMoney(inputBetAmount);
					if (Main.player.getMoney() != 0) {
						allInButton.setVisible(true);
						betComboBox.setVisible(true);
						inputBox.setVisible(true);
					}
					refreshMoney();
				} else {
					// Se l'utente ha abbastanza denaro e c'è una chip selezionata
					if (selectedImage != null && Main.player.getMoney() >= selectedPrice) {
						// Aggiungi l'immagine solo se il saldo è sufficiente
						ImageView imageView = new ImageView(selectedImage);
						imageView.setFitWidth(20); // Imposta la larghezza dell'immagine
						imageView.setFitHeight(20); // Imposta l'altezza dell'immagine
						clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
						// Sottrai il prezzo
						Main.player.subtractMoney(selectedPrice);
						refreshMoney(); // Ricalcola e aggiorna il saldo
					}
				}
			}
		}
	}

	@FXML
	public void handleTwelves(ActionEvent event) {
		if (isAnimationRunning) {
			return;
		}
		Button clickedButton = (Button) event.getSource();
		// Verifica se c'è già un'immagine nel bottone
		if (clickedButton.getGraphic() != null) {
			// Se c'è già un'immagine, dobbiamo determinare quale tipo di scommessa è stata
			// piazzata
			ImageView currentImageView = (ImageView) clickedButton.getGraphic();
			Image currentImage = currentImageView.getImage();
			// Controlla se è una scommessa "All In"
			if (currentImage == allIn) {
				// Ripristina il saldo con il valore di "All In"
				Main.player.addMoney(allInQuantity);
				refreshMoney();
				// Resetta le variabili di "All In"
				allInIsClicked = false;
				allInIsPositioned = false;
				allInQuantity = 0;
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
				// Rimuovi l'immagine dal bottone
				clickedButton.setGraphic(null);
				return;
			}
			// Controlla se è una scommessa "Input Bet"
			else if (currentImage == inputChip) {
				// Trova la scommessa corrispondente nel list inputBets
				int buttonId = getButtonIdFromButton(clickedButton); // Funzione per ottenere l'ID del bottone
				InputBet inputBet = findInputBetByButtonId(buttonId);
				if (inputBet != null) {
					// Ripristina il saldo con il valore della scommessa
					Main.player.addMoney(inputBet.getBetAmount());
					refreshMoney();
					// Rimuovi la scommessa dalla lista inputBets
					inputBets.remove(inputBet);
					// Rimuovi l'immagine dal bottone
					clickedButton.setGraphic(null);
					// Resetta le variabili di "Input Bet"
					inputBetIsPositioned = false;
					inputBetAmount = 0;
					return;
				}
			}
			// Altrimenti, è una scommessa normale (chip standard)
			else {
				// Recupera il prezzo corrispondente all'immagine
				int priceToAdd = getPriceForImage(currentImage);
				// Rimuovi l'immagine
				clickedButton.setGraphic(null);
				// Aggiungi il prezzo del numero al denaro (corrispondente alla rimozione)
				Main.player.addMoney(priceToAdd);
				refreshMoney(); // Ricalcola e aggiorna il saldo
				currentTwelve.clear();
				return;
			}
		}
		if (allInIsClicked && !allInIsPositioned) {
			// Aggiungi l'immagine solo se il saldo è sufficiente
			ImageView imageView = new ImageView(allIn);
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			selectedNums.add(num); // Aggiungi il numero alla lista
			allInIsPositioned = true;
			Main.player.setMoney(0);
			refreshMoney();
			return;
		}
		if (!inputBetIsPositioned) {
			ImageView imageView = new ImageView(inputChip);
			imageView.setFitWidth(25);
			imageView.setFitHeight(25);
			clickedButton.setGraphic(imageView);
			inputBetIsPositioned = true;
			if (clickedButton.getText().equalsIgnoreCase("1st 12")) {
				inputBets.add(new InputBet(inputBetAmount, 42)); // 42 è id per prima dozzina
			} else if (clickedButton.getText().equalsIgnoreCase("2nd 12")) {
				inputBets.add(new InputBet(inputBetAmount, 43)); // 43 è id per seconda dozzina
			} else {
				inputBets.add(new InputBet(inputBetAmount, 44)); // 44 è id per terza dozzina
			}
			Main.player.subtractMoney(inputBetAmount);
			if (Main.player.getMoney() != 0) {
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
			}
			refreshMoney();
			return;
		}
		// Se l'utente ha abbastanza denaro e c'è una chip selezionata
		if (selectedImage != null && Main.player.getMoney() >= selectedPrice) {
			// Aggiungi la selezione dei numeri in base al bottone premuto
			switch (clickedButton.getText()) {
			case "1st 12":
				for (int i = 1; i <= 12; i++) {
					currentTwelve.add(i);
				}
				break;
			case "2nd 12":
				for (int i = 13; i <= 24; i++) {
					currentTwelve.add(i);
				}
				break;
			case "3rd 12":
				for (int i = 25; i <= 36; i++) {
					currentTwelve.add(i);
				}
				break;
			}
			// Aggiungi l'immagine al bottone
			ImageView imageView = new ImageView(selectedImage);
			imageView.setFitWidth(20); // Imposta la larghezza dell'immagine
			imageView.setFitHeight(20); // Imposta l'altezza dell'immagine
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			// Sottrai il prezzo dal saldo
			Main.player.subtractMoney(selectedPrice);
			refreshMoney(); // Ricalcola e aggiorna il saldo
		}
	}

	@FXML
	public void handleEighteen(ActionEvent event) {
		if (isAnimationRunning) {
			return;
		}
		Button clickedButton = (Button) event.getSource();
		// Verifica se c'è già un'immagine nel bottone
		if (clickedButton.getGraphic() != null) {
			// Se c'è già un'immagine, dobbiamo determinare quale tipo di scommessa è stata
			// piazzata
			ImageView currentImageView = (ImageView) clickedButton.getGraphic();
			Image currentImage = currentImageView.getImage();
			// Controlla se è una scommessa "All In"
			if (currentImage == allIn) {
				// Ripristina il saldo con il valore di "All In"
				Main.player.addMoney(allInQuantity);
				refreshMoney();
				// Resetta le variabili di "All In"
				allInIsClicked = false;
				allInIsPositioned = false;
				allInQuantity = 0;
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
				// Rimuovi l'immagine dal bottone
				clickedButton.setGraphic(null);
				return;
			}
			// Controlla se è una scommessa "Input Bet"
			else if (currentImage == inputChip) {
				// Trova la scommessa corrispondente nel list inputBets
				int buttonId = getButtonIdFromButton(clickedButton); // Funzione per ottenere l'ID del bottone
				InputBet inputBet = findInputBetByButtonId(buttonId);
				if (inputBet != null) {
					// Ripristina il saldo con il valore della scommessa
					Main.player.addMoney(inputBet.getBetAmount());
					refreshMoney();
					// Rimuovi la scommessa dalla lista inputBets
					inputBets.remove(inputBet);
					// Rimuovi l'immagine dal bottone
					clickedButton.setGraphic(null);
					// Resetta le variabili di "Input Bet"
					inputBetIsPositioned = false;
					inputBetAmount = 0;
					return;
				}
			}
			// Altrimenti, è una scommessa normale (chip standard)
			else {
				// Recupera il prezzo corrispondente all'immagine
				int priceToAdd = getPriceForImage(currentImage);
				// Rimuovi l'immagine
				clickedButton.setGraphic(null);
				// Aggiungi il prezzo del numero al denaro (corrispondente alla rimozione)
				Main.player.addMoney(priceToAdd);
				refreshMoney(); // Ricalcola e aggiorna il saldo
				currentEighteen.clear();
				return;
			}
		}
		if (allInIsClicked && !allInIsPositioned) {
			// Aggiungi l'immagine solo se il saldo è sufficiente
			ImageView imageView = new ImageView(allIn);
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			selectedNums.add(num); // Aggiungi il numero alla lista
			allInIsPositioned = true;
			Main.player.setMoney(0);
			refreshMoney();
			return;
		}
		if (!inputBetIsPositioned) {
			ImageView imageView = new ImageView(inputChip);
			imageView.setFitWidth(25);
			imageView.setFitHeight(25);
			clickedButton.setGraphic(imageView);
			inputBetIsPositioned = true;
			if (clickedButton.getText().equalsIgnoreCase("1 to 18")) {
				inputBets.add(new InputBet(inputBetAmount, 49)); // 49 è id per i primi 18
			} else {
				inputBets.add(new InputBet(inputBetAmount, 50)); // 46 è id per gli ultimi 18
			}
			Main.player.subtractMoney(inputBetAmount);
			if (Main.player.getMoney() != 0) {
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
			}
			refreshMoney();
			return;
		}
		// Se l'utente ha abbastanza denaro e c'è una chip selezionata
		if (selectedImage != null && Main.player.getMoney() >= selectedPrice) {
			// Aggiungi la selezione dei numeri in base al bottone premuto
			if (clickedButton.getText().equalsIgnoreCase("1 to 18")) {
				for (int i = 1; i <= 18; i++) {
					currentEighteen.add(i);
				}
			} else {
				for (int i = 19; i <= 36; i++) {
					currentEighteen.add(i);
				}
			}
			// Aggiungi l'immagine al bottone
			ImageView imageView = new ImageView(selectedImage);
			imageView.setFitWidth(20); // Imposta la larghezza dell'immagine
			imageView.setFitHeight(20); // Imposta l'altezza dell'immagine
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			// Sottrai il prezzo dal saldo
			Main.player.subtractMoney(selectedPrice);
			refreshMoney(); // Ricalcola e aggiorna il saldo
		}
	}

	@FXML
	public void handleRow1(ActionEvent event) {
		if (isAnimationRunning) {
			return;
		}
		Button clickedButton = (Button) event.getSource();
		currentRow.addAll(firstRow);
		// Verifica se c'è già un'immagine nel bottone
		if (clickedButton.getGraphic() != null) {
			// Se c'è già un'immagine, dobbiamo determinare quale tipo di scommessa è stata
			// piazzata
			ImageView currentImageView = (ImageView) clickedButton.getGraphic();
			Image currentImage = currentImageView.getImage();
			// Controlla se è una scommessa "All In"
			if (currentImage == allIn) {
				// Ripristina il saldo con il valore di "All In"
				Main.player.addMoney(allInQuantity);
				refreshMoney();
				// Resetta le variabili di "All In"
				allInIsClicked = false;
				allInIsPositioned = false;
				allInQuantity = 0;
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
				// Rimuovi l'immagine dal bottone
				clickedButton.setGraphic(null);
				return;
			}
			// Controlla se è una scommessa "Input Bet"
			else if (currentImage == inputChip) {
				// Trova la scommessa corrispondente nel list inputBets
				int buttonId = getButtonIdFromButton(clickedButton); // Funzione per ottenere l'ID del bottone
				InputBet inputBet = findInputBetByButtonId(buttonId);
				if (inputBet != null) {
					// Ripristina il saldo con il valore della scommessa
					Main.player.addMoney(inputBet.getBetAmount());
					refreshMoney();
					// Rimuovi la scommessa dalla lista inputBets
					inputBets.remove(inputBet);
					// Rimuovi l'immagine dal bottone
					clickedButton.setGraphic(null);
					// Resetta le variabili di "Input Bet"
					inputBetIsPositioned = false;
					inputBetAmount = 0;
					return;
				}
			}
			// Altrimenti, è una scommessa normale (chip standard)
			else {
				// Recupera il prezzo corrispondente all'immagine
				int priceToAdd = getPriceForImage(currentImage);
				// Rimuovi l'immagine
				clickedButton.setGraphic(null);
				// Aggiungi il prezzo del numero al denaro (corrispondente alla rimozione)
				Main.player.addMoney(priceToAdd);
				refreshMoney(); // Ricalcola e aggiorna il saldo
				currentRow.clear();
				return;
			}
		}
		if (allInIsClicked && !allInIsPositioned) {
			// Aggiungi l'immagine solo se il saldo è sufficiente
			ImageView imageView = new ImageView(allIn);
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			selectedNums.add(num); // Aggiungi il numero alla lista
			allInIsPositioned = true;
			Main.player.setMoney(0);
			refreshMoney();
			return;
		}
		if (!inputBetIsPositioned) {
			ImageView imageView = new ImageView(inputChip);
			imageView.setFitWidth(25);
			imageView.setFitHeight(25);
			clickedButton.setGraphic(imageView);
			inputBetIsPositioned = true;
			inputBets.add(new InputBet(inputBetAmount, 39)); // 39 è id per prima riga
			Main.player.subtractMoney(inputBetAmount);
			if (Main.player.getMoney() != 0) {
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
			}
			refreshMoney();
			return;
		}
		// Se l'utente ha abbastanza denaro e c'è una chip selezionata
		if (selectedImage != null && Main.player.getMoney() >= selectedPrice) {
			// Aggiungi l'immagine al bottone
			ImageView imageView = new ImageView(selectedImage);
			imageView.setFitWidth(20); // Imposta la larghezza dell'immagine
			imageView.setFitHeight(20); // Imposta l'altezza dell'immagine
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			// Sottrai il prezzo dal saldo
			Main.player.subtractMoney(selectedPrice);
			refreshMoney(); // Ricalcola e aggiorna il saldo
		}
	}

	@FXML
	public void handleRow2(ActionEvent event) {
		if (isAnimationRunning) {
			return;
		}
		Button clickedButton = (Button) event.getSource();
		currentRow.addAll(secondRow);
		// Verifica se c'è già un'immagine nel bottone
		if (clickedButton.getGraphic() != null) {
			// Se c'è già un'immagine, dobbiamo determinare quale tipo di scommessa è stata
			// piazzata
			ImageView currentImageView = (ImageView) clickedButton.getGraphic();
			Image currentImage = currentImageView.getImage();
			// Controlla se è una scommessa "All In"
			if (currentImage == allIn) {
				// Ripristina il saldo con il valore di "All In"
				Main.player.addMoney(allInQuantity);
				refreshMoney();
				// Resetta le variabili di "All In"
				allInIsClicked = false;
				allInIsPositioned = false;
				allInQuantity = 0;
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
				// Rimuovi l'immagine dal bottone
				clickedButton.setGraphic(null);
				return;
			}
			// Controlla se è una scommessa "Input Bet"
			else if (currentImage == inputChip) {
				// Trova la scommessa corrispondente nel list inputBets
				int buttonId = getButtonIdFromButton(clickedButton); // Funzione per ottenere l'ID del bottone
				InputBet inputBet = findInputBetByButtonId(buttonId);
				if (inputBet != null) {
					// Ripristina il saldo con il valore della scommessa
					Main.player.addMoney(inputBet.getBetAmount());
					refreshMoney();
					// Rimuovi la scommessa dalla lista inputBets
					inputBets.remove(inputBet);
					// Rimuovi l'immagine dal bottone
					clickedButton.setGraphic(null);
					// Resetta le variabili di "Input Bet"
					inputBetIsPositioned = false;
					inputBetAmount = 0;
					return;
				}
			}
			// Altrimenti, è una scommessa normale (chip standard)
			else {
				// Recupera il prezzo corrispondente all'immagine
				int priceToAdd = getPriceForImage(currentImage);
				// Rimuovi l'immagine
				clickedButton.setGraphic(null);
				// Aggiungi il prezzo del numero al denaro (corrispondente alla rimozione)
				Main.player.addMoney(priceToAdd);
				refreshMoney(); // Ricalcola e aggiorna il saldo
				currentRow.clear();
				return;
			}
		}
		if (allInIsClicked && !allInIsPositioned) {
			// Aggiungi l'immagine solo se il saldo è sufficiente
			ImageView imageView = new ImageView(allIn);
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			selectedNums.add(num); // Aggiungi il numero alla lista
			allInIsPositioned = true;
			Main.player.setMoney(0);
			refreshMoney();
			return;
		}
		if (!inputBetIsPositioned) {
			ImageView imageView = new ImageView(inputChip);
			imageView.setFitWidth(25);
			imageView.setFitHeight(25);
			clickedButton.setGraphic(imageView);
			inputBetIsPositioned = true;
			inputBets.add(new InputBet(inputBetAmount, 40)); // 40 è id per seconda riga
			Main.player.subtractMoney(inputBetAmount);
			if (Main.player.getMoney() != 0) {
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
			}
			refreshMoney();
			return;
		}
		// Se l'utente ha abbastanza denaro e c'è una chip selezionata
		if (selectedImage != null && Main.player.getMoney() >= selectedPrice) {
			// Aggiungi l'immagine al bottone
			ImageView imageView = new ImageView(selectedImage);
			imageView.setFitWidth(20); // Imposta la larghezza dell'immagine
			imageView.setFitHeight(20); // Imposta l'altezza dell'immagine
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			// Sottrai il prezzo dal saldo
			Main.player.subtractMoney(selectedPrice);
			refreshMoney(); // Ricalcola e aggiorna il saldo
		}
	}

	@FXML
	public void handleRow3(ActionEvent event) {
		if (isAnimationRunning) {
			return;
		}
		Button clickedButton = (Button) event.getSource();
		currentRow.addAll(thirdRow);
		// Verifica se c'è già un'immagine nel bottone
		if (clickedButton.getGraphic() != null) {
			// Se c'è già un'immagine, dobbiamo determinare quale tipo di scommessa è stata
			// piazzata
			ImageView currentImageView = (ImageView) clickedButton.getGraphic();
			Image currentImage = currentImageView.getImage();
			// Controlla se è una scommessa "All In"
			if (currentImage == allIn) {
				// Ripristina il saldo con il valore di "All In"
				Main.player.addMoney(allInQuantity);
				refreshMoney();
				// Resetta le variabili di "All In"
				allInIsClicked = false;
				allInIsPositioned = false;
				allInQuantity = 0;
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
				// Rimuovi l'immagine dal bottone
				clickedButton.setGraphic(null);
				return;
			}
			// Controlla se è una scommessa "Input Bet"
			else if (currentImage == inputChip) {
				// Trova la scommessa corrispondente nel list inputBets
				int buttonId = getButtonIdFromButton(clickedButton); // Funzione per ottenere l'ID del bottone
				InputBet inputBet = findInputBetByButtonId(buttonId);
				if (inputBet != null) {
					// Ripristina il saldo con il valore della scommessa
					Main.player.addMoney(inputBet.getBetAmount());
					refreshMoney();
					// Rimuovi la scommessa dalla lista inputBets
					inputBets.remove(inputBet);
					// Rimuovi l'immagine dal bottone
					clickedButton.setGraphic(null);
					// Resetta le variabili di "Input Bet"
					inputBetIsPositioned = false;
					inputBetAmount = 0;
					return;
				}
			}
			// Altrimenti, è una scommessa normale (chip standard)
			else {
				// Recupera il prezzo corrispondente all'immagine
				int priceToAdd = getPriceForImage(currentImage);
				// Rimuovi l'immagine
				clickedButton.setGraphic(null);
				// Aggiungi il prezzo del numero al denaro (corrispondente alla rimozione)
				Main.player.addMoney(priceToAdd);
				refreshMoney(); // Ricalcola e aggiorna il saldo
				currentRow.clear();
				return;
			}
		}
		if (allInIsClicked && !allInIsPositioned) {
			// Aggiungi l'immagine solo se il saldo è sufficiente
			ImageView imageView = new ImageView(allIn);
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			selectedNums.add(num); // Aggiungi il numero alla lista
			allInIsPositioned = true;
			Main.player.setMoney(0);
			refreshMoney();
			return;
		}
		if (!inputBetIsPositioned) {
			ImageView imageView = new ImageView(inputChip);
			imageView.setFitWidth(25);
			imageView.setFitHeight(25);
			clickedButton.setGraphic(imageView);
			inputBetIsPositioned = true;
			inputBets.add(new InputBet(inputBetAmount, 41)); // 41 è id per terza riga
			Main.player.subtractMoney(inputBetAmount);
			if (Main.player.getMoney() != 0) {
				allInButton.setVisible(true);
				betComboBox.setVisible(true);
				inputBox.setVisible(true);
			}
			refreshMoney();
			return;
		}
		// Se l'utente ha abbastanza denaro e c'è una chip selezionata
		if (selectedImage != null && Main.player.getMoney() >= selectedPrice) {
			// Aggiungi l'immagine al bottone
			ImageView imageView = new ImageView(selectedImage);
			imageView.setFitWidth(20); // Imposta la larghezza dell'immagine
			imageView.setFitHeight(20); // Imposta l'altezza dell'immagine
			clickedButton.setGraphic(imageView); // Aggiungi l'immagine al bottone
			// Sottrai il prezzo dal saldo
			Main.player.subtractMoney(selectedPrice);
			refreshMoney(); // Ricalcola e aggiorna il saldo
		}
	}

	public void refreshMoney() {
		lblMoney.setText(Main.player.getMoney().toString());
		if (Main.player.getMoney() == 0) {
			lblMoney.setStyle("-fx-text-fill: red;");
		} else {
			lblMoney.setStyle("-fx-text-fill: white;");
		}
	}

	@FXML
	private void goBack(ActionEvent event) {
		switchScene(event, "/menu/fxml/GameChoiceMenu.fxml");
	}

	@FXML
	private void showStats(ActionEvent event) {
		if (gameChart != null && gameChart.isWindowOpen()) {
			// If it's open, show a warning alert
			gameChart.showGraphAlreadyOpenAlert();
		} else {
			// If the graph window is not open, create a new instance and show it
			gameChart = new GameChart();
			gameChart.showChartWindow();
		}
	}


	
	public void debug() {
		System.out.println("Games Played: " + Main.player.getGamesPlayed());
		System.out.println("Games won: " + Main.player.getGamesWon());
		System.out.println("Games lost: " + Main.player.getGamesLost());
		System.out.println("Money " + Main.player.getMoney());
		// IMPLEMENTARE GLI ALTRI METODI
	}
}
