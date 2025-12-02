package com.npci.level5;

import java.util.ArrayList;
import java.util.List;

/**
 * Level 5: Abstraction - RTGS Payment Implementation
 *
 * RTGS (Real Time Gross Settlement):
 * - Real-time settlement for high-value transactions
 * - Minimum Rs.2 Lakhs
 * - No upper limit
 */
public class RTGSPayment extends PaymentService implements NPCIPaymentStandard, Auditable, Notifiable {

    private String currentTransactionId;
    private String transactionStatus;
    private List<String> auditLog;

    public RTGSPayment() {
        super("RTGS Payment", "RTGS");
        this.minAmount = 200000;  // Minimum 2 Lakhs
        this.maxAmount = Double.MAX_VALUE;
        this.auditLog = new ArrayList<>();
        recordAuditEvent("INIT", "RTGS Payment Service initialized");
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING PaymentService ABSTRACT METHODS
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String processPayment(String fromAccount, String toAccount, double amount) {
        String txnId = generateTransactionId();
        this.currentTransactionId = txnId;

        double charges = calculateCharges(amount);

        System.out.println("  [RTGS] Processing high-value real-time transfer...");
        System.out.println("  From Account: " + maskAccountNumber(fromAccount));
        System.out.println("  To Account: " + maskAccountNumber(toAccount));
        System.out.println("  Amount: Rs." + String.format("%,.2f", amount));
        System.out.println("  Charges: Rs." + charges);
        System.out.println("  Total Debit: Rs." + String.format("%,.2f", (amount + charges)));
        System.out.println("  Transaction ID: " + txnId);
        System.out.println("  Status: SUCCESS ✓ (Real-time Settlement)");

        this.transactionStatus = "SUCCESS";
        recordAuditEvent("PAYMENT", "RTGS completed: Rs." + String.format("%,.2f", amount));

        return txnId;
    }

    @Override
    public double calculateCharges(double amount) {
        if (amount <= 500000) return 25.00;
        return 50.00;
    }

    @Override
    public String getSettlementTime() {
        return "Instant (Real-time Gross Settlement)";
    }

    @Override
    public boolean validatePaymentDetails(String fromAccount, String toAccount) {
        System.out.println("  [RTGS] Validating high-value transaction...");

        if (!NPCIPaymentStandard.validateAccountNumber(fromAccount)) {
            System.out.println("  Error: Invalid source account!");
            return false;
        }
        if (!NPCIPaymentStandard.validateAccountNumber(toAccount)) {
            System.out.println("  Error: Invalid destination account!");
            return false;
        }

        System.out.println("  [RTGS] ✓ High-value validation successful");
        return true;
    }

    @Override
    public void displayServiceLimits() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│     RTGS SERVICE LIMITS (High Value)    │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Min Amount        : Rs.2,00,000");
        System.out.println("  Max Amount        : No Limit");
        System.out.println("  Charges           : Rs.25 - Rs.50");
        System.out.println("  Settlement        : Real-time");
        System.out.println("  Availability      : 24x7");
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING NPCIPaymentStandard INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String initiateTransaction(String sourceAccount, String destinationAccount, double amount) {
        String txnId = formatTransactionId(generateTransactionId());
        this.currentTransactionId = txnId;
        this.transactionStatus = "INITIATED";
        return txnId;
    }

    @Override
    public boolean validateTransaction(String transactionId) {
        return transactionId != null && transactionId.contains("RTGS");
    }

    @Override
    public boolean executeTransaction(String transactionId) {
        this.transactionStatus = "SUCCESS";
        recordAuditEvent("EXECUTE", "RTGS executed: " + transactionId);
        return true;
    }

    @Override
    public String getTransactionStatus(String transactionId) {
        return transactionStatus;
    }

    @Override
    public boolean reverseTransaction(String transactionId, String reason) {
        this.transactionStatus = "REVERSED";
        return true;
    }

    @Override
    public String getTransactionDetails(String transactionId) {
        return "RTGS Transaction | ID: " + transactionId + " | Status: " + transactionStatus;
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING Auditable INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String getAuditLog() {
        StringBuilder log = new StringBuilder("=== RTGS AUDIT LOG ===\n");
        for (String entry : auditLog) log.append(entry).append("\n");
        return log.toString();
    }

    @Override
    public void recordAuditEvent(String eventType, String details) {
        auditLog.add(formatAuditEntry(eventType, details));
    }

    @Override
    public String getAuditEntityId() {
        return currentTransactionId != null ? currentTransactionId : "NO_TXN";
    }

    @Override
    public String getAuditEntityType() {
        return "RTGS_PAYMENT";
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING Notifiable INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public void sendNotification(String recipient, String message, String channel) {
        System.out.println("  [RTGS Notification] " + message);
    }

    @Override
    public String getNotificationTemplate(String templateType) {
        return "RTGS High-Value Transaction: {status}";
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) return accountNumber;
        return "XXXX" + accountNumber.substring(accountNumber.length() - 4);
    }
}