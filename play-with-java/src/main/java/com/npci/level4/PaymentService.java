package com.npci.level4;

/**
 * Level 4: Polymorphism - Payment Service Base Class
 *
 * This class demonstrates polymorphism with payment channels.
 * Different payment methods (UPI, IMPS, NEFT, RTGS) all extend this class
 * and override methods with their specific implementations.
 */
public class PaymentService {

    protected String serviceName;
    protected String serviceCode;
    protected double minAmount;
    protected double maxAmount;
    protected double charges;
    protected boolean isAvailable24x7;

    public PaymentService() {
        this.serviceName = "Generic Payment";
        this.serviceCode = "GEN";
        this.minAmount = 1;
        this.maxAmount = 100000;
        this.charges = 0;
        this.isAvailable24x7 = false;
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getServiceName() { return serviceName; }
    public String getServiceCode() { return serviceCode; }
    public double getMinAmount() { return minAmount; }
    public double getMaxAmount() { return maxAmount; }
    public double getCharges() { return charges; }
    public boolean isAvailable24x7() { return isAvailable24x7; }

    // ═══════════════════════════════════════════════════════════════
    // METHODS TO BE OVERRIDDEN
    // ═══════════════════════════════════════════════════════════════

    /**
     * Validate payment - to be overridden by each payment type
     */
    public boolean validatePayment(double amount) {
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
     * Calculate charges - to be overridden
     */
    public double calculateCharges(double amount) {
        return charges;
    }

    /**
     * Process payment - to be overridden
     */
    public String processPayment(String fromAccount, String toAccount, double amount) {
        System.out.println("[" + serviceName + "] Processing payment...");

        if (!validatePayment(amount)) {
            return "FAILED";
        }

        double totalCharges = calculateCharges(amount);

        System.out.println("From: " + fromAccount);
        System.out.println("To: " + toAccount);
        System.out.println("Amount: Rs." + amount);
        System.out.println("Charges: Rs." + totalCharges);
        System.out.println("Status: SUCCESS");

        return generateTransactionId();
    }

    /**
     * Get settlement time - to be overridden
     */
    public String getSettlementTime() {
        return "T+1 (Next Business Day)";
    }

    /**
     * Generate transaction ID
     */
    protected String generateTransactionId() {
        return serviceCode + System.currentTimeMillis();
    }

    /**
     * Display service info
     */
    public void displayServiceInfo() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│          PAYMENT SERVICE INFO           │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Service Name    : " + serviceName);
        System.out.println("  Service Code    : " + serviceCode);
        System.out.println("  Min Amount      : Rs." + minAmount);
        System.out.println("  Max Amount      : Rs." + maxAmount);
        System.out.println("  Charges         : Rs." + charges);
        System.out.println("  Available 24x7  : " + (isAvailable24x7 ? "Yes" : "No"));
        System.out.println("  Settlement      : " + getSettlementTime());
        System.out.println("└─────────────────────────────────────────┘");
    }
}