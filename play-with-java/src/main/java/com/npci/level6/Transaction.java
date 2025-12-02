package com.npci.level6;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Level 6: Composition - Transaction
 *
 * Transaction demonstrates:
 * - COMPOSITION with TransactionDetails (details are PART OF transaction)
 * - Transaction itself is COMPOSED within BankAccount
 */
public class Transaction {

    private String transactionId;
    private String accountNumber;
    private String type;              // CREDIT, DEBIT, TRANSFER_IN, TRANSFER_OUT
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;
    private String status;

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION - TransactionDetails is PART OF Transaction
    // Created internally, cannot exist without Transaction
    // ═══════════════════════════════════════════════════════════════
    private TransactionDetails details;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public Transaction(String accountNumber, String type, double amount,
                       double balanceAfter, String description) {
        this.transactionId = generateTransactionId(type);
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
        this.status = "SUCCESS";

        // COMPOSITION - Creating TransactionDetails internally
        this.details = new TransactionDetails("SYSTEM", description);
    }

    public Transaction(String accountNumber, String type, double amount,
                       double balanceAfter, String channel, String description) {
        this.transactionId = generateTransactionId(type);
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
        this.status = "SUCCESS";

        // COMPOSITION - Creating TransactionDetails with channel
        this.details = new TransactionDetails(channel, description);
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getStatus() { return status; }

    // Return composed object
    public TransactionDetails getDetails() { return details; }

    // ═══════════════════════════════════════════════════════════════
    // METHODS
    // ═══════════════════════════════════════════════════════════════

    private String generateTransactionId(String type) {
        String prefix = "TXN";
        if (type.startsWith("TRANSFER")) {
            prefix = "TRF";
        } else if (type.equals("CREDIT")) {
            prefix = "CRD";
        } else if (type.equals("DEBIT")) {
            prefix = "DBT";
        }
        return prefix + System.currentTimeMillis();
    }

    public boolean isCredit() {
        return type.equals("CREDIT") || type.equals("TRANSFER_IN") || type.equals("OPENING");
    }

    public boolean isDebit() {
        return type.equals("DEBIT") || type.equals("TRANSFER_OUT");
    }

    /**
     * Display transaction
     */
    public void displayTransaction() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String sign = isCredit() ? "+" : "-";

        System.out.printf("  %s | %s | %s Rs.%-10.2f | Bal: Rs.%-10.2f | %s%n",
                transactionId,
                timestamp.format(fmt),
                sign,
                amount,
                balanceAfter,
                details.getRemarks());
    }

    /**
     * Display full details
     */
    public void displayFullDetails() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│       TRANSACTION DETAILS               │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Transaction ID : " + transactionId);
        System.out.println("  Account        : " + accountNumber);
        System.out.println("  Type           : " + type);
        System.out.println("  Amount         : Rs." + amount);
        System.out.println("  Balance After  : Rs." + balanceAfter);
        System.out.println("  Timestamp      : " + timestamp);
        System.out.println("  Status         : " + status);
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  DETAILS (Composed):");
        details.displayDetails();
        System.out.println("└─────────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return transactionId + " | " + type + " | Rs." + amount + " | " + status;
    }
}