package com.npci.level5;

/**
 * Level 5: Abstraction - Payment Processor
 *
 * Demonstrates:
 * - Programming to interface (not implementation)
 * - Using abstract class references
 * - Polymorphism with abstraction
 */
public class PaymentProcessor {

    // ═══════════════════════════════════════════════════════════════
    // USING ABSTRACT CLASS REFERENCE
    // Can accept ANY PaymentService (UPI, IMPS, NEFT, RTGS)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Process payment using any payment service
     */
    public String processPayment(PaymentService service, String from, String to, double amount) {
        System.out.println("\n=== Processing via " + service.getServiceName() + " ===");
        return service.executePayment(from, to, amount);
    }

    // ═══════════════════════════════════════════════════════════════
    // USING INTERFACE REFERENCE
    // Can accept ANY class implementing NPCIPaymentStandard
    // ═══════════════════════════════════════════════════════════════

    /**
     * Process using NPCI standard interface
     */
    public String processNPCITransaction(NPCIPaymentStandard paymentChannel,
                                         String source, String destination, double amount) {
        System.out.println("\n=== NPCI Standard Transaction ===");
        System.out.println("Channel Version: " + paymentChannel.getNPCIVersion());

        // Step 1: Initiate
        String txnId = paymentChannel.initiateTransaction(source, destination, amount);
        System.out.println("Transaction Initiated: " + txnId);

        // Step 2: Validate
        if (!paymentChannel.validateTransaction(txnId)) {
            System.out.println("Validation Failed!");
            return "FAILED";
        }
        System.out.println("Transaction Validated");

        // Step 3: Execute
        if (!paymentChannel.executeTransaction(txnId)) {
            System.out.println("Execution Failed!");
            return "FAILED";
        }
        System.out.println("Transaction Executed");

        // Step 4: Status
        System.out.println("Final Status: " + paymentChannel.getTransactionStatus(txnId));

        return txnId;
    }

    /**
     * Get audit log from any auditable service
     */
    public void printAuditLog(Auditable auditable) {
        System.out.println("\n=== AUDIT LOG ===");
        System.out.println(auditable.getAuditLog());
    }

    /**
     * Recommend payment channel based on amount
     */
    public PaymentService recommendChannel(double amount) {
        System.out.println("\nRecommending channel for Rs." + String.format("%,.2f", amount));

        if (amount >= 200000) {
            System.out.println("→ Recommended: RTGS (High-value, real-time)");
            return new RTGSPayment();
        } else if (amount > 100000) {
            System.out.println("→ Recommended: NEFT (No upper limit)");
            return new NEFTPayment();
        } else if (amount > 10000) {
            System.out.println("→ Recommended: IMPS (Instant, nominal charges)");
            return new IMPSPayment();
        } else {
            System.out.println("→ Recommended: UPI (Instant, free)");
            return new UPIPayment();
        }
    }

    /**
     * Compare all payment services
     */
    public void compareServices() {
        PaymentService[] services = {
                new UPIPayment(),
                new IMPSPayment(),
                new NEFTPayment(),
                new RTGSPayment()
        };

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║            PAYMENT SERVICES COMPARISON                     ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.printf("  %-8s │ %-12s │ %-12s │ %-18s%n",
                "Service", "Min Amount", "Max Amount", "Settlement");
        System.out.println("  ─────────────────────────────────────────────────────────────");

        for (PaymentService service : services) {
            String maxAmt = service.getMaxAmount() == Double.MAX_VALUE ?
                    "No Limit" : "Rs." + String.format("%,.0f", service.getMaxAmount());

            System.out.printf("  %-8s │ Rs.%-9.0f │ %-12s │ %-18s%n",
                    service.getServiceCode(),
                    service.getMinAmount(),
                    maxAmt,
                    service.getSettlementTime().substring(0, Math.min(18, service.getSettlementTime().length())));
        }
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    /**
     * Process account transactions
     */
    public void processAccountTransaction(BankAccount account, String operation, double amount) {
        System.out.println("\n=== Account Transaction ===");
        System.out.println("Account: " + account.getAccountNumber() + " (" + account.getAccountType() + ")");
        System.out.println("Category: " + account.getAccountCategory());
        System.out.println("Operation: " + operation);

        if (operation.equals("DEPOSIT")) {
            account.deposit(amount);
        } else if (operation.equals("WITHDRAW")) {
            account.withdraw(amount);
        }

        System.out.println("Updated Balance: Rs." + account.getBalance());
    }
}