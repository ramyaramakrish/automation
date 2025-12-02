package com.npci.level4;

/**
 * Level 4: Polymorphism - UPI Payment Service
 *
 * UPI specific implementation:
 * - Instant settlement
 * - Available 24x7
 * - No charges for P2P
 * - Lower limits
 */
public class UPIPayment extends PaymentService {

    private String upiId;

    public UPIPayment() {
        super();
        this.serviceName = "UPI Payment";
        this.serviceCode = "UPI";
        this.minAmount = 1;
        this.maxAmount = 100000;
        this.charges = 0;
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

        // UPI specific validations
        System.out.println("[UPI] Validating UPI transaction...");
        System.out.println("[UPI] ✓ Amount within UPI limits");
        return true;
    }

    @Override
    public double calculateCharges(double amount) {
        // UPI P2P is free
        return 0;
    }

    @Override
    public String processPayment(String fromAccount, String toAccount, double amount) {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║           UPI PAYMENT                 ║");
        System.out.println("╠═══════════════════════════════════════╣");

        if (!validatePayment(amount)) {
            System.out.println("║  Status: FAILED                       ║");
            System.out.println("╚═══════════════════════════════════════╝");
            return "FAILED";
        }

        String txnId = generateTransactionId();

        System.out.println("  From UPI ID    : " + fromAccount);
        System.out.println("  To UPI ID      : " + toAccount);
        System.out.println("  Amount         : Rs." + amount);
        System.out.println("  Charges        : Rs." + calculateCharges(amount));
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
        return "UPI" + System.currentTimeMillis();
    }

    // ═══════════════════════════════════════════════════════════════
    // UPI SPECIFIC METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Collect request (UPI specific)
     */
    public String sendCollectRequest(String fromUpiId, String toUpiId, double amount, String remarks) {
        System.out.println("\n[UPI] Sending collect request...");
        System.out.println("  From: " + fromUpiId);
        System.out.println("  To: " + toUpiId);
        System.out.println("  Amount: Rs." + amount);
        System.out.println("  Remarks: " + remarks);
        System.out.println("  Status: Collect Request Sent!");
        return "CR" + System.currentTimeMillis();
    }
}