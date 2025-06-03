package guessNumber;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Main;

public class GuessNumber {
    private static int secretNumber;
    private int attempts;
    private double multiplier;
    private final int totalAttempts;
    private double baseMultiplier;
    private static int minRange;
    private static int maxRange;
    private int bet = 0;
    private int totalGames = 0;
    private int victory = 0;
    private int loss = 0;
    private final int initialBudget;

    public GuessNumber() {
        this(15, 1); // Valori di default
    }

    public GuessNumber(int maxRange, int minRange) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.attempts = 0;
        this.baseMultiplier = calculateBaseMultiplier();
        this.multiplier = baseMultiplier;
        this.totalAttempts = (maxRange - minRange);
        this.initialBudget = Main.player.getMoney();
    }
    
    public int autoGuessNumber(int bet) {
        // Randomly select mode with adjusted base multipliers
        int mode = (int) (Math.random() * 3);
        int min = 1;
        int max;
        double baseMultiplier;

        switch (mode) {
            case 0:  // 1-10 numbers
                max = 10;
                baseMultiplier = 0.8;
                break;
            case 1:  // 1-15 numbers
                max = 15;
                baseMultiplier = 1.2;
                break;
            case 2:  // 1-20 numbers
                max = 20;
                baseMultiplier = 1.6; 
                break;
            default:
                max = 10;
                baseMultiplier = 0.8;
                break;
        }

        int secret = generateRandomNumber(min, max); //random num in range
        List<Integer> numbers = new ArrayList<>();
        for (int i = min; i <= max; i++) numbers.add(i); //adding fake tiles
        Collections.shuffle(numbers); //mix all list

        int attempts = 0;
        for (int num : numbers) { //iterate through list until find a secret num
            if (num == secret) {
                // Aggressive multiplier decay formula
                double step = (baseMultiplier + 1.2) / (max - min);
                double finalMultiplier = baseMultiplier - (step * attempts);
                return (int) Math.round(bet * Math.max(finalMultiplier, -1.0));
            }
            attempts++;
        }
        return -bet;
    }


    private static int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static int generateRandomNumber() {
    	int range = maxRange - minRange + 1;
    	secretNumber = (int) (Math.random() * range) + minRange;
    	return secretNumber;
    }
    
    private double calculateBaseMultiplier() {
        int range = maxRange - minRange + 1;
        if (range <= 10) return 1.5;
        if (range <= 15) return 2.0;
        return 3.0;
    }
    
    public boolean verifyNumber(int chosenNumber) {
        return secretNumber == chosenNumber;
    }

    public double updateMultiplier() {
        attempts++;
        double progress = (double) attempts / totalAttempts;
        
        if(progress <= 0.8) {
            multiplier = baseMultiplier * (1 - (progress/0.8));
        } else {
            double negativeProgress = (progress - 0.8)/0.2;
            multiplier = -1 * (1 - Math.exp(-5 * negativeProgress));
        }
        
        multiplier = Math.round(multiplier * 100.0) / 100.0;
        return multiplier;
    }

    public void calculateFinalBudget() {
        double winAmount = bet * multiplier;
        
        // Garantiamo che non si perda più della puntata
        if(winAmount < -bet) winAmount = -bet;
        
        int newBalance = (int) Math.round(initialBudget + winAmount);
        if(newBalance < 0) newBalance = 0;
        
        Main.player.setMoney(newBalance);
    }

    
    public void resetGame() {
        this.attempts = 0;
        this.multiplier = baseMultiplier;
    }

    public void incrementVictory() {
        victory += 1;
    }

    public int getVictoryCount() {
        return victory;
    }

    public void incrementLoss() {
        loss += 1;
    }

    public int getLossCount() {
        return loss;
    }

    public boolean isGameWinnedOrLosed() {
        return Main.player.getMoney() > initialBudget;
    }

    // GETTER e SETTER
    public double getMultiplier() {
        return multiplier;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public int getRemainingAttempts() {
        return totalAttempts - attempts;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

    public String getMoney() {
        return Main.player.getMoney() + " $ ";
    }

    public int getInitialBudget() {
        return initialBudget;
    }
}