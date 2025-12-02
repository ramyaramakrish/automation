package com.npci.level4;

/**
 * Level 4: Polymorphism - Child Class (Current Account)
 *
 * Demonstrates Method Overriding:
 * - withdraw() - allows overdraft
 * - calculateInterest() - returns overdraft interest (charged)
 * - processMonthEnd() - charges overdraft interest
 * - getAccountCategory() - returns "Business Banking"
 */
public class CurrentAccount extends BankAccount {

    private double overdraftLimit;
    private double overdraftUsed;
    private double overdraftInterestRate;

    public CurrentAccount(String accountNumber, String holderName, double initialBalance, double overdraftLimit) {
        super(accountNumber, holderName, initialBalance);
        this.overdraftLimit = overdraftLimit;
        this.overdraftUsed = 0;
        this.overdraftInterestRate = 12.0;
        this.accountType = "Current";
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public double getOverdraftLimit() { return overdraftLimit; }
    public double getOverdraftUsed() { return overdraftUsed; }
    public double getAvailableOverdraft() { return overdraftLimit - overdraftUsed; }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Override withdraw - allow overdraft
     */
    @Override
    public void withdraw(double amount) {
        double totalAvailable = balance + getAvailableOverdraft();

        if (amount > totalAvailable) {
            System.out.println("[Current] Error: Exceeds available balance and overdraft!");
            System.out.println("Total Available: Rs." + totalAvailable);
            return;
        }

        if (amount <= balance) {
            balance -= amount;
            System.out.println("[Current] Withdrawn: Rs." + amount);
        } else {
            double fromBalance = balance;
            double fromOverdraft = amount - balance;
            balance = 0;
            overdraftUsed += fromOverdraft;
            System.out.println("[Current] Withdrawn: Rs." + amount);
            System.out.println("  From Balance: Rs." + fromBalance);
            System.out.println("  From Overdraft: Rs." + fromOverdraft);
            System.out.println("  ⚠ Using overdraft facility!");
        }
        System.out.println("New Balance: Rs." + balance);
    }

    /**
     * Override deposit - repay overdraft first
     */
    @Override
    public void deposit(double amount) {
        if (overdraftUsed > 0) {
            if (amount >= overdraftUsed) {
                double remaining = amount - overdraftUsed;
                System.out.println("[Current] Overdraft repaid: Rs." + overdraftUsed);
                overdraftUsed = 0;
                balance += remaining;
                if (remaining > 0) {
                    System.out.println("Added to balance: Rs." + remaining);
                }
            } else {
                overdraftUsed -= amount;
                System.out.println("[Current] Partial OD repaid: Rs." + amount);
                System.out.println("Remaining OD: Rs." + overdraftUsed);
            }
        } else {
            super.deposit(amount);
        }
        System.out.println("New Balance: Rs." + balance);
    }

    /**
     * Override calculateInterest - overdraft interest (charged, not earned)
     */
    @Override
    public double calculateInterest() {
        // Returns monthly interest on overdraft (to be charged)
        return overdraftUsed * overdraftInterestRate / 100 / 12;
    }

    /**
     * Override getWithdrawalLimit
     */
    @Override
    public double getWithdrawalLimit() {
        return balance + getAvailableOverdraft();
    }

    /**
     * Override processMonthEnd - charge overdraft interest
     */
    @Override
    public void processMonthEnd() {
        System.out.println("[Current] Month end processing...");
        if (overdraftUsed > 0) {
            double interest = calculateInterest();
            overdraftUsed += interest;
            System.out.println("Overdraft interest charged: Rs." + String.format("%.2f", interest));
            System.out.println("Total Overdraft: Rs." + String.format("%.2f", overdraftUsed));
        } else {
            System.out.println("No overdraft used. No interest charged.");
        }
    }

    /**
     * Override getAccountCategory
     */
    @Override
    public String getAccountCategory() {
        return "Business Banking";
    }

    /**
     * Override displayInfo
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│       CURRENT ACCOUNT DETAILS           │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Overdraft Limit : Rs." + overdraftLimit);
        System.out.println("  Overdraft Used  : Rs." + overdraftUsed);
        System.out.println("  Available OD    : Rs." + getAvailableOverdraft());
        System.out.println("  Total Available : Rs." + getWithdrawalLimit());
        if (overdraftUsed > 0) {
            System.out.println("  OD Interest/Month: Rs." + String.format("%.2f", calculateInterest()));
        }
        System.out.println("└─────────────────────────────────────────┘");
    }
}