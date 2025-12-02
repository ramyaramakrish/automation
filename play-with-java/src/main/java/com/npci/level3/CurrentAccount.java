package com.npci.level3;

/**
 * Level 3: Inheritance - Child Class (Current Account)
 *
 * CurrentAccount IS-A BankAccount
 * - Inherits all features from BankAccount
 * - Adds overdraft facility
 * - No minimum balance requirement
 * - No interest (typically)
 */
public class CurrentAccount extends BankAccount {

    // ═══════════════════════════════════════════════════════════════
    // ADDITIONAL FIELDS - Specific to Current Account
    // ═══════════════════════════════════════════════════════════════

    private double overdraftLimit;
    private double overdraftUsed;
    private double overdraftInterestRate;
    private String businessName;
    private String gstNumber;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public CurrentAccount(String accountNumber, String holderName, double initialBalance, double overdraftLimit) {
        // Call parent constructor
        super(accountNumber, holderName, initialBalance);

        // Initialize current account specific fields
        this.overdraftLimit = overdraftLimit;
        this.overdraftUsed = 0;
        this.overdraftInterestRate = 12.0;  // 12% per annum on overdraft
        this.accountType = "Current";
        this.businessName = "";
        this.gstNumber = "";

        System.out.println("[CurrentAccount] Current account created with OD limit: Rs." + overdraftLimit);
    }

    public CurrentAccount(String accountNumber, String holderName, double initialBalance,
                          double overdraftLimit, String businessName) {
        this(accountNumber, holderName, initialBalance, overdraftLimit);
        this.businessName = businessName;
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getOverdraftUsed() {
        return overdraftUsed;
    }

    public double getAvailableOverdraft() {
        return overdraftLimit - overdraftUsed;
    }

    public double getOverdraftInterestRate() {
        return overdraftInterestRate;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    // ═══════════════════════════════════════════════════════════════
    // SETTERS
    // ═══════════════════════════════════════════════════════════════

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setGstNumber(String gstNumber) {
        if (gstNumber != null && gstNumber.length() == 15) {
            this.gstNumber = gstNumber;
            System.out.println("GST Number updated.");
        } else {
            System.out.println("Error: Invalid GST Number!");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Override withdraw - allows overdraft
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

        double totalAvailable = balance + getAvailableOverdraft();

        if (amount > totalAvailable) {
            System.out.println("Error: Amount exceeds available balance and overdraft limit!");
            System.out.println("Available Balance: Rs." + balance);
            System.out.println("Available Overdraft: Rs." + getAvailableOverdraft());
            System.out.println("Total Available: Rs." + totalAvailable);
            return;
        }

        // Withdraw from balance first
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: Rs." + amount);
        } else {
            // Use overdraft
            double fromBalance = balance;
            double fromOverdraft = amount - balance;

            balance = 0;
            overdraftUsed += fromOverdraft;

            System.out.println("Withdrawn: Rs." + amount);
            System.out.println("  From Balance: Rs." + fromBalance);
            System.out.println("  From Overdraft: Rs." + fromOverdraft);
            System.out.println("⚠ Warning: Using overdraft facility!");
        }

        System.out.println("New Balance: Rs." + balance);
        if (overdraftUsed > 0) {
            System.out.println("Overdraft Used: Rs." + overdraftUsed);
        }
    }

    /**
     * Override deposit - repay overdraft first
     */
    @Override
    public void deposit(double amount) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid deposit amount!");
            return;
        }

        // If overdraft is used, repay it first
        if (overdraftUsed > 0) {
            if (amount >= overdraftUsed) {
                double remaining = amount - overdraftUsed;
                System.out.println("Overdraft repaid: Rs." + overdraftUsed);
                overdraftUsed = 0;
                balance += remaining;
                System.out.println("Added to balance: Rs." + remaining);
            } else {
                overdraftUsed -= amount;
                System.out.println("Partial overdraft repaid: Rs." + amount);
                System.out.println("Remaining overdraft: Rs." + overdraftUsed);
            }
        } else {
            balance += amount;
            System.out.println("Deposited: Rs." + amount);
        }

        System.out.println("New Balance: Rs." + balance);
    }

    /**
     * Override calculateInterest - overdraft interest (charged, not earned)
     */
    @Override
    public double calculateInterest() {
        // Current accounts charge interest on overdraft, don't pay interest on balance
        return overdraftUsed * overdraftInterestRate / 100 / 12;  // Monthly interest on OD
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
        if (!businessName.isEmpty()) {
            System.out.println("  Business Name     : " + businessName);
        }
        if (!gstNumber.isEmpty()) {
            System.out.println("  GST Number        : " + gstNumber);
        }
        System.out.println("  Overdraft Limit   : Rs." + overdraftLimit);
        System.out.println("  Overdraft Used    : Rs." + overdraftUsed);
        System.out.println("  Available OD      : Rs." + getAvailableOverdraft());
        if (overdraftUsed > 0) {
            System.out.println("  OD Interest Rate  : " + overdraftInterestRate + "%");
            System.out.println("  Monthly OD Interest: Rs." + String.format("%.2f", calculateInterest()));
        }
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ═══════════════════════════════════════════════════════════════
    // NEW METHODS - Specific to Current Account
    // ═══════════════════════════════════════════════════════════════

    /**
     * Increase overdraft limit
     */
    public void increaseOverdraftLimit(double additionalLimit) {
        this.overdraftLimit += additionalLimit;
        System.out.println("Overdraft limit increased by Rs." + additionalLimit);
        System.out.println("New Overdraft Limit: Rs." + this.overdraftLimit);
    }

    /**
     * Charge monthly overdraft interest
     */
    public void chargeOverdraftInterest() {
        if (overdraftUsed > 0) {
            double interest = calculateInterest();
            overdraftUsed += interest;
            System.out.println("Overdraft interest charged: Rs." + String.format("%.2f", interest));
            System.out.println("Total Overdraft: Rs." + overdraftUsed);
        } else {
            System.out.println("No overdraft interest applicable.");
        }
    }

    /**
     * Get effective balance (balance - overdraft used)
     */
    public double getEffectiveBalance() {
        return balance - overdraftUsed;
    }
}