package main;

public interface MoneyValidator {
    default int validate(int amount) { //default gives you a permission to write non-abstract methods inside interfaces
        return Math.max(amount, 0);
    }
    
    default int validateOperation(int current, int delta) {
        return Math.max(current + delta, 0);
    }
}