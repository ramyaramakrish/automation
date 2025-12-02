package com.npci.level5;

import java.util.ArrayList;
import java.util.List;

/**
 * Level 5: Abstraction - IMPS Payment Implementation
 *
 * IMPS (Immediate Payment Service):
 * - Instant inter-bank transfer
 * - 24x7 availability
 * - Uses account number + IFSC
 */
public class IMPSPayment extends PaymentService implements NPCIPaymentStandard, Auditable, Notifiable {

    private String currentTransactionId;
    private String transactionStatus;
    private List<String> auditLog;

    public IMPSPayment() {
        super("IMPS Payment", "IMPS");
        this.minAmount = 1;
        this.maxAmount = 500000;
        this.auditLog = new ArrayList<>();
        recordAuditEvent("INIT", "IMPS Payment Service initialized");
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING PaymentService ABSTRACT METHODS
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String processPayment(String fromAccount, String toAccount, double amount) {
        String txnId = generateTransactionId();
        this.currentTransactionId = txnId;

        double charges = calculateCharges(amount);

        System.out.println("  [IMPS] Processing instant bank transfer...");
        System.out.println("  From Account: " + maskAccountNumber(fromAccount));
        System.out.println("  To Account: " + maskAccountNumber(toAccount));
        System.out.println("  Amount: Rs." + amount);
        System.out.println("  Charges: Rs." + charges);
        System.out.println("  Total Debit: Rs." + (amount + charges));
        System.out.println("  Transaction ID: " + txnId);
        System.out.println("  Status: SUCCESS ✓");

        this.transactionStatus = "SUCCESS";
        recordAuditEvent("PAYMENT", "IMPS payment: Rs." + amount + " + charges Rs." + charges);

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
        return "Instant (Real-time)";
    }

    @Override
    public boolean validatePaymentDetails(String fromAccount, String toAccount) {
        System.out.println("  [IMPS] Validating account details...");

        if (!NPCIPaymentStandard.validateAccountNumber(fromAccount)) {
            System.out.println("  Error: Invalid source account number!");
            return false;
        }
        if (!NPCIPaymentStandard.validateAccountNumber(toAccount)) {
            System.out.println("  Error: Invalid destination account number!");
            return false;
        }

        System.out.println("  [IMPS] ✓ Account validation successful");
        return true;
    }

    @Override
    public void displayServiceLimits() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│          IMPS SERVICE LIMITS            │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Min Amount        : Rs." + minAmount);
        System.out.println("  Max Amount        : Rs." + maxAmount);
        System.out.println("  Charges           : Rs.2.50 - Rs.25");
        System.out.println("  Settlement        : Instant");
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
        recordAuditEvent("INITIATE", "IMPS transaction initiated");
        return txnId;
    }

    @Override
    public boolean validateTransaction(String transactionId) {
        return transactionId != null && transactionId.contains("IMPS");
    }

    @Override
    public boolean executeTransaction(String transactionId) {
        this.transactionStatus = "SUCCESS";
        recordAuditEvent("EXECUTE", "Transaction executed: " + transactionId);
        return true;
    }

    @Override
    public String getTransactionStatus(String transactionId) {
        return transactionStatus != null ? transactionStatus : "UNKNOWN";
    }

    @Override
    public boolean reverseTransaction(String transactionId, String reason) {
        this.transactionStatus = "REVERSED";
        recordAuditEvent("REVERSE", "Reversed: " + reason);
        return true;
    }

    @Override
    public String getTransactionDetails(String transactionId) {
        return "IMPS Transaction | ID: " + transactionId + " | Status: " + transactionStatus;
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING Auditable INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String getAuditLog() {
        StringBuilder log = new StringBuilder("=== IMPS AUDIT LOG ===\n");
        for (String entry : auditLog) {
            log.append(entry).append("\n");
        }
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
        return "IMPS_PAYMENT";
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING Notifiable INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public void sendNotification(String recipient, String message, String channel) {
        System.out.println("  [IMPS Notification] " + message);
    }

    @Override
    public String getNotificationTemplate(String templateType) {
        return "IMPS Transaction: {status}";
    }

    // ═══════════════════════════════════════════════════════════════
    // HELPER METHODS
    // ═══════════════════════════════════════════════════════════════

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) return accountNumber;
        return "XXXX" + accountNumber.substring(accountNumber.length() - 4);
    }
}