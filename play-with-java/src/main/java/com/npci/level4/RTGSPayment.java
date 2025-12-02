package com.npci.level4;

/**
 * Level 4: Polymorphism - RTGS Payment Service
 *
 * RTGS specific implementation:
 * - Real-time settlement
 * - Minimum Rs.2 Lakhs
 * - No upper limit
 * - For high-value transactions
 */
public class RTGSPayment extends PaymentService {

    public RTGSPayment() {
        super();
        this.serviceName = "RTGS Payment";
        this.serviceCode = "RTGS";
        this.minAmount = 200000;  // Minimum 2 Lakhs
        this.maxAmount = Double.MAX_VALUE;  // No upper limit
        this.charges = 25;
        this.isAvailable24x7 = true;  // Now 24x7
    }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS
    // ═══════════════════════════════════════════════════════════════

    @Override
    public boolean validatePayment(double amount) {
        if (amount < minAmount) {
            System.out.println("[RTGS] Error: Minimum amount for RTGS is Rs.2,00,000");
            System.out.println("[RTGS] Use NEFT or IMPS for smaller amounts.");
            return false;
        }

        System.out.println("[RTGS] Validating RTGS transaction...");
        System.out.println("[RTGS] ✓ High-value transaction validated");
        return true;
    }

    @Override
    public double calculateCharges(double amount) {
        // RTGS charges based on amount
        if (amount <= 500000) {
            return 25.00;
        } else {
            return 50.00;
        }
    }

    @Override
    public String processPayment(String fromAccount, String toAccount, double amount) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║           RTGS PAYMENT (High Value)           ║");
        System.out.println("╠═══════════════════════════════════════════════╣");

        if (!validatePayment(amount)) {
            System.out.println("║  Status: FAILED                               ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
            return "FAILED";
        }

        String txnId = generateTransactionId();
        double charges = calculateCharges(amount);

        System.out.println("  From Account   : " + fromAccount);
        System.out.println("  To Account     : " + toAccount);
        System.out.println("  IFSC           : NPCI0001234");
        System.out.println("  Amount         : Rs." + String.format("%,.2f", amount));
        System.out.println("  Charges        : Rs." + charges);
        System.out.println("  Total Debit    : Rs." + String.format("%,.2f", (amount + charges)));
        System.out.println("  Transaction ID : " + txnId);
        System.out.println("  Settlement     : " + getSettlementTime());
        System.out.println("  Status         : SUCCESS ✓ (Real-time)");
        System.out.println("╚═══════════════════════════════════════════════╝");

        return txnId;
    }

    @Override
    public String getSettlementTime() {
        return "Instant (Real-time Gross Settlement)";
    }

    @Override
    protected String generateTransactionId() {
        return "RTGS" + System.currentTimeMillis();
    }
}