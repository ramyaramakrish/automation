package com.npci.level4;

/**
 * Level 4: Polymorphism - IMPS Payment Service
 *
 * IMPS specific implementation:
 * - Instant settlement
 * - Available 24x7
 * - Nominal charges
 * - Medium limits
 */
public class IMPSPayment extends PaymentService {

    public IMPSPayment() {
        super();
        this.serviceName = "IMPS Payment";
        this.serviceCode = "IMPS";
        this.minAmount = 1;
        this.maxAmount = 500000;
        this.charges = 5;
        this.isAvailable24x7 = true;
    }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS
    // ═══════════════════════════════════════════════════════════════

    @Override
    public boolean validatePayment(double amount) {
        if (!super.validatePayment(amount)) {
            return false;
        }

        System.out.println("[IMPS] Validating IMPS transaction...");
        System.out.println("[IMPS] ✓ Amount within IMPS limits");
        return true;
    }

    @Override
    public double calculateCharges(double amount) {
        // IMPS charges based on amount
        if (amount <= 10000) {
            return 2.50;
        } else if (amount <= 100000) {
            return 5.00;
        } else {
            return 15.00;
        }
    }

    @Override
    public String processPayment(String fromAccount, String toAccount, double amount) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           IMPS PAYMENT                ║");
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
        System.out.println("  Amount         : Rs." + amount);
        System.out.println("  Charges        : Rs." + charges);
        System.out.println("  Total Debit    : Rs." + (amount + charges));
        System.out.println("  Transaction ID : " + txnId);
        System.out.println("  Settlement     : " + getSettlementTime());
        System.out.println("  Status         : SUCCESS ✓");
        System.out.println("╚═══════════════════════════════════════╝");

        return txnId;
    }

    @Override
    public String getSettlementTime() {
        return "Instant (Real-time)";
    }

    @Override
    protected String generateTransactionId() {
        return "IMPS" + System.currentTimeMillis();
    }
}