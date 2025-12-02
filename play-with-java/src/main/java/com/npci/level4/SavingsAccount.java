package com.npci.level4;

/**
 * Level 4: Polymorphism - Child Class (Savings Account)
 *
 * Demonstrates Method Overriding:
 * - withdraw() - enforces minimum balance
 * - calculateInterest() - returns savings interest
 * - processMonthEnd() - adds interest
 * - getAccountCategory() - returns "Retail Banking"
 */
public class SavingsAccount extends BankAccount {

    private double interestRate;
    private double minimumBalance;

    public SavingsAccount(String accountNumber, String holderName, double initialBalance, double interestRate) {
        super(accountNumber, holderName, initialBalance);
        this.interestRate = interestRate;
        this.minimumBalance = 1000.0;
        this.accountType = "Savings";
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public double getInterestRate() { return interestRate; }
    public double getMinimumBalance() { return minimumBalance; }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS - Runtime Polymorphism
    // ═══════════════════════════════════════════════════════════════

    /**
     * Override withdraw - enforce minimum balance
     */
    @Override
    public void withdraw(double amount) {
        if (balance - amount < minimumBalance) {
            System.out.println("[Savings] Error: Cannot withdraw!");
            System.out.println("Minimum balance of Rs." + minimumBalance + " must be maintained.");
            System.out.println("Available: Rs." + (balance - minimumBalance));
            return;
        }
        super.withdraw(amount);
    }

    /**
     * Override calculateInterest - savings interest
     */
    @Override
    public double calculateInterest() {
        return balance * interestRate / 100;
    }

    /**
     * Override getWithdrawalLimit
     */
    @Override
    public double getWithdrawalLimit() {
        return balance - minimumBalance;
    }

    /**
     * Override processMonthEnd - add interest
     */
    @Override
    public void processMonthEnd() {
        System.out.println("[Savings] Month end processing...");
        double monthlyInterest = calculateInterest() / 12;
        balance += monthlyInterest;
        System.out.println("Interest added: Rs." + String.format("%.2f", monthlyInterest));
        System.out.println("New Balance: Rs." + String.format("%.2f", balance));
    }

    /**
     * Override getAccountCategory
     */
    @Override
    public String getAccountCategory() {
        return "Retail Banking";
    }

    /**
     * Override displayInfo
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│       SAVINGS ACCOUNT DETAILS           │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Interest Rate   : " + interestRate + "% p.a.");
        System.out.println("  Min Balance     : Rs." + minimumBalance);
        System.out.println("  Available       : Rs." + getWithdrawalLimit());
        System.out.println("  Annual Interest : Rs." + String.format("%.2f", calculateInterest()));
        System.out.println("└─────────────────────────────────────────┘");
    }
}