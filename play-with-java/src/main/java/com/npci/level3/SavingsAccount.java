package com.npci.level3;

/**
 * Level 3: Inheritance - Child Class (Savings Account)
 *
 * SavingsAccount IS-A BankAccount
 * - Inherits all features from BankAccount
 * - Adds interest rate feature
 * - Overrides withdraw() to maintain minimum balance
 * - Overrides calculateInterest() for savings interest
 */
public class SavingsAccount extends BankAccount {

    // ═══════════════════════════════════════════════════════════════
    // ADDITIONAL FIELDS - Specific to Savings Account
    // ═══════════════════════════════════════════════════════════════

    private double interestRate;
    private double minimumBalance;
    private int freeWithdrawalsPerMonth;
    private int withdrawalsThisMonth;
    private double withdrawalFee;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR - Calls parent constructor using super()
    // ═══════════════════════════════════════════════════════════════

    public SavingsAccount(String accountNumber, String holderName, double initialBalance, double interestRate) {
        // Call parent constructor FIRST
        super(accountNumber, holderName, initialBalance);

        // Initialize child-specific fields
        this.interestRate = interestRate;
        this.minimumBalance = 1000.0;
        this.freeWithdrawalsPerMonth = 5;
        this.withdrawalsThisMonth = 0;
        this.withdrawalFee = 10.0;

        // Set account type (inherited field)
        this.accountType = "Savings";

        System.out.println("[SavingsAccount] Savings account created with " + interestRate + "% interest");
    }

    public SavingsAccount(String accountNumber, String holderName, double initialBalance,
                          double interestRate, double minimumBalance) {
        this(accountNumber, holderName, initialBalance, interestRate);
        this.minimumBalance = minimumBalance;
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS - Specific to Savings Account
    // ═══════════════════════════════════════════════════════════════

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public int getFreeWithdrawalsRemaining() {
        return Math.max(0, freeWithdrawalsPerMonth - withdrawalsThisMonth);
    }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS - Different behavior from parent
    // ═══════════════════════════════════════════════════════════════

    /**
     * Override withdraw - maintain minimum balance
     */
    @Override
    public void withdraw(double amount) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid withdrawal amount!");
            return;
        }

        // Check minimum balance
        double effectiveAmount = amount;

        // Check if withdrawal fee applies
        if (withdrawalsThisMonth >= freeWithdrawalsPerMonth) {
            effectiveAmount += withdrawalFee;
            System.out.println("Note: Withdrawal fee of Rs." + withdrawalFee + " applies");
        }

        if (balance - effectiveAmount < minimumBalance) {
            System.out.println("Error: Cannot withdraw! Minimum balance of Rs." + minimumBalance + " required.");
            System.out.println("Available for withdrawal: Rs." + (balance - minimumBalance));
            return;
        }

        balance -= effectiveAmount;
        withdrawalsThisMonth++;

        System.out.println("Withdrawn: Rs." + amount);
        if (effectiveAmount > amount) {
            System.out.println("Fee Charged: Rs." + withdrawalFee);
        }
        System.out.println("New Balance: Rs." + balance);
        System.out.println("Free withdrawals remaining: " + getFreeWithdrawalsRemaining());
    }

    /**
     * Override calculateInterest - savings interest calculation
     */
    @Override
    public double calculateInterest() {
        return balance * interestRate / 100;
    }

    /**
     * Override displayInfo - show additional savings account details
     */
    @Override
    public void displayInfo() {
        // Call parent's displayInfo first
        super.displayInfo();

        // Add savings-specific info
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│       SAVINGS ACCOUNT DETAILS           │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Interest Rate     : " + interestRate + "%");
        System.out.println("  Minimum Balance   : Rs." + minimumBalance);
        System.out.println("  Free Withdrawals  : " + getFreeWithdrawalsRemaining() + " remaining");
        System.out.println("  Monthly Interest  : Rs." + String.format("%.2f", calculateInterest()));
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ═══════════════════════════════════════════════════════════════
    // NEW METHODS - Specific to Savings Account
    // ═══════════════════════════════════════════════════════════════

    /**
     * Add monthly interest to balance
     */
    public void addMonthlyInterest() {
        double interest = calculateInterest() / 12;  // Monthly interest
        balance += interest;
        System.out.println("Monthly interest added: Rs." + String.format("%.2f", interest));
        System.out.println("New Balance: Rs." + String.format("%.2f", balance));
    }

    /**
     * Add quarterly interest to balance
     */
    public void addQuarterlyInterest() {
        double interest = calculateInterest() / 4;  // Quarterly interest
        balance += interest;
        System.out.println("Quarterly interest added: Rs." + String.format("%.2f", interest));
        System.out.println("New Balance: Rs." + String.format("%.2f", balance));
    }

    /**
     * Reset monthly withdrawal count (called at month start)
     */
    public void resetMonthlyWithdrawals() {
        this.withdrawalsThisMonth = 0;
        System.out.println("Monthly withdrawal count reset.");
    }

    /**
     * Set new interest rate
     */
    public void setInterestRate(double newRate) {
        System.out.println("Interest rate changed from " + this.interestRate + "% to " + newRate + "%");
        this.interestRate = newRate;
    }
}