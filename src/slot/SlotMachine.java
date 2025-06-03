package slot;

import java.util.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.util.Duration;
import menu.*;
import slot.controller.SlotMachineController;

public class SlotMachine {
	private String winDescriptor = "";
	private ArrayList<Integer> winCounts = new ArrayList<>();
	private int[][] rows;

	private ImageView imgSlot1, imgSlot2, imgSlot3, imgSlot1h, imgSlot2h, imgSlot3h, imgSlot4h, imgSlot5h, imgSlot6h,
			imgSlot7h, imgSlot8h, imgSlot9h;

	public SlotMachine(ImageView imgSlot1, ImageView imgSlot2, ImageView imgSlot3, ImageView imgSlot1h,
			ImageView imgSlot2h, ImageView imgSlot3h, ImageView imgSlot4h, ImageView imgSlot5h, ImageView imgSlot6h,
			ImageView imgSlot7h, ImageView imgSlot8h, ImageView imgSlot9h) {

		this.imgSlot1 = imgSlot1;
		this.imgSlot2 = imgSlot2;
		this.imgSlot3 = imgSlot3;
		this.imgSlot1h = imgSlot1h;
		this.imgSlot2h = imgSlot2h;
		this.imgSlot3h = imgSlot3h;
		this.imgSlot4h = imgSlot4h;
		this.imgSlot5h = imgSlot5h;
		this.imgSlot6h = imgSlot6h;
		this.imgSlot7h = imgSlot7h;
		this.imgSlot8h = imgSlot8h;
		this.imgSlot9h = imgSlot9h;
	}

	public Integer playRound(Integer bet, SlotMachineController controller, ImageView imgSlot1, ImageView imgSlot2,
			ImageView imgSlot3, ImageView imgSlot1h, ImageView imgSlot2h, ImageView imgSlot3h, ImageView imgSlot4h,
			ImageView imgSlot5h, ImageView imgSlot6h, ImageView imgSlot7h, ImageView imgSlot8h, ImageView imgSlot9h,
			Label lblSlot1, Label lblSlot2, Label lblSlot3, Label lblSlot1h, Label lblSlot2h, Label lblSlot3h,
			Label lblSlot4h, Label lblSlot5h, Label lblSlot6h, Label lblSlot7h, Label lblSlot8h, Label lblSlot9h,
			Label lblWinLose, Integer moneySpent, Boolean moneyIsZero, Label lblMWCurrency, ActionEvent event,
			Label lblMLCurrency, Integer maxSlot) {

		if (moneyIsZero) {

			return bet = 0;
		}

		// 1. Genera simboli casuali
		ArrayList<Integer> symbols = randomNumber(maxSlot);

		// 2. Associa immagini alle slot
		ArrayList<ImageView> imgSlots = new ArrayList<ImageView>();
		// Prima riga (Alto: 4 colonne)
		switch (maxSlot) {
		case 3: // 3x1
			imgSlots.add(imgSlot1);
			imgSlots.add(imgSlot2);
			imgSlots.add(imgSlot3);
			break;

		case 9: // 3x3
			imgSlots.add(imgSlot1h);
			imgSlots.add(imgSlot2h);
			imgSlots.add(imgSlot3h);
			imgSlots.add(imgSlot1);
			imgSlots.add(imgSlot2);
			imgSlots.add(imgSlot3);
			imgSlots.add(imgSlot4h);
			imgSlots.add(imgSlot5h);
			imgSlots.add(imgSlot6h);
			break;

		case 12: // 3x4
			imgSlots.add(imgSlot1h);
			imgSlots.add(imgSlot2h);
			imgSlots.add(imgSlot3h);
			imgSlots.add(imgSlot7h);
			imgSlots.add(imgSlot1);
			imgSlots.add(imgSlot2);
			imgSlots.add(imgSlot3);
			imgSlots.add(imgSlot8h);
			imgSlots.add(imgSlot4h);
			imgSlots.add(imgSlot5h);
			imgSlots.add(imgSlot6h);
			imgSlots.add(imgSlot9h);
			break;
		}

		association(symbols, imgSlots, new ArrayList<>(), maxSlot);

		// 3. Analizza risultato
		String comparisonResult = numberComparison(symbols, maxSlot);
		winDescriptor = comparisonResult;
		// 4. Calcola vincita
		MoneyManagerSlot mms = new MoneyManagerSlot();
		Integer moneyMultiplied = mms.moneyMultiplied(winDescriptor, moneySpent, maxSlot, getWinCounts());

		// 5. Mostra risultati
		if (!winDescriptor.equals("none")) {
			victoryState(lblWinLose, maxSlot);
		} else {
			lblWinLose.setText("YOU'VE LOST!");
			lblWinLose.setStyle("-fx-text-fill: red;-fx-font-size: 40px;-fx-font-weight: bold;");
		}

		ArrayList<Integer> delays = generateDelayList(maxSlot);
		mms.moneyMultipliedDisplay(lblMWCurrency, moneyMultiplied, delays, maxSlot);

		return mms.setMoneyLeft(moneyMultiplied, moneySpent, bet);
	}

