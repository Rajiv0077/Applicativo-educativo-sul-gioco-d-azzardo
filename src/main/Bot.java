package main;

public class Bot implements Entity {

    private String botName;
    private Integer gamesPlayed;
    private Integer gamesWon;
    private Integer gamesLost;

    public Bot(String botName) {
        this.botName = botName;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
    }

    // 🔹 **Game Tracking Methods (Consistent with Player)**
    @Override
    public String getName() { return botName; }
    
    @Override
    public Integer getGamesPlayed() { return gamesPlayed; }

    @Override
    public Integer getGamesWon() { return gamesWon; }

    @Override
    public Integer getGamesLost() { return gamesLost; }

    @Override
    public void incrementGamesPlayed() { gamesPlayed++; }

    @Override
    public void incrementGamesWon() { gamesWon++; }

    @Override
    public void incrementGamesLost() { gamesLost++; }

    // 🔹 **Bot's AI-based Actions**
    public int rollDice() {
        return (int) (Math.random() * 6) + 1;
    }

    public int spinRouletteWheel() {
        return (int) (Math.random() * 36); // Standard 0-36 roulette numbers
    }

    public int drawBlackjackCard() {
        return (int) (Math.random() * 11) + 1; // Values 1-11 like real blackjack
    }

    // 🔹 **Casino House Logic**
    public boolean decideToHitInBlackjack(int currentScore) {
        return currentScore < 17; // Standard casino rule: hit on 16 or lower
    }

    @Override
    public String toString() {
        return botName + " | Games Played: " + gamesPlayed + " | Wins: " + gamesWon + " | Losses: " + gamesLost;
    }
}
