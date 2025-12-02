package com.npci.level6;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 6: Composition & Aggregation - Bank Account
 *
 * BankAccount demonstrates:
 * - AGGREGATION with Customer (referenced, not owned)
 * - AGGREGATION with Branch (referenced, not owned)
 * - COMPOSITION with Transaction list (transactions are part of account)
 */
public class BankAccount {

    private String accountNumber;
    private String accountType;
    private double balance;
    private LocalDate openingDate;
    private boolean isActive;
    private double minimumBalance;

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION - References to independent entities
    // Account doesn't own these - they exist independently
    // ═══════════════════════════════════════════════════════════════
    private String customerId;     // Reference to customer (not the Customer object)
    private String branchCode;     // Reference to branch (not the Branch object)

    // We could also store actual references (for demonstration)
    private Branch homeBranch;     // AGGREGATION - branch exists independently

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION - Transactions are PART OF Account
    // Transactions are created within account, die with account
    // ═══════════════════════════════════════════════════════════════
    private List<Transaction> transactions;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public BankAccount(String accountNumber, String accountType, double initialBalance,
                       String customerId, Branch branch) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = initialBalance;
        this.openingDate = LocalDate.now();
        this.isActive = true;
        this.minimumBalance = 1000;

        // AGGREGATION - Storing references
        this.customerId = customerId;
        this.homeBranch = branch;  // Branch exists independently
        this.branchCode = branch.getBranchCode();

        // COMPOSITION - Creating transaction list (owned by account)
        this.transactions = new ArrayList<>();

        // Record opening transaction (COMPOSITION - transaction created here)
        recordTransaction("OPENING", initialBalance, "Account Opening Deposit");

        // Register with branch (AGGREGATION - account added to branch's list)
        branch.registerAccount(accountNumber);

        System.out.println("[BankAccount] Created: " + accountNumber + " (" + accountType + ")");
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public LocalDate getOpeningDate() { return openingDate; }
    public boolean isActive() { return isActive; }
    public String getCustomerId() { return customerId; }
    public String getBranchCode() { return branchCode; }
    public Branch getHomeBranch() { return homeBranch; }
    public int getTransactionCount() { return transactions.size(); }
    public List<Transaction> getTransactions() { return new ArrayList<>(transactions); }

    // ═══════════════════════════════════════════════════════════════
    // BANKING OPERATIONS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Deposit money
     */
    public void deposit(double amount, String description) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid amount!");
            return;
        }

        balance += amount;

        // COMPOSITION - Create transaction internally
        recordTransaction("CREDIT", amount, description);

        System.out.println("[Deposit] Rs." + amount + " | Balance: Rs." + balance);
    }

    /**
     * Withdraw money
     */
    public void withdraw(double amount, String description) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid amount!");
            return;
        }
        if (balance - amount < minimumBalance) {
            System.out.println("Error: Insufficient balance! Min: Rs." + minimumBalance);
            return;
        }

        balance -= amount;

        // COMPOSITION - Create transaction internally
        recordTransaction("DEBIT", amount, description);

        System.out.println("[Withdraw] Rs." + amount + " | Balance: Rs." + balance);
    }

    /**
     * Transfer to another account
     */
    public void transfer(BankAccount toAccount, double amount, String description) {
        if (!isActive) {
            System.out.println("Error: Source account is inactive!");
            return;
        }
        if (!toAccount.isActive()) {
            System.out.println("Error: Destination account is inactive!");
            return;
        }
        if (balance - amount < minimumBalance) {
            System.out.println("Error: Insufficient balance!");
            return;
        }

        // Debit from this account
        balance -= amount;
        recordTransaction("TRANSFER_OUT", amount, "Transfer to " + toAccount.getAccountNumber() + ": " + description);

        // Credit to destination account
        toAccount.balance += amount;
        toAccount.recordTransaction("TRANSFER_IN", amount, "Transfer from " + accountNumber + ": " + description);

        System.out.println("[Transfer] Rs." + amount + " from " + accountNumber + " to " + toAccount.getAccountNumber());
    }

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION - Transaction Management
    // ═══════════════════════════════════════════════════════════════

    /**
     * Record transaction (COMPOSITION - creates Transaction internally)
     */
    private void recordTransaction(String type, double amount, String description) {
        Transaction txn = new Transaction(accountNumber, type, amount, balance, description);
        transactions.add(txn);
    }

    /**
     * Get last N transactions
     */
    public List<Transaction> getLastTransactions(int n) {
        int size = transactions.size();
        int fromIndex = Math.max(0, size - n);
        return new ArrayList<>(transactions.subList(fromIndex, size));
    }

    /**
     * Get transactions by type
     */
    public List<Transaction> getTransactionsByType(String type) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction txn : transactions) {
            if (txn.getType().equals(type)) {
                result.add(txn);
            }
        }
        return result;
    }

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION - Branch Management
    // ═══════════════════════════════════════════════════════════════

    /**
     * Transfer account to new branch (AGGREGATION)
     */
    public void transferToBranch(Branch newBranch) {
        if (homeBranch != null) {
            homeBranch.removeAccount(accountNumber);
        }

        this.homeBranch = newBranch;
        this.branchCode = newBranch.getBranchCode();
        newBranch.registerAccount(accountNumber);

        recordTransaction("BRANCH_TRANSFER", 0, "Branch transferred to " + newBranch.getBranchName());

        System.out.println("[Account] Transferred to branch: " + newBranch.getBranchName());
    }

    // ═══════════════════════════════════════════════════════════════
    // DISPLAY METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Display account info
     */
    public void displayAccountInfo() {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║            ACCOUNT INFORMATION                ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("  Account Number : " + accountNumber);
        System.out.println("  Account Type   : " + accountType);
        System.out.println("  Balance        : Rs." + balance);
        System.out.println("  Opening Date   : " + openingDate);
        System.out.println("  Status         : " + (isActive ? "Active" : "Inactive"));
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("  LINKED ENTITIES (Aggregated):");
        System.out.println("  Customer ID    : " + customerId);
        System.out.println("  Branch Code    : " + branchCode);
        if (homeBranch != null) {
            System.out.println("  Branch Name    : " + homeBranch.getBranchName());
        }
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("  TRANSACTIONS (Composed): " + transactions.size());
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    /**
     * Display mini statement
     */
    public void displayMiniStatement(int lastN) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    MINI STATEMENT                                 ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("  Account: " + accountNumber + " | " + accountType);
        System.out.println("  Current Balance: Rs." + balance);
        System.out.println("├───────────────────────────────────────────────────────────────────┤");
        System.out.println("  LAST " + lastN + " TRANSACTIONS:");
        System.out.println("  ─────────────────────────────────────────────────────────────────");

        List<Transaction> recent = getLastTransactions(lastN);
        for (Transaction txn : recent) {
            txn.displayTransaction();
        }

        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return accountNumber + " | " + accountType + " | Rs." + balance;
    }
}