	public void updateMoneyAnimation(int start, int end, Label label) {
		Timeline timeline = new Timeline();
		int diff = end - start;
		int steps = Math.max(Math.abs(diff), 1); // Almeno 1 step

		for (int i = 0; i <= steps; i++) {
			final int val = start + (diff * i / steps);
			KeyFrame kf = new KeyFrame(Duration.millis((700L * i) / steps), // Distribuzione uniforme
					e -> label.setText("$" + val));
			timeline.getKeyFrames().add(kf);
		}

		// KeyFrame finale per garantire il valore esatto
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(700), e -> label.setText("$" + end)));

		timeline.play();
	}

	public ArrayList<ImageView> imgSlot(ArrayList<ImageView> imgSlot, Integer maxSlot, ImageView... imageViews) {
		imgSlot.clear();

		// Ordine basato sulla logica del playRound
		switch (maxSlot) {
		case 3:
			Collections.addAll(imgSlot, imageViews[0], imageViews[1], imageViews[2]);
			break;
		case 9:
			Collections.addAll(imgSlot, imageViews[3], imageViews[4], imageViews[5], // Righe superiori
					imageViews[0], imageViews[1], imageViews[2], // Centro
					imageViews[6], imageViews[7], imageViews[8]); // Inferiori
			break;
		case 12:
			Collections.addAll(imgSlot, imageViews[3], imageViews[4], imageViews[5], imageViews[9], // Riga 1
					imageViews[0], imageViews[1], imageViews[2], imageViews[10], // Riga 2
					imageViews[6], imageViews[7], imageViews[8], imageViews[11]); // Riga 3
			break;
		}
		return imgSlot;
	}

	private ArrayList<Integer> generateDelayList(int maxSlot) {
		ArrayList<Integer> delayList = new ArrayList<>();

		delayList.add(700);

		return delayList;
	}

	private String getSymbolName(int number) {
		switch (number) {
		case 1:
			return "JA";
		case 2:
			return "RP";
		case 3:
			return "RS";
		case 4:
			return "RB";
		case 5:
			return "SS";
		case 6:
			return "AT";
		default:
			return "UNKNOWN";
		}
	}

	private void association(ArrayList<Integer> symbols, ArrayList<ImageView> imgSlots, ArrayList<Integer> endAnim,
			Integer maxSlot) {
		for (int i = 0; i < symbols.size(); i++) { // Usa symbols.size() invece di imgSlots.size()
			try {
				ImageView currentSlot = imgSlots.get(i);
				int symbolNumber = symbols.get(i);

// Debug avanzato
				System.out.println("Modalità " + maxSlot + " | Slot " + i + " (" + getImageName(currentSlot) + ") → "
						+ symbolNumber);

				String path = "/resources/img/Icon" + symbolNumber + ".jpg";
				Image image = new Image(getClass().getResourceAsStream(path));

				applySlotAnimation(currentSlot, image);
			} catch (Exception e) {
				System.err.println("Errore slot " + i + ": " + e.getMessage());
			}
		}
	}

	public ArrayList<Integer> randomNumber(int totalSlots) {
		ArrayList<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < totalSlots; i++) {
			int randomN = (int) (Math.random() * 6) + 1;
			numbers.add(randomN);
		}
		return numbers;
	}

	private void applySlotAnimation(ImageView imgSlot, Image finalImage) {
		Timeline timeline = new Timeline();

		// 1. Ferma eventuali animazioni precedenti
		timeline.stop();

		// 2. Svuota i keyframe esistenti
		timeline.getKeyFrames().clear();

		// 3. Animazione con immagini casuali (senza sovrascrittura finale)
		for (int i = 0; i < 14; i++) { // 700ms / 50ms = 14 frame
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 50), e -> imgSlot.setImage(getRandomImage())));
		}

		// 4. Keyframe finale CON GARANZIA di immagine corretta
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(700), e -> {
			// Forza l'update sincrono
			Platform.runLater(() -> {
				imgSlot.setImage(finalImage);
				imgSlot.setUserData(finalImage); // Memorizza l'immagine corretta
			});
		}));

		// 5. Avvia l'animazione
		timeline.play();
	}

	public String getImageName(ImageView img) {
		// Implementa una mappa o switch-case basato sugli fx:id
		if (img == imgSlot1h)
			return "1h";
		if (img == imgSlot2h)
			return "2h";
		if (img == imgSlot3h)
			return "3h";
		if (img == imgSlot1)
			return "1";
		if (img == imgSlot2)
			return "2";
		if (img == imgSlot3)
			return "3";
		if (img == imgSlot4h)
			return "4h";
		if (img == imgSlot5h)
			return "5h";
		if (img == imgSlot6h)
			return "6h";
		if (img == imgSlot7h)
			return "7h";
		if (img == imgSlot8h)
			return "8h";
		if (img == imgSlot9h)
			return "9h";

		return "unknown";
	}

	private Image getRandomImage() {
		String[] images = { "/resources/img/Icon1.jpg", "/resources/img/Icon2.jpg", "/resources/img/Icon3.jpg",
				"/resources/img/Icon4.jpg", "/resources/img/Icon5.jpg", "/resources/img/Icon6.jpg" };
		int index = new Random().nextInt(images.length);
		return new Image(images[index]);
	}

	private String numberComparison(ArrayList<Integer> symbols, int maxSlot) {
		winCounts.clear();
		int columns = determineColumns(maxSlot);
		int numRows = maxSlot / columns;
		rows = new int[numRows][columns];

		System.out.println("\n=== NUOVA PARTITA ===");
		System.out.println("Simboli generati: " + symbols);

		// Popolazione matrice in base al layout
		if (maxSlot == 12) {
			populate3x4Matrix(symbols, numRows, columns);
		} else {
			populateStandardMatrix(symbols, numRows, columns);
		}

		// Analisi specifica per layout
		switch (maxSlot) {
		case 3: // 3x1
			analyze3x1Rows();
			break;
		case 9: // 3x3
			analyze3x3Rows();
			break;
		case 12: // 4x3
			analyze3x4Rows();
			break;
		}

		return checkResults(maxSlot);
	}

	// Metodi per la popolazione della matrice
	private void populate3x4Matrix(ArrayList<Integer> symbols, int numRows, int columns) {
		int index = 0;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < columns; j++) {
				rows[i][j] = symbols.get(index);
				System.out.println(
						"Riga " + (i + 1) + ", Colonna " + (j + 1) + " → " + getSymbolName(symbols.get(index)));
				index++;
			}
		}
	}

	private void populateStandardMatrix(ArrayList<Integer> symbols, int numRows, int columns) {
		int index = 0;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < columns; j++) {
				if (index < symbols.size()) {
					rows[i][j] = symbols.get(index);
					System.out.println(
							"Riga " + (i + 1) + ", Colonna " + (j + 1) + " → " + getSymbolName(symbols.get(index)));
				}
				index++;
			}
		}
	}

	// Metodi per l'analisi delle righe
	private void analyze3x1Rows() {
		// Analisi singola riga orizzontale
		int[] row = rows[0];
		int maxCount = countSymbols(row);
		winCounts.add(maxCount);
		System.out.println("3x1 - Massimo simboli: " + maxCount);
	}

	private void analyze3x3Rows() {
		// Analisi 3 righe orizzontali
		for (int i = 0; i < 3; i++) {
			int[] row = rows[i];
			int maxCount = countSymbols(row);
			winCounts.add(maxCount);
			System.out.println("3x3 - Riga " + (i + 1) + ": " + maxCount);
		}
	}

	private void analyze3x4Rows() {
		// Analizza nell'ordine: UP(0), MID(1), DOWN(2)
		for (int i = 0; i < 3; i++) {
			int[] row = rows[i];
			winCounts.add(countSymbols(row));
		}
	}

	// Metodo helper per il conteggio
	private int countSymbols(int[] row) {
		int[] counts = new int[7];
		for (int symbol : row) {
			if (symbol >= 1 && symbol <= 6) {
				counts[symbol]++;
			}
		}

		int max = 0;
		for (int j = 1; j <= 6; j++) {
			if (counts[j] > max) {
				max = counts[j];
			}
		}
		return max;
	}

	private String checkResults(int maxSlot) {
		int minWin = (maxSlot == 12) ? 3 : 2;
		boolean hasWin = false;
		boolean fullWin = true;
		int firstSymbol = rows[0][0];

		for (int i = 0; i < rows.length; i++) {
			if (winCounts.get(i) >= minWin)
				hasWin = true;
			for (int j = 0; j < rows[i].length; j++) { // Usa la lunghezza della riga
				if (rows[i][j] != firstSymbol)
					fullWin = false;
			}
			// Verifica jackpot

		}

		System.out.println("\nRISULTATO FINALE:");
		System.out.println("Vincite valide: " + hasWin);
		System.out.println("Jackpot: " + fullWin);

		return fullWin ? "f" : hasWin ? "multi-win" : "none";
	}

	public ArrayList<Integer> getWinCounts() {
		return winCounts;
	}

	private int determineColumns(int maxSlot) {
		switch (maxSlot) {
		case 3:
			return 3;
		case 9:
			return 3;
		case 12:
			return 4;
		default:
			return 3;
		}
	}

	// Metodi per le diverse modalitÃ  di gioco
	public Integer changeSlot3x1(Integer bet, SlotMachineController controller, ImageView imgSlot1, ImageView imgSlot2,
			ImageView imgSlot3, ImageView imgSlot1h, ImageView imgSlot2h, ImageView imgSlot3h, ImageView imgSlot4h,
			ImageView imgSlot5h, ImageView imgSlot6h, ImageView imgSlot7h, ImageView imgSlot8h, ImageView imgSlot9h,
			Label lblSlot1, Label lblSlot2, Label lblSlot3, Label lblSlot1h, Label lblSlot2h, Label lblSlot3h,
			Label lblSlot4h, Label lblSlot5h, Label lblSlot6h, Label lblSlot7h, Label lblSlot8h, Label lblSlot9h,
			Label lblWinLose, Integer moneySpent, Boolean moneyIsZero, Label lblMWCurrency, ActionEvent event,
			Label lblMLCurrency) {
		return playRound(bet, controller, imgSlot1, imgSlot2, imgSlot3, imgSlot1h, imgSlot2h, imgSlot3h, imgSlot4h,
				imgSlot5h, imgSlot6h, imgSlot7h, imgSlot8h, imgSlot9h, lblSlot1, lblSlot2, lblSlot3, lblSlot1h,
				lblSlot2h, lblSlot3h, lblSlot4h, lblSlot5h, lblSlot6h, lblSlot7h, lblSlot8h, lblSlot9h, lblWinLose,
				moneySpent, moneyIsZero, lblMWCurrency, event, lblMLCurrency, 3); // Label 4-12 (non usati)
	}

