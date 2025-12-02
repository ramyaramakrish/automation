package com.npci.level4;

/**
 * Level 4: Polymorphism - Transaction Processor
 *
 * This class demonstrates the power of polymorphism.
 * It can process ANY account type and ANY payment type
 * using parent class references.
 */
public class TransactionProcessor {

    // ═══════════════════════════════════════════════════════════════
    // POLYMORPHIC METHODS - Work with any account type
    // ═══════════════════════════════════════════════════════════════

    /**
     * Transfer between ANY account types
     * Uses parent reference BankAccount - works with Savings, Current, etc.
     */
    public boolean transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║           FUND TRANSFER                       ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("  From: " + fromAccount.getAccountNumber() + " (" + fromAccount.getAccountType() + ")");
        System.out.println("  To: " + toAccount.getAccountNumber() + " (" + toAccount.getAccountType() + ")");
        System.out.println("  Amount: Rs." + amount);
        System.out.println("├───────────────────────────────────────────────┤");

        // Check withdrawal limit (polymorphic - each account type has different logic)
        if (amount > fromAccount.getWithdrawalLimit()) {
            System.out.println("  Error: Amount exceeds withdrawal limit!");
            System.out.println("  Available: Rs." + fromAccount.getWithdrawalLimit());
            System.out.println("  Status: FAILED");
            System.out.println("╚═══════════════════════════════════════════════╝");
            return false;
        }

        // Perform transfer
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("  Status: SUCCESS ✓");
        System.out.println("╚═══════════════════════════════════════════════╝");

        return true;
    }

    /**
     * Process payment using ANY payment service
     * Uses parent reference PaymentService - works with UPI, IMPS, NEFT, RTGS
     */
    public String processPayment(PaymentService paymentService,
                                 String fromAccount, String toAccount, double amount) {
        System.out.println("\n--- Processing via " + paymentService.getServiceName() + " ---");
        return paymentService.processPayment(fromAccount, toAccount, amount);
    }

    /**
     * Process month-end for ANY account type
     */
    public void processMonthEnd(BankAccount account) {
        System.out.println("\n--- Month-End Processing for " + account.getAccountNumber() + " ---");
        account.processMonthEnd();  // Polymorphic call - different behavior for each type
    }

    /**
     * Process month-end for multiple accounts
     */
    public void processMonthEndBatch(BankAccount[] accounts) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║       BATCH MONTH-END PROCESSING              ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("  Processing " + accounts.length + " accounts...\n");

        for (BankAccount account : accounts) {
            account.processMonthEnd();  // Each account type handles differently!
            System.out.println();
        }

        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    /**
     * Calculate total interest across all accounts
     */
    public double calculateTotalInterest(BankAccount[] accounts) {
        double totalInterest = 0;

        for (BankAccount account : accounts) {
            double interest = account.calculateInterest();  // Polymorphic call
            totalInterest += interest;
            System.out.println(account.getAccountType() + " [" + account.getAccountNumber() + "]: Rs." +
                    String.format("%.2f", interest));
        }

        return totalInterest;
    }

    /**
     * Display all account info
     */
    public void displayAllAccounts(BankAccount[] accounts) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║           ALL ACCOUNTS SUMMARY                ║");
        System.out.println("╠═══════════════════════════════════════════════╣");

        double totalBalance = 0;

        for (BankAccount account : accounts) {
            System.out.println("  " + account.getSummary());
            totalBalance += account.getBalance();
        }

        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("  Total Balance: Rs." + String.format("%.2f", totalBalance));
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    /**
     * Choose best payment method based on amount
     */
    public PaymentService recommendPaymentMethod(double amount) {
        System.out.println("\nRecommending payment method for Rs." + String.format("%,.2f", amount) + "...");

        if (amount >= 200000) {
            System.out.println("Recommendation: RTGS (Real-time for high-value)");
            return new RTGSPayment();
        } else if (amount > 100000) {
            System.out.println("Recommendation: NEFT (Batch processing, no limit)");
            return new NEFTPayment();
        } else if (amount > 10000) {
            System.out.println("Recommendation: IMPS (Instant, nominal charges)");
            return new IMPSPayment();
        } else {
            System.out.println("Recommendation: UPI (Instant, free)");
            return new UPIPayment();
        }
    }

    /**
     * Compare all payment services
     */
    public void comparePaymentServices(double amount) {
        PaymentService[] services = {
                new UPIPayment(),
                new IMPSPayment(),
                new NEFTPayment(),
                new RTGSPayment()
        };

        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║           PAYMENT SERVICES COMPARISON                            ║");
        System.out.println("║           Amount: Rs." + String.format("%,.2f", amount) + "                                 ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.printf("  %-10s | %-15s | %-10s | %-15s%n", "Service", "Charges", "Settlement", "Available");
        System.out.println("  ─────────────────────────────────────────────────────────────────");

        for (PaymentService service : services) {
            boolean valid = amount >= service.getMinAmount() && amount <= service.getMaxAmount();
            String availability = valid ? (service.isAvailable24x7() ? "24x7" : "Banking Hours") : "N/A";
            String charges = valid ? "Rs." + String.format("%.2f", service.calculateCharges(amount)) : "N/A";
            String settlement = valid ? service.getSettlementTime() : "N/A";

            // Truncate settlement time for display
            if (settlement.length() > 15) {
                settlement = settlement.substring(0, 12) + "...";
            }

            System.out.printf("  %-10s | %-15s | %-15s | %-10s%n",
                    service.getServiceCode(), charges, settlement, availability);
        }

        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
    }
}