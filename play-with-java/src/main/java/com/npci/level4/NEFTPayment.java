package com.npci.level4;

/**
 * Level 4: Polymorphism - NEFT Payment Service
 *
 * NEFT specific implementation:
 * - Batch settlement (every 30 mins)
 * - Available during banking hours
 * - No upper limit
 * - Charges based on amount
 */
public class NEFTPayment extends PaymentService {

    public NEFTPayment() {
        super();
        this.serviceName = "NEFT Payment";
        this.serviceCode = "NEFT";
        this.minAmount = 1;
        this.maxAmount = Double.MAX_VALUE;  // No upper limit
        this.charges = 2.50;
        this.isAvailable24x7 = true;  // Now 24x7
    }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS
    // ═══════════════════════════════════════════════════════════════

    @Override
    public boolean validatePayment(double amount) {
        if (amount < minAmount) {
            System.out.println("[NEFT] Error: Amount below minimum limit of Rs." + minAmount);
            return false;
        }

        System.out.println("[NEFT] Validating NEFT transaction...");
        System.out.println("[NEFT] ✓ Amount validated (No upper limit for NEFT)");
        return true;
    }

    @Override
    public double calculateCharges(double amount) {
        // NEFT charges based on amount slabs
        if (amount <= 10000) {
            return 2.50;
        } else if (amount <= 100000) {
            return 5.00;
        } else if (amount <= 200000) {
            return 15.00;
        } else {
            return 25.00;
        }
    }

    @Override
    public String processPayment(String fromAccount, String toAccount, double amount) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           NEFT PAYMENT                ║");
        System.out.println("╠═══════════════════════════════════════╣");

        if (!validatePayment(amount)) {
            System.out.println("║  Status: FAILED                       ║");
            System.out.println("╚═══════════════════════════════════════╝");
            return "FAILED";
        }

        String txnId = generateTransactionId();
        double charges = calculateCharges(amount);

        System.out.println("  From Account   : " + fromAccount);
        System.out.println("  To Account     : " + toAccount);
        System.out.println("  IFSC           : NPCI0001234");
        System.out.println("  Amount         : Rs." + amount);
        System.out.println("  Charges        : Rs." + charges);
        System.out.println("  Total Debit    : Rs." + (amount + charges));
        System.out.println("  Transaction ID : " + txnId);
        System.out.println("  Settlement     : " + getSettlementTime());
        System.out.println("  Status         : PENDING (Batch Processing)");
        System.out.println("╚═══════════════════════════════════════╝");

        return txnId;
    }

    @Override
    public String getSettlementTime() {
        return "Within 2 hours (Batch every 30 mins)";
    }

    @Override
    protected String generateTransactionId() {
        return "NEFT" + System.currentTimeMillis();
    }
}