//Metodo per 3x3
	public Integer changeSlot3x3(Integer startMoney, SlotMachineController controller, ImageView imgSlot1,
			ImageView imgSlot2, ImageView imgSlot3, ImageView imgSlot1h, ImageView imgSlot2h, ImageView imgSlot3h,
			ImageView imgSlot4h, ImageView imgSlot5h, ImageView imgSlot6h, ImageView imgSlot7h, ImageView imgSlot8h,
			ImageView imgSlot9h, Label lblSlot1, Label lblSlot2, Label lblSlot3, Label lblSlot1h, Label lblSlot2h,
			Label lblSlot3h, Label lblSlot4h, Label lblSlot5h, Label lblSlot6h, Label lblSlot7h, Label lblSlot8h,
			Label lblSlot9h, Label lblWinLose, Integer moneySpent, Boolean moneyIsZero, Label lblMWCurrency,
			ActionEvent event, Label lblMLCurrency) {
		return playRound(startMoney, controller, imgSlot1, imgSlot2, imgSlot3, imgSlot1h, imgSlot2h, imgSlot3h,
				imgSlot4h, imgSlot5h, imgSlot6h, imgSlot7h, imgSlot8h, imgSlot9h, lblSlot1, lblSlot2, lblSlot3,
				lblSlot1h, lblSlot2h, lblSlot3h, lblSlot4h, lblSlot5h, lblSlot6h, lblSlot7h, lblSlot8h, lblSlot9h,
				lblWinLose, moneySpent, moneyIsZero, lblMWCurrency, event, lblMLCurrency, 9); // Label 10-12 (non usati)
	}

