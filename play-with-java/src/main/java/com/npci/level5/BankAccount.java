package com.npci.level5;

/**
 * Level 5: Abstraction - Abstract Bank Account Class
 *
 * Demonstrates abstract class for bank accounts.
 * Common functionality implemented, specific behavior defined as abstract.
 */
public abstract class BankAccount {

    protected String accountNumber;
    protected String holderName;
    protected double balance;
    protected String accountType;
    protected boolean isActive;

    public BankAccount(String accountNumber, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
        this.accountType = "General";
        this.isActive = true;
    }

    // Concrete methods
    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    public boolean isActive() { return isActive; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: Rs." + amount + " | New Balance: Rs." + balance);
        }
    }

    // Abstract methods - must be implemented by child classes
    public abstract void withdraw(double amount);
    public abstract double calculateInterest();
    public abstract double getWithdrawalLimit();
    public abstract void processMonthEnd();
    public abstract String getAccountCategory();

    public void displayInfo() {
        System.out.println("Account: " + accountNumber + " | " + accountType + " | Rs." + balance);
    }
}