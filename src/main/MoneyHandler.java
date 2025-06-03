package main;

public interface MoneyHandler {
    void setMoney(int amount);
    void addMoney(int amount);
    void subtractMoney(int amount);
    Integer getMoney();
    Boolean isMoneyZero();
}