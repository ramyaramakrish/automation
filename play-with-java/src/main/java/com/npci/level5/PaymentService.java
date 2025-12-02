package com.npci.level5;

/**
 * Level 5: Abstraction - Abstract Class
 *
 * Key Concepts:
 * - Abstraction = Hiding implementation details, showing only functionality
 * - Abstract Class = Partial blueprint (some methods implemented, some not)
 * - Abstract Method = Method without body (child MUST implement)
 * - Cannot instantiate abstract class directly
 * - Can have constructors, fields, concrete methods
 * - Use when: Classes share common code AND need some methods forced to be implemented
 *
 * Real-world analogy:
 * - NPCI defines WHAT payment services must do (abstract methods)
 * - Each bank decides HOW to do it (concrete implementation)
 */
public abstract class PaymentService {

    // ═══════════════════════════════════════════════════════════════
    // FIELDS - Common to all payment services
    // ═══════════════════════════════════════════════════════════════

    protected String serviceName;
    protected String serviceCode;
    protected double minAmount;
    protected double maxAmount;
    protected boolean isActive;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR - Abstract class CAN have constructors
    // ═══════════════════════════════════════════════════════════════

    public PaymentService(String serviceName, String serviceCode) {
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.isActive = true;
        System.out.println("[PaymentService] Initializing " + serviceName);
    }

    // ═══════════════════════════════════════════════════════════════
    // CONCRETE METHODS - Implemented here, inherited by children
    // ═══════════════════════════════════════════════════════════════

    /**
     * Generate transaction ID - same for all payment types
     */
    public String generateTransactionId() {
        return serviceCode + System.currentTimeMillis();
    }

    /**
     * Validate basic amount rules - common validation
     */
    public boolean validateAmount(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Amount must be positive!");
            return false;
        }
        if (amount < minAmount) {
            System.out.println("Error: Amount below minimum limit of Rs." + minAmount);
            return false;
        }
        if (amount > maxAmount) {
            System.out.println("Error: Amount exceeds maximum limit of Rs." + maxAmount);
            return false;
        }
        return true;
    }

    /**
     * Log transaction - common logging
     */
    protected void logTransaction(String txnId, String status) {
        System.out.println("[LOG] " + serviceName + " | TxnID: " + txnId + " | Status: " + status);
    }

    /**
     * Check if service is active
     */
    public boolean isServiceActive() {
        return isActive;
    }

    /**
     * Activate/Deactivate service
     */
    public void setServiceActive(boolean active) {
        this.isActive = active;
        System.out.println(serviceName + " is now " + (active ? "ACTIVE" : "INACTIVE"));
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getServiceName() { return serviceName; }
    public String getServiceCode() { return serviceCode; }
    public double getMinAmount() { return minAmount; }
    public double getMaxAmount() { return maxAmount; }

    // ═══════════════════════════════════════════════════════════════
    // ABSTRACT METHODS - No body, MUST be implemented by children
    // ═══════════════════════════════════════════════════════════════

    /**
     * Process payment - each service implements differently
     * UPI: Instant via VPA
     * IMPS: Instant via account number
     * NEFT: Batch processing
     * RTGS: Real-time gross settlement
     */
    public abstract String processPayment(String fromAccount, String toAccount, double amount);

    /**
     * Calculate charges - different for each service
     */
    public abstract double calculateCharges(double amount);

    /**
     * Get settlement time - different for each service
     */
    public abstract String getSettlementTime();

    /**
     * Validate service-specific rules
     */
    public abstract boolean validatePaymentDetails(String fromAccount, String toAccount);

    /**
     * Get service-specific limits
     */
    public abstract void displayServiceLimits();

    // ═══════════════════════════════════════════════════════════════
    // TEMPLATE METHOD PATTERN - Defines algorithm structure
    // Concrete steps call abstract methods (implemented by children)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Execute payment - Template Method
     * Defines the steps, but some steps are abstract (implemented by children)
     */
    public final String executePayment(String fromAccount, String toAccount, double amount) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║   EXECUTING " + serviceName.toUpperCase());
        System.out.println("╠═══════════════════════════════════════════════╣");

        // Step 1: Check service status (concrete)
        if (!isServiceActive()) {
            System.out.println("  Error: Service is currently inactive!");
            System.out.println("╚═══════════════════════════════════════════════╝");
            return "FAILED_SERVICE_INACTIVE";
        }

        // Step 2: Validate amount (concrete)
        if (!validateAmount(amount)) {
            System.out.println("╚═══════════════════════════════════════════════╝");
            return "FAILED_INVALID_AMOUNT";
        }

        // Step 3: Validate payment details (abstract - child implements)
        if (!validatePaymentDetails(fromAccount, toAccount)) {
            System.out.println("╚═══════════════════════════════════════════════╝");
            return "FAILED_INVALID_DETAILS";
        }

        // Step 4: Calculate charges (abstract - child implements)
        double charges = calculateCharges(amount);
        System.out.println("  Charges: Rs." + charges);

        // Step 5: Process payment (abstract - child implements)
        String txnId = processPayment(fromAccount, toAccount, amount);

        // Step 6: Log transaction (concrete)
        logTransaction(txnId, "SUCCESS");

        System.out.println("  Settlement: " + getSettlementTime());
        System.out.println("╚═══════════════════════════════════════════════╝");

        return txnId;
    }
}