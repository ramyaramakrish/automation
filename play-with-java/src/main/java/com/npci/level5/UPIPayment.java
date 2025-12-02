package com.npci.level5;

import java.util.ArrayList;
import java.util.List;

/**
 * Level 5: Abstraction - Concrete Implementation
 *
 * UPIPayment:
 * - EXTENDS abstract class PaymentService (inherits + implements abstract methods)
 * - IMPLEMENTS interface NPCIPaymentStandard (must implement all methods)
 * - IMPLEMENTS interface Auditable (audit trail)
 * - IMPLEMENTS interface Notifiable (notifications)
 *
 * Demonstrates: Class implementing multiple interfaces + extending abstract class
 */
public class UPIPayment extends PaymentService implements NPCIPaymentStandard, Auditable, Notifiable {

    // ═══════════════════════════════════════════════════════════════
    // FIELDS
    // ═══════════════════════════════════════════════════════════════

    private String currentTransactionId;
    private String transactionStatus;
    private List<String> auditLog;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public UPIPayment() {
        super("UPI Payment", "UPI");  // Call abstract class constructor
        this.minAmount = 1;
        this.maxAmount = 100000;
        this.auditLog = new ArrayList<>();
        recordAuditEvent("INIT", "UPI Payment Service initialized");
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING ABSTRACT METHODS FROM PaymentService
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String processPayment(String fromAccount, String toAccount, double amount) {
        String txnId = generateTransactionId();
        this.currentTransactionId = txnId;

        System.out.println("  [UPI] Processing instant payment...");
        System.out.println("  From VPA: " + fromAccount);
        System.out.println("  To VPA: " + toAccount);
        System.out.println("  Amount: Rs." + amount);
        System.out.println("  Transaction ID: " + txnId);
        System.out.println("  Status: SUCCESS ✓");

        this.transactionStatus = "SUCCESS";

        // Audit and notify
        recordAuditEvent("PAYMENT", "Payment processed: Rs." + amount);
        sendNotification(fromAccount, "UPI payment of Rs." + amount + " successful. TxnID: " + txnId, CHANNEL_SMS);

        return txnId;
    }

    @Override
    public double calculateCharges(double amount) {
        // UPI is free for P2P
        return 0;
    }

    @Override
    public String getSettlementTime() {
        return "Instant (Real-time)";
    }

    @Override
    public boolean validatePaymentDetails(String fromAccount, String toAccount) {
        System.out.println("  [UPI] Validating VPA addresses...");

        if (!NPCIPaymentStandard.validateUPIId(fromAccount)) {
            System.out.println("  Error: Invalid source UPI ID format!");
            return false;
        }
        if (!NPCIPaymentStandard.validateUPIId(toAccount)) {
            System.out.println("  Error: Invalid destination UPI ID format!");
            return false;
        }
        if (fromAccount.equals(toAccount)) {
            System.out.println("  Error: Source and destination cannot be same!");
            return false;
        }

        System.out.println("  [UPI] ✓ VPA validation successful");
        return true;
    }

    @Override
    public void displayServiceLimits() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│          UPI SERVICE LIMITS             │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Min Amount        : Rs." + minAmount);
        System.out.println("  Max Amount        : Rs." + maxAmount);
        System.out.println("  Daily Limit       : Rs.100,000");
        System.out.println("  Transactions/Day  : 20");
        System.out.println("  Charges           : FREE");
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
        System.out.println("[NPCI] Transaction initiated: " + txnId);
        recordAuditEvent("INITIATE", "Transaction initiated for Rs." + amount);
        return txnId;
    }

    @Override
    public boolean validateTransaction(String transactionId) {
        System.out.println("[NPCI] Validating transaction: " + transactionId);
        return transactionId != null && transactionId.contains("UPI");
    }

    @Override
    public boolean executeTransaction(String transactionId) {
        System.out.println("[NPCI] Executing transaction: " + transactionId);
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
        System.out.println("[NPCI] Reversing transaction: " + transactionId);
        System.out.println("       Reason: " + reason);
        this.transactionStatus = "REVERSED";
        recordAuditEvent("REVERSE", "Transaction reversed: " + reason);
        return true;
    }

    @Override
    public String getTransactionDetails(String transactionId) {
        return "UPI Transaction | ID: " + transactionId + " | Status: " + transactionStatus;
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING Auditable INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public String getAuditLog() {
        StringBuilder log = new StringBuilder();
        log.append("=== UPI PAYMENT AUDIT LOG ===\n");
        for (String entry : auditLog) {
            log.append(entry).append("\n");
        }
        return log.toString();
    }

    @Override
    public void recordAuditEvent(String eventType, String details) {
        String entry = formatAuditEntry(eventType, details);
        auditLog.add(entry);
    }

    @Override
    public String getAuditEntityId() {
        return currentTransactionId != null ? currentTransactionId : "NO_TXN";
    }

    @Override
    public String getAuditEntityType() {
        return "UPI_PAYMENT";
    }

    // ═══════════════════════════════════════════════════════════════
    // IMPLEMENTING Notifiable INTERFACE
    // ═══════════════════════════════════════════════════════════════

    @Override
    public void sendNotification(String recipient, String message, String channel) {
        System.out.println("\n  --- Notification ---");
        switch (channel) {
            case CHANNEL_SMS:
                sendSMS(recipient, message);
                break;
            case CHANNEL_EMAIL:
                sendEmail(recipient, "UPI Transaction Alert", message);
                break;
            case CHANNEL_PUSH:
                sendPushNotification(recipient, "UPI Alert", message);
                break;
            case CHANNEL_ALL:
                sendSMS(recipient, message);
                sendEmail(recipient, "UPI Transaction Alert", message);
                sendPushNotification(recipient, "UPI Alert", message);
                break;
        }
    }

    @Override
    public String getNotificationTemplate(String templateType) {
        switch (templateType) {
            case "SUCCESS":
                return "Your UPI payment of {amount} to {beneficiary} is successful. TxnID: {txnId}";
            case "FAILED":
                return "Your UPI payment of {amount} to {beneficiary} has failed. Reason: {reason}";
            case "PENDING":
                return "Your UPI payment of {amount} is being processed. TxnID: {txnId}";
            default:
                return "UPI Transaction Update";
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // UPI SPECIFIC METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Collect request (UPI specific)
     */
    public String sendCollectRequest(String fromVPA, String toVPA, double amount, String remarks) {
        String collectId = "CR" + System.currentTimeMillis();
        System.out.println("\n[UPI] Collect Request Sent");
        System.out.println("  From: " + fromVPA);
        System.out.println("  To: " + toVPA);
        System.out.println("  Amount: Rs." + amount);
        System.out.println("  Remarks: " + remarks);
        System.out.println("  Collect ID: " + collectId);

        recordAuditEvent("COLLECT_REQUEST", "Collect request sent to " + toVPA);
        return collectId;
    }

    /**
     * Check VPA availability
     */
    public boolean checkVPAAvailability(String vpa) {
        System.out.println("[UPI] Checking VPA: " + vpa);
        boolean valid = NPCIPaymentStandard.validateUPIId(vpa);
        System.out.println("[UPI] VPA " + vpa + " is " + (valid ? "VALID" : "INVALID"));
        return valid;
    }
}