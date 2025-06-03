package main;

import javafx.beans.property.*;

public class Player implements Entity, MoneyHandler, MoneyValidator {
    
    // Observable Properties
    private final IntegerProperty money = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final IntegerProperty gamesPlayed = new SimpleIntegerProperty();
    private final IntegerProperty gamesWon = new SimpleIntegerProperty();
    private final IntegerProperty gamesLost = new SimpleIntegerProperty();
    private final IntegerProperty highestEarning = new SimpleIntegerProperty();
    private final BooleanProperty moneyIsZero = new SimpleBooleanProperty();

    public Player(String username, Integer startingMoney, Integer gamesPlayed, 
                 Integer gamesWon, Integer gamesLost, Integer highestEarning) {
        this.username.set(username);
        this.money.set(startingMoney);
        this.gamesPlayed.set(gamesPlayed);
        this.gamesWon.set(gamesWon);
        this.gamesLost.set(gamesLost);
        this.highestEarning.set(highestEarning);
        
        // Auto-update moneyIsZero when money changes
        money.addListener((obs, oldVal, newVal) -> {
            moneyIsZero.set(newVal.intValue() <= 0);
        });
    }

    // ===== Property Accessors =====
    public IntegerProperty moneyProperty() { return money; }
    public BooleanProperty moneyIsZeroProperty() { return moneyIsZero; }
    public StringProperty usernameProperty() { return username; }
    public IntegerProperty gamesPlayedProperty() { return gamesPlayed; }
    public IntegerProperty gamesWonProperty() { return gamesWon; }
    public IntegerProperty gamesLostProperty() { return gamesLost; }
    public IntegerProperty highestEarningProperty() { return highestEarning; }

    // ===== Original Methods (Adapted for Properties) =====
    @Override
    public String getName() { return username.get(); }

    @Override
    public Integer getGamesPlayed() { return gamesPlayed.get(); }

    @Override
    public Integer getGamesWon() { return gamesWon.get(); }

    @Override
    public Integer getGamesLost() { return gamesLost.get(); }

    @Override
    public void incrementGamesPlayed() { gamesPlayed.set(gamesPlayed.get() + 1); }

    @Override
    public void incrementGamesWon() { gamesWon.set(gamesWon.get() + 1); }

    @Override
    public void incrementGamesLost() { gamesLost.set(gamesLost.get() + 1); }

    public Integer getMoney() { return money.get(); }

    public Integer getHighestEarning() { return highestEarning.get(); }

    public Boolean isMoneyZero() { return moneyIsZero.get(); }

    @Override
    public void setMoney(int amount) {
        int validated = validate(amount);
        if(validated > highestEarning.get()) {
            highestEarning.set(validated);
        }
        money.set(validated);
    }

    @Override
    public void addMoney(int amount) {
        int newAmount = validateOperation(money.get(), amount);
        if(newAmount > highestEarning.get()) {
            highestEarning.set(newAmount);
        }
        money.set(newAmount);
    }

    @Override
    public void subtractMoney(int amount) {
        money.set(validateOperation(money.get(), -amount));
    }
    
    public Double getWinLossRatio() {
        if (gamesLost.get() == 0) return (double) gamesWon.get();
        return (double) gamesWon.get() / gamesLost.get();
    }

    public Integer getNetProfit() {
        return money.get() - highestEarning.get();
    }

    @Override
    public String toString() {
        return username.get() + " | Money: $" + money.get() 
             + " | Wins: " + gamesWon.get() + " | Losses: " + gamesLost.get();
    }

    // ===== MoneyIsZero Methods =====
    public Boolean getMoneyIsZero() {
        return moneyIsZero.get();
    }

    public void setMoneyIsZero(Boolean moneyIsZero) {
        this.moneyIsZero.set(moneyIsZero);
    }
}