package com.npci.level5;

/**
 * Level 5: Abstraction - Interface
 *
 * Key Concepts:
 * - Interface = 100% abstract contract (before Java 8)
 * - All methods are implicitly public abstract
 * - All fields are implicitly public static final (constants)
 * - A class can implement MULTIPLE interfaces
 * - Use when: Define a contract/standard that multiple unrelated classes must follow
 *
 * Real-world analogy:
 * - NPCI defines the STANDARD that all payment channels must follow
 * - Every bank/payment provider MUST implement these methods
 * - Ensures interoperability across the payment ecosystem
 */
public interface NPCIPaymentStandard {

    // ═══════════════════════════════════════════════════════════════
    // CONSTANTS - Implicitly public static final
    // ═══════════════════════════════════════════════════════════════

    String NPCI_VERSION = "2.0";
    String NPCI_COUNTRY_CODE = "IN";
    int MAX_RETRY_ATTEMPTS = 3;
    int TRANSACTION_TIMEOUT_SECONDS = 30;

    // ═══════════════════════════════════════════════════════════════
    // ABSTRACT METHODS - Must be implemented by all payment channels
    // ═══════════════════════════════════════════════════════════════

    /**
     * Initiate a transaction
     * @return Transaction ID
     */
    String initiateTransaction(String sourceAccount, String destinationAccount, double amount);

    /**
     * Validate transaction before processing
     */
    boolean validateTransaction(String transactionId);

    /**
     * Execute the transaction
     */
    boolean executeTransaction(String transactionId);

    /**
     * Get transaction status
     */
    String getTransactionStatus(String transactionId);

    /**
     * Reverse/Refund a transaction
     */
    boolean reverseTransaction(String transactionId, String reason);

    /**
     * Get transaction details
     */
    String getTransactionDetails(String transactionId);

    // ═══════════════════════════════════════════════════════════════
    // DEFAULT METHODS (Java 8+) - Can have implementation
    // Can be overridden by implementing classes
    // ═══════════════════════════════════════════════════════════════

    /**
     * Format transaction ID according to NPCI standard
     */
    default String formatTransactionId(String rawId) {
        return NPCI_COUNTRY_CODE + "_" + rawId + "_" + System.currentTimeMillis();
    }

    /**
     * Check if transaction amount is within NPCI limits
     */
    default boolean isWithinNPCILimits(double amount) {
        return amount > 0 && amount <= 10000000;  // 1 Crore limit
    }

    /**
     * Get NPCI compliance version
     */
    default String getNPCIVersion() {
        return "NPCI Standard Version: " + NPCI_VERSION;
    }

    /**
     * Generate NPCI compliant reference number
     */
    default String generateNPCIReference() {
        return "NPCI" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    // ═══════════════════════════════════════════════════════════════
    // STATIC METHODS (Java 8+) - Utility methods
    // Called via interface name: NPCIPaymentStandard.validateIFSC()
    // ═══════════════════════════════════════════════════════════════

    /**
     * Validate IFSC code format
     */
    static boolean validateIFSC(String ifsc) {
        if (ifsc == null || ifsc.length() != 11) {
            return false;
        }
        // Format: First 4 chars (bank code), 5th char is 0, last 6 chars (branch code)
        return ifsc.matches("[A-Z]{4}0[A-Z0-9]{6}");
    }

    /**
     * Validate account number format
     */
    static boolean validateAccountNumber(String accountNumber) {
        if (accountNumber == null) {
            return false;
        }
        // Account number: 9-18 digits
        return accountNumber.matches("\\d{9,18}");
    }

    /**
     * Validate UPI ID format
     */
    static boolean validateUPIId(String upiId) {
        if (upiId == null) {
            return false;
        }
        // Format: username@bankhandle
        return upiId.matches("[a-zA-Z0-9.\\-_]+@[a-zA-Z]+");
    }

    /**
     * Get transaction type from ID
     */
    static String getTransactionType(String transactionId) {
        if (transactionId == null) return "UNKNOWN";
        if (transactionId.startsWith("UPI")) return "UPI";
        if (transactionId.startsWith("IMPS")) return "IMPS";
        if (transactionId.startsWith("NEFT")) return "NEFT";
        if (transactionId.startsWith("RTGS")) return "RTGS";
        return "UNKNOWN";
    }
}