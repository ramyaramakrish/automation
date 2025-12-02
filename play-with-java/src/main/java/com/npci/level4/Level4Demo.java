package com.npci.level4;

/**
 * Level 4: Demo - Polymorphism
 *
 * Run this file to see all Level 4 concepts in action.
 */
public class Level4Demo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     LEVEL 4: POLYMORPHISM                            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Demo 1: Method Overloading (Compile-time)
        System.out.println("\n▶ DEMO 1: Method Overloading (Compile-time Polymorphism)");
        System.out.println("─────────────────────────────────────────────\n");
        demo1_MethodOverloading();

        // Demo 2: Method Overriding (Runtime)
        System.out.println("\n▶ DEMO 2: Method Overriding (Runtime Polymorphism)");
        System.out.println("─────────────────────────────────────────────\n");
        demo2_MethodOverriding();

        // Demo 3: Parent Reference, Child Object
        System.out.println("\n▶ DEMO 3: Parent Reference Holding Child Objects");
        System.out.println("─────────────────────────────────────────────\n");
        demo3_ParentReferenceChildObject();

        // Demo 4: Polymorphic Payment Processing
        System.out.println("\n▶ DEMO 4: Polymorphic Payment Processing");
        System.out.println("─────────────────────────────────────────────\n");
        demo4_PolymorphicPayments();

        // Demo 5: Transaction Processor
        System.out.println("\n▶ DEMO 5: Transaction Processor (Real-world Polymorphism)");
        System.out.println("─────────────────────────────────────────────\n");
        demo5_TransactionProcessor();

        // Demo 6: Dynamic Method Dispatch
        System.out.println("\n▶ DEMO 6: Dynamic Method Dispatch");
        System.out.println("─────────────────────────────────────────────\n");
        demo6_DynamicMethodDispatch();
    }

    /**
     * Demo 1: Method Overloading - Same method, different parameters
     */
    static void demo1_MethodOverloading() {
        BankAccount account = new BankAccount("ACC001", "Ramesh Kumar", 10000);

        System.out.println("Demonstrating deposit() overloading:\n");

        // Version 1: Just amount
        account.deposit(5000);

        System.out.println();

        // Version 2: Amount with description
        account.deposit(3000, "Cash deposit at branch");

        System.out.println();

        // Version 3: Cheque deposit
        account.deposit(10000, "CHQ123456", "SBI");

        System.out.println("\n--- Withdraw Overloading ---\n");

        // Version 1: Just amount
        account.withdraw(2000);

        System.out.println();

        // Version 2: Amount with mode
        account.withdraw(3000, "ATM");
    }

    /**
     * Demo 2: Method Overriding - Same method, different behavior
     */
    static void demo2_MethodOverriding() {
        // Create different account types
        BankAccount general = new BankAccount("GEN001", "User A", 10000);
        SavingsAccount savings = new SavingsAccount("SAV001", "User B", 10000, 4.0);
        CurrentAccount current = new CurrentAccount("CUR001", "User C", 10000, 50000);
        FixedDepositAccount fd = new FixedDepositAccount("FD001", "User D", 100000, 12, 7.0);

        System.out.println("Each account type calculates interest differently:\n");

        System.out.println("General Account Interest: Rs." + general.calculateInterest());
        System.out.println("Savings Account Interest: Rs." + savings.calculateInterest());
        System.out.println("Current Account Interest (OD charge): Rs." + current.calculateInterest());
        System.out.println("Fixed Deposit Interest: Rs." + fd.calculateInterest());

        System.out.println("\n--- Withdrawal Limit Comparison ---\n");

        System.out.println("General Withdrawal Limit: Rs." + general.getWithdrawalLimit());
        System.out.println("Savings Withdrawal Limit: Rs." + savings.getWithdrawalLimit() + " (after min balance)");
        System.out.println("Current Withdrawal Limit: Rs." + current.getWithdrawalLimit() + " (includes OD)");
        System.out.println("FD Withdrawal Limit: Rs." + fd.getWithdrawalLimit() + " (locked until maturity)");

        System.out.println("\n--- Account Category Comparison ---\n");

        System.out.println("General: " + general.getAccountCategory());
        System.out.println("Savings: " + savings.getAccountCategory());
        System.out.println("Current: " + current.getAccountCategory());
        System.out.println("FD: " + fd.getAccountCategory());
    }

    /**
     * Demo 3: Parent reference can hold any child object
     */
    static void demo3_ParentReferenceChildObject() {
        // Parent reference, child objects
        BankAccount[] accounts = new BankAccount[4];

        accounts[0] = new BankAccount("GEN001", "User A", 10000);
        accounts[1] = new SavingsAccount("SAV001", "User B", 20000, 4.0);
        accounts[2] = new CurrentAccount("CUR001", "User C", 30000, 50000);
        accounts[3] = new FixedDepositAccount("FD001", "User D", 100000, 12, 7.0);

        System.out.println("Processing all accounts with same loop:\n");

        for (BankAccount account : accounts) {
            System.out.println("Account: " + account.getAccountNumber());
            System.out.println("  Type: " + account.getAccountType());
            System.out.println("  Category: " + account.getAccountCategory());
            System.out.println("  Balance: Rs." + account.getBalance());
            System.out.println("  Interest: Rs." + account.calculateInterest());
            System.out.println("  Withdrawal Limit: Rs." + account.getWithdrawalLimit());
            System.out.println();
        }

        System.out.println("--- Depositing Rs.5000 to ALL accounts ---\n");

        for (BankAccount account : accounts) {
            System.out.println(account.getAccountType() + " Account:");
            account.deposit(5000);  // Each handles differently!
            System.out.println();
        }
    }

    /**
     * Demo 4: Polymorphic payment services
     */
    static void demo4_PolymorphicPayments() {
        // Parent reference, child objects
        PaymentService[] paymentServices = new PaymentService[4];

        paymentServices[0] = new UPIPayment();
        paymentServices[1] = new IMPSPayment();
        paymentServices[2] = new NEFTPayment();
        paymentServices[3] = new RTGSPayment();

        System.out.println("Comparing payment services:\n");

        for (PaymentService service : paymentServices) {
            System.out.println(service.getServiceName() + ":");
            System.out.println("  Min Amount: Rs." + service.getMinAmount());
            System.out.println("  Max Amount: Rs." + (service.getMaxAmount() == Double.MAX_VALUE ? "No Limit" : service.getMaxAmount()));
            System.out.println("  Settlement: " + service.getSettlementTime());
            System.out.println("  24x7: " + (service.isAvailable24x7() ? "Yes" : "No"));
            System.out.println();
        }

        System.out.println("--- Processing Rs.50,000 via each channel ---\n");

        double amount = 50000;
        for (PaymentService service : paymentServices) {
            service.processPayment("ACC001", "ACC002", amount);
            System.out.println();
        }
    }

    /**
     * Demo 5: Transaction Processor - Real-world polymorphism
     */
    static void demo5_TransactionProcessor() {
        TransactionProcessor processor = new TransactionProcessor();

        // Create accounts
        SavingsAccount savings = new SavingsAccount("SAV001", "Ramesh", 50000, 4.0);
        CurrentAccount current = new CurrentAccount("CUR001", "ABC Corp", 100000, 50000);

        // Transfer between different account types
        processor.transfer(savings, current, 20000);

        // Create account array
        BankAccount[] allAccounts = {
                new SavingsAccount("SAV002", "User A", 25000, 4.0),
                new CurrentAccount("CUR002", "User B", 50000, 30000),
                new FixedDepositAccount("FD001", "User C", 100000, 12, 7.0)
        };

        // Display all accounts
        processor.displayAllAccounts(allAccounts);

        // Calculate total interest
        System.out.println("\n--- Interest Calculation ---");
        double totalInterest = processor.calculateTotalInterest(allAccounts);
        System.out.println("Total Interest: Rs." + String.format("%.2f", totalInterest));

        // Month-end batch processing
        processor.processMonthEndBatch(allAccounts);

        // Payment recommendation
        processor.recommendPaymentMethod(5000);
        processor.recommendPaymentMethod(150000);
        processor.recommendPaymentMethod(500000);

        // Compare payment services
        processor.comparePaymentServices(75000);
    }

    /**
     * Demo 6: Dynamic Method Dispatch
     */
    static void demo6_DynamicMethodDispatch() {
        System.out.println("Dynamic Method Dispatch - Method called based on actual object type:\n");

        // Reference type is BankAccount, but actual objects vary
        BankAccount account;

        // Runtime decision - could be based on user input, config, etc.
        String accountType = "SAVINGS";  // This could come from user input

        switch (accountType) {
            case "SAVINGS":
                account = new SavingsAccount("SAV001", "User", 10000, 4.0);
                break;
            case "CURRENT":
                account = new CurrentAccount("CUR001", "User", 10000, 50000);
                break;
            case "FD":
                account = new FixedDepositAccount("FD001", "User", 100000, 12, 7.0);
                break;
            default:
                account = new BankAccount("GEN001", "User", 10000);
        }

        // Same method call - behavior depends on actual object
        System.out.println("Account type decided at runtime: " + accountType);
        System.out.println("Calling account.processMonthEnd()...\n");
        account.processMonthEnd();  // Calls the correct version based on actual type!

        // Payment service selection at runtime
        System.out.println("\n--- Payment Service Selection at Runtime ---\n");

        double[] amounts = {5000, 75000, 300000};

        for (double amount : amounts) {
            PaymentService service;

            // Dynamic selection based on amount
            if (amount >= 200000) {
                service = new RTGSPayment();
            } else if (amount > 100000) {
                service = new NEFTPayment();
            } else {
                service = new UPIPayment();
            }

            System.out.println("Amount: Rs." + amount + " → Using: " + service.getServiceName());
            System.out.println("Settlement: " + service.getSettlementTime());
            System.out.println("Charges: Rs." + service.calculateCharges(amount));
            System.out.println();
        }
    }
}