//Metodo per 4x3
	public Integer changeSlot3x4(Integer bet, SlotMachineController controller, ImageView imgSlot1, ImageView imgSlot2,
			ImageView imgSlot3, ImageView imgSlot1h, ImageView imgSlot2h, ImageView imgSlot3h, ImageView imgSlot4h,
			ImageView imgSlot5h, ImageView imgSlot6h, ImageView imgSlot7h, ImageView imgSlot8h, ImageView imgSlot9h,
			Label lblSlot1, Label lblSlot2, Label lblSlot3, Label lblSlot1h, Label lblSlot2h, Label lblSlot3h,
			Label lblSlot4h, Label lblSlot5h, Label lblSlot6h, Label lblSlot7h, Label lblSlot8h, Label lblSlot9h,
			Label lblWinLose, Integer moneySpent, Boolean moneyIsZero, Label lblMWCurrency, ActionEvent event,
			Label lblMLCurrency) {
		return playRound(bet, controller, imgSlot1, imgSlot2, imgSlot3, imgSlot1h, imgSlot2h, imgSlot3h, imgSlot4h,
				imgSlot5h, imgSlot6h, imgSlot7h, imgSlot8h, imgSlot9h, lblSlot1, lblSlot2, lblSlot3, lblSlot1h,
				lblSlot2h, lblSlot3h, lblSlot4h, lblSlot5h, lblSlot6h, lblSlot7h, lblSlot8h, lblSlot9h, lblWinLose,
				moneySpent, moneyIsZero, lblMWCurrency, event, lblMLCurrency, 12);
	}

	public ArrayList<Integer> endAnim(ArrayList<Integer> endAnim, Integer maxSlot) {
		endAnim.clear();
		endAnim.add(700); // Tempo fisso a 700ms per tutti i layout
		return endAnim;
	}

	// ________________________________

	private void victoryState(Label lblWinLose, int maxSlot) {
		StringBuilder message = new StringBuilder();
		double totalMultiplier = 0;
		final int finalTotalMultiplier = 0;
		MoneyManagerSlot mms = new MoneyManagerSlot();

		if (winDescriptor.equals("f")) {
			message.append("JACKPOT! (").append(mms.getJackpotMultiplier(maxSlot)).append("x)");
		} else if (winCounts != null && !winCounts.isEmpty()) {
			List<Integer> orderedWinCounts = new ArrayList<>();

			// Organizza le righe nell'ordine corretto
			switch (maxSlot) {
			case 3: // 3x1 - singola riga centrale
				orderedWinCounts.addAll(winCounts);
				break;

			case 9: // 3x3 - righe: UP, MID, DOWN
			case 12: // 3x4 - righe: UP, MID, DOWN
				if (winCounts.size() >= 3) {
					orderedWinCounts.add(winCounts.get(0)); // UP (prima riga)
					orderedWinCounts.add(winCounts.get(1)); // MID (seconda riga)
					orderedWinCounts.add(winCounts.get(2)); // DOWN (terza riga)
				}
				break;
			}

			String[] labels = maxSlot == 3 ? new String[] { "Mid" } : // Solo Mid per 3x1
					new String[] { "Up", "Mid", "Down" }; // Etichette standard

			for (int i = 0; i < orderedWinCounts.size(); i++) {
				int count = orderedWinCounts.get(i);
				if (count < (maxSlot == 12 ? 3 : 2))
					continue; // Filtra vincite non valide

				double multiplier = mms.calculateMultiplier(count, maxSlot);
				totalMultiplier += multiplier;

				message.append(labels[i]).append(": ").append(count).append("x (").append(multiplier).append("x)\n");
			}

			if (totalMultiplier > 0) {
				message.append("TOT: ").append(totalMultiplier).append("x");
			} else {
				message.setLength(0);
				message.append("Nessuna vincita valida");
			}
		} else {
			message.append("Nessuna vincita");
		}

		new Timeline(new KeyFrame(Duration.millis(700), e -> {
			lblWinLose.setText(message.toString());

			// Applica lo stile contestualmente al testo

			lblWinLose.setStyle("-fx-font-size: 35px; -fx-text-fill: #00ff00;");

		})).play();
	}



}