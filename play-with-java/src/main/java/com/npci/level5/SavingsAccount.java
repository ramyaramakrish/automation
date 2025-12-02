package com.npci.level5;

/**
 * Level 5: Abstraction - Concrete Savings Account
 */
public class SavingsAccount extends BankAccount {

    private double interestRate;
    private double minimumBalance;

    public SavingsAccount(String accountNumber, String holderName, double initialBalance, double interestRate) {
        super(accountNumber, holderName, initialBalance);
        this.interestRate = interestRate;
        this.minimumBalance = 1000;
        this.accountType = "Savings";
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount < minimumBalance) {
            System.out.println("Error: Minimum balance Rs." + minimumBalance + " required!");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawn: Rs." + amount + " | Balance: Rs." + balance);
    }

    @Override
    public double calculateInterest() {
        return balance * interestRate / 100;
    }

    @Override
    public double getWithdrawalLimit() {
        return balance - minimumBalance;
    }

    @Override
    public void processMonthEnd() {
        double interest = calculateInterest() / 12;
        balance += interest;
        System.out.println("[Savings] Interest Rs." + String.format("%.2f", interest) + " added");
    }

    @Override
    public String getAccountCategory() {
        return "Retail Banking";
    }
}