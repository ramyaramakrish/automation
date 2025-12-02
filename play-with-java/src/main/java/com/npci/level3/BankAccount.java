package com.npci.level3;

/**
 * Level 3: Inheritance - Parent/Base Class
 *
 * Key Concepts:
 * - Inheritance = Creating new class from existing class (child from parent)
 * - extends = Keyword to inherit from parent class
 * - Parent/Super Class = Base class with common features
 * - Child/Sub Class = Derived class with specialized features
 * - super = Refers to parent class (constructor/methods)
 * - protected = Accessible in child classes
 * - Code Reusability = Write once in parent, reuse in all children
 * - IS-A Relationship = SavingsAccount IS-A BankAccount
 *
 * This is the PARENT CLASS for all account types.
 */
public class BankAccount {

    // ═══════════════════════════════════════════════════════════════
    // PROTECTED FIELDS - Accessible by child classes
    // ═══════════════════════════════════════════════════════════════

    protected String accountNumber;
    protected String holderName;
    protected double balance;
    protected String accountType;
    protected boolean isActive;
    protected String branch;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public BankAccount(String accountNumber, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
        this.accountType = "General";
        this.isActive = true;
        this.branch = "Head Office";
        System.out.println("[BankAccount] Account created: " + accountNumber);
    }

    public BankAccount(String accountNumber, String holderName, double initialBalance, String branch) {
        this(accountNumber, holderName, initialBalance);
        this.branch = branch;
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getBranch() {
        return branch;
    }

    // ═══════════════════════════════════════════════════════════════
    // COMMON METHODS - Inherited by all child classes
    // ═══════════════════════════════════════════════════════════════

    /**
     * Deposit money - common for all account types
     */
    public void deposit(double amount) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid deposit amount!");
            return;
        }
        balance += amount;
        System.out.println("Deposited: Rs." + amount);
        System.out.println("New Balance: Rs." + balance);
    }

    /**
     * Withdraw money - can be overridden by child classes
     */
    public void withdraw(double amount) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid withdrawal amount!");
            return;
        }
        if (amount > balance) {
            System.out.println("Error: Insufficient balance!");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawn: Rs." + amount);
        System.out.println("New Balance: Rs." + balance);
    }

    /**
     * Check balance
     */
    public void checkBalance() {
        System.out.println("Account: " + accountNumber + " | Balance: Rs." + balance);
    }

    /**
     * Display account info - can be overridden by child classes
     */
    public void displayInfo() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│          ACCOUNT INFORMATION            │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Account No   : " + accountNumber);
        System.out.println("  Holder Name  : " + holderName);
        System.out.println("  Account Type : " + accountType);
        System.out.println("  Balance      : Rs." + balance);
        System.out.println("  Branch       : " + branch);
        System.out.println("  Status       : " + (isActive ? "Active" : "Inactive"));
        System.out.println("└─────────────────────────────────────────┘");
    }

    /**
     * Deactivate account
     */
    public void deactivate() {
        this.isActive = false;
        System.out.println("Account " + accountNumber + " deactivated.");
    }

    /**
     * Activate account
     */
    public void activate() {
        this.isActive = true;
        System.out.println("Account " + accountNumber + " activated.");
    }

    /**
     * Calculate interest - default implementation (0%)
     * Child classes will override this
     */
    public double calculateInterest() {
        return 0.0;
    }

    /**
     * Get account summary as string
     */
    public String getSummary() {
        return accountNumber + " | " + holderName + " | " + accountType + " | Rs." + balance;
    }
}