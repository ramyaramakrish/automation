package com.npci.level5;

import java.util.ArrayList;
import java.util.List;

/**
 * Level 5: Abstraction - NEFT Payment Implementation
 *
 * NEFT (National Electronic Funds Transfer):
 * - Batch-based settlement (every 30 minutes)
 * - No upper limit
 * - Uses account number + IFSC
 */
public class NEFTPayment extends PaymentService implements NPCIPaymentStandard, Auditable, Notifiable {

    private String currentTransactionId;
    private String transactionStatus;
    private List<String> auditLog;

    public NEFTPayment() {
        super("NEFT Payment", "NEFT");
        this.minAmount = 1;
        this.maxAmount = Double.MAX_VALUE;  // No upper limit
        this.auditLog = new ArrayList<>();
        recordAuditEvent("INIT", "NEFT Payment Service initialized");
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING PaymentService ABSTRACT METHODS
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String processPayment(String fromAccount, String toAccount, double amount) {
        String txnId = generateTransactionId();
        this.currentTransactionId = txnId;

        double charges = calculateCharges(amount);

        System.out.println("  [NEFT] Queuing for batch processing...");
        System.out.println("  From Account: " + maskAccountNumber(fromAccount));
        System.out.println("  To Account: " + maskAccountNumber(toAccount));
        System.out.println("  Amount: Rs." + String.format("%,.2f", amount));
        System.out.println("  Charges: Rs." + charges);
        System.out.println("  Transaction ID: " + txnId);
        System.out.println("  Status: PENDING (Batch Processing)");

        this.transactionStatus = "PENDING";
        recordAuditEvent("PAYMENT", "NEFT queued: Rs." + amount);

        return txnId;
    }

    @Override
    public double calculateCharges(double amount) {
        if (amount <= 10000) return 2.50;
        if (amount <= 100000) return 5.00;
        if (amount <= 200000) return 15.00;
        return 25.00;
    }

    @Override
    public String getSettlementTime() {
        return "Within 2 hours (Batch every 30 mins)";
    }

    @Override
    public boolean validatePaymentDetails(String fromAccount, String toAccount) {
        System.out.println("  [NEFT] Validating account and IFSC...");

        if (!NPCIPaymentStandard.validateAccountNumber(fromAccount)) {
            System.out.println("  Error: Invalid source account!");
            return false;
        }
        if (!NPCIPaymentStandard.validateAccountNumber(toAccount)) {
            System.out.println("  Error: Invalid destination account!");
            return false;
        }

        System.out.println("  [NEFT] ✓ Validation successful");
        return true;
    }

    @Override
    public void displayServiceLimits() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│          NEFT SERVICE LIMITS            │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Min Amount        : Rs." + minAmount);
        System.out.println("  Max Amount        : No Limit");
        System.out.println("  Charges           : Rs.2.50 - Rs.25");
        System.out.println("  Settlement        : Batch (30 mins)");
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
        return transactionId != null && transactionId.contains("NEFT");
    }

    @Override
    public boolean executeTransaction(String transactionId) {
        this.transactionStatus = "SUCCESS";
        recordAuditEvent("EXECUTE", "Batch processed: " + transactionId);
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
        return "NEFT Transaction | ID: " + transactionId + " | Status: " + transactionStatus;
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING Auditable INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String getAuditLog() {
        StringBuilder log = new StringBuilder("=== NEFT AUDIT LOG ===\n");
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
        return "NEFT_PAYMENT";
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING Notifiable INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public void sendNotification(String recipient, String message, String channel) {
        System.out.println("  [NEFT Notification] " + message);
    }

    @Override
    public String getNotificationTemplate(String templateType) {
        return "NEFT Transaction: {status}";
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) return accountNumber;
        return "XXXX" + accountNumber.substring(accountNumber.length() - 4);
    }
}