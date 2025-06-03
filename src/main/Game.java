package main;

public abstract class Game {
    
    protected Integer minBet;

    public Game(Integer minBet) {
        this.minBet = minBet;
    }

    public Integer getMinBet() { return minBet; }

    public void setMinBet(Integer minBet) { this.minBet = minBet; }

    // **Abstract method for game logic (must be implemented in each game)**
    public abstract void startGame();
}
