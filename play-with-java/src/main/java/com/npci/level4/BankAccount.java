package com.npci.level4;

/**
 * Level 4: Polymorphism - Parent Class
 *
 * Key Concepts:
 * - Polymorphism = "Many Forms" - same method behaves differently
 * - Compile-time (Static) Polymorphism = Method Overloading (same class, different params)
 * - Runtime (Dynamic) Polymorphism = Method Overriding (parent-child, same signature)
 * - Parent reference can hold Child object: BankAccount acc = new SavingsAccount()
 * - Method called depends on actual object type, not reference type
 * - Why? = Write flexible, extensible code that works with any account type
 */
public class BankAccount {

    protected String accountNumber;
    protected String holderName;
    protected double balance;
    protected String accountType;
    protected boolean isActive;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public BankAccount(String accountNumber, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
        this.accountType = "General";
        this.isActive = true;
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    public boolean isActive() { return isActive; }

    // ═══════════════════════════════════════════════════════════════
    // METHOD OVERLOADING - Compile-time Polymorphism
    // Same method name, different parameters
    // ═══════════════════════════════════════════════════════════════

    /**
     * Deposit - Version 1: Just amount
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("[" + accountType + "] Deposited: Rs." + amount);
            System.out.println("New Balance: Rs." + balance);
        }
    }

    /**
     * Deposit - Version 2: Amount with description
     */
    public void deposit(double amount, String description) {
        if (amount > 0) {
            balance += amount;
            System.out.println("[" + accountType + "] Deposited: Rs." + amount);
            System.out.println("Description: " + description);
            System.out.println("New Balance: Rs." + balance);
        }
    }

    /**
     * Deposit - Version 3: Amount with cheque details
     */
    public void deposit(double amount, String chequeNumber, String bankName) {
        if (amount > 0) {
            balance += amount;
            System.out.println("[" + accountType + "] Cheque Deposit: Rs." + amount);
            System.out.println("Cheque No: " + chequeNumber + " | Bank: " + bankName);
            System.out.println("New Balance: Rs." + balance);
        }
    }

    /**
     * Withdraw - Version 1: Just amount
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("[" + accountType + "] Withdrawn: Rs." + amount);
            System.out.println("New Balance: Rs." + balance);
        } else {
            System.out.println("Error: Invalid withdrawal!");
        }
    }

    /**
     * Withdraw - Version 2: Amount with mode
     */
    public void withdraw(double amount, String mode) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("[" + accountType + "] Withdrawn: Rs." + amount);
            System.out.println("Mode: " + mode);
            System.out.println("New Balance: Rs." + balance);
        } else {
            System.out.println("Error: Invalid withdrawal!");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // METHODS TO BE OVERRIDDEN - Runtime Polymorphism
    // ═══════════════════════════════════════════════════════════════

    /**
     * Calculate interest - default is 0
     * Child classes will override with their own rates
     */
    public double calculateInterest() {
        return 0.0;
    }

    /**
     * Get withdrawal limit - default is balance
     * Child classes may have different limits
     */
    public double getWithdrawalLimit() {
        return balance;
    }

    /**
     * Process month end - default does nothing
     * Child classes will implement specific logic
     */
    public void processMonthEnd() {
        System.out.println("[" + accountType + "] Month end processing...");
        System.out.println("No specific processing for general account.");
    }

    /**
     * Get account category
     */
    public String getAccountCategory() {
        return "General Banking";
    }

    /**
     * Display account info
     */
    public void displayInfo() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│          ACCOUNT INFORMATION            │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Account No   : " + accountNumber);
        System.out.println("  Holder Name  : " + holderName);
        System.out.println("  Account Type : " + accountType);
        System.out.println("  Category     : " + getAccountCategory());
        System.out.println("  Balance      : Rs." + balance);
        System.out.println("  Status       : " + (isActive ? "Active" : "Inactive"));
        System.out.println("└─────────────────────────────────────────┘");
    }

    /**
     * Get summary string
     */
    public String getSummary() {
        return String.format("%s | %s | %s | Rs.%.2f",
                accountNumber, holderName, accountType, balance);
    }
}