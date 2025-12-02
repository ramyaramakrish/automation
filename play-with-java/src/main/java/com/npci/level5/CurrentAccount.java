package com.npci.level5;

/**
 * Level 5: Abstraction - Concrete Current Account
 */
public class CurrentAccount extends BankAccount {

    private double overdraftLimit;
    private double overdraftUsed;

    public CurrentAccount(String accountNumber, String holderName, double initialBalance, double overdraftLimit) {
        super(accountNumber, holderName, initialBalance);
        this.overdraftLimit = overdraftLimit;
        this.overdraftUsed = 0;
        this.accountType = "Current";
    }

    @Override
    public void withdraw(double amount) {
        double available = balance + (overdraftLimit - overdraftUsed);
        if (amount > available) {
            System.out.println("Error: Exceeds available balance + overdraft!");
            return;
        }

        if (amount <= balance) {
            balance -= amount;
        } else {
            double fromOD = amount - balance;
            overdraftUsed += fromOD;
            balance = 0;
            System.out.println("Using overdraft: Rs." + fromOD);
        }
        System.out.println("Withdrawn: Rs." + amount + " | Balance: Rs." + balance);
    }

    @Override
    public double calculateInterest() {
        // Interest on overdraft (charged)
        return overdraftUsed * 12 / 100 / 12;
    }

    @Override
    public double getWithdrawalLimit() {
        return balance + (overdraftLimit - overdraftUsed);
    }

    @Override
    public void processMonthEnd() {
        if (overdraftUsed > 0) {
            double interest = calculateInterest();
            overdraftUsed += interest;
            System.out.println("[Current] OD Interest Rs." + String.format("%.2f", interest) + " charged");
        }
    }

    @Override
    public String getAccountCategory() {
        return "Business Banking";
    }
}