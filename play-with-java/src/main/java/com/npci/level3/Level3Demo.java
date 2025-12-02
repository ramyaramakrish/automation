package com.npci.level3;

/**
 * Level 3: Demo - Inheritance
 *
 * Run this file to see all Level 3 concepts in action.
 */
public class Level3Demo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     LEVEL 3: INHERITANCE                             ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Demo 1: Parent Class
        System.out.println("\n▶ DEMO 1: Parent Class (BankAccount)");
        System.out.println("─────────────────────────────────────────────\n");
        demo1_ParentClass();

        // Demo 2: Savings Account
        System.out.println("\n▶ DEMO 2: Savings Account (Child Class)");
        System.out.println("─────────────────────────────────────────────\n");
        demo2_SavingsAccount();

        // Demo 3: Current Account
        System.out.println("\n▶ DEMO 3: Current Account (Child Class)");
        System.out.println("─────────────────────────────────────────────\n");
        demo3_CurrentAccount();

        // Demo 4: Fixed Deposit
        System.out.println("\n▶ DEMO 4: Fixed Deposit Account (Child Class)");
        System.out.println("─────────────────────────────────────────────\n");
        demo4_FixedDeposit();

        // Demo 5: Salary Account
        System.out.println("\n▶ DEMO 5: Salary Account (Multi-Level Inheritance)");
        System.out.println("─────────────────────────────────────────────\n");
        demo5_SalaryAccount();

        // Demo 6: Polymorphism Preview
        System.out.println("\n▶ DEMO 6: Polymorphism Preview (Parent Reference)");
        System.out.println("─────────────────────────────────────────────\n");
        demo6_PolymorphismPreview();
    }

    /**
     * Demo 1: Basic BankAccount (Parent Class)
     */
    static void demo1_ParentClass() {
        BankAccount account = new BankAccount("GEN001", "Ramesh Kumar", 10000);

        account.displayInfo();

        System.out.println("\nPerforming transactions:");
        account.deposit(5000);
        account.withdraw(3000);
        account.checkBalance();

        System.out.println("\nInterest (base class): Rs." + account.calculateInterest());
    }

    /**
     * Demo 2: SavingsAccount with minimum balance and interest
     */
    static void demo2_SavingsAccount() {
        SavingsAccount savings = new SavingsAccount("SAV001", "Priya Sharma", 10000, 4.0);

        savings.displayInfo();

        // Test minimum balance protection
        System.out.println("\nTrying to withdraw Rs.9500 (would break minimum balance):");
        savings.withdraw(9500);

        // Valid withdrawal
        System.out.println("\nValid withdrawal of Rs.5000:");
        savings.withdraw(5000);

        // Test free withdrawals limit
        System.out.println("\nTesting withdrawal limits:");
        for (int i = 0; i < 6; i++) {
            System.out.println("\nWithdrawal #" + (i + 1) + ":");
            savings.withdraw(100);
        }

        // Add interest
        System.out.println("\nAdding quarterly interest:");
        savings.addQuarterlyInterest();

        savings.displayInfo();
    }

    /**
     * Demo 3: CurrentAccount with overdraft facility
     */
    static void demo3_CurrentAccount() {
        CurrentAccount current = new CurrentAccount("CUR001", "ABC Traders", 50000, 100000);
        current.setBusinessName("ABC Trading Co.");
        current.setGstNumber("27AABCU9603R1ZM");

        current.displayInfo();

        // Normal withdrawal
        System.out.println("\nNormal withdrawal of Rs.30000:");
        current.withdraw(30000);

        // Withdrawal using overdraft
        System.out.println("\nWithdrawal of Rs.50000 (uses overdraft):");
        current.withdraw(50000);

        // Check overdraft status
        System.out.println("\nOverdraft Used: Rs." + current.getOverdraftUsed());
        System.out.println("Available Overdraft: Rs." + current.getAvailableOverdraft());

        // Deposit to repay overdraft
        System.out.println("\nDepositing Rs.40000 (repays overdraft):");
        current.deposit(40000);

        // Charge overdraft interest
        System.out.println("\nCharging monthly overdraft interest:");
        current.chargeOverdraftInterest();

        current.displayInfo();
    }

    /**
     * Demo 4: FixedDepositAccount
     */
    static void demo4_FixedDeposit() {
        FixedDepositAccount fd = new FixedDepositAccount("FD001", "Amit Singh", 100000, 12, 7.0);

        fd.displayInfo();

        // Try to deposit (should fail)
        System.out.println("\nTrying to deposit (not allowed for FD):");
        fd.deposit(10000);

        // Try to withdraw before maturity
        System.out.println("\nTrying to withdraw before maturity:");
        fd.withdraw(50000);

        // Check days remaining
        System.out.println("\nDays until maturity: " + fd.getDaysRemaining());

        // Simulate maturity
        System.out.println("\nSimulating FD maturity...");
        fd.matureFD();

        // Now withdrawal works
        System.out.println("\nWithdrawing Rs.50000 after maturity:");
        fd.withdraw(50000);

        fd.displayInfo();
    }

    /**
     * Demo 5: SalaryAccount (inherits from SavingsAccount)
     */
    static void demo5_SalaryAccount() {
        SalaryAccount salary = new SalaryAccount("SAL001", "Neha Gupta", "TechCorp India", "EMP12345", 75000);

        salary.displayInfo();

        // Credit salary
        System.out.println("\nCrediting monthly salary:");
        salary.creditSalary(75000, "TECH001");

        // Withdrawal (no minimum balance)
        System.out.println("\nWithdrawing Rs.70000 (no minimum balance for salary account):");
        salary.withdraw(70000);

        // Check loan eligibility
        System.out.println("\nLoan Eligibility: Rs." + salary.getMaxLoanEligibility());

        // Add interest (inherited from SavingsAccount)
        System.out.println("\nAdding quarterly interest:");
        salary.addQuarterlyInterest();

        salary.displayInfo();
    }

    /**
     * Demo 6: Polymorphism Preview - Parent reference holding child objects
     */
    static void demo6_PolymorphismPreview() {
        // Parent reference can hold any child object
        BankAccount[] accounts = new BankAccount[4];

        accounts[0] = new BankAccount("GEN001", "User A", 10000);
        accounts[1] = new SavingsAccount("SAV001", "User B", 20000, 4.0);
        accounts[2] = new CurrentAccount("CUR001", "User C", 30000, 50000);
        accounts[3] = new FixedDepositAccount("FD001", "User D", 100000, 12, 7.0);

        System.out.println("Processing all accounts with parent reference:\n");

        // Same method call, different behavior (Polymorphism!)
        for (BankAccount account : accounts) {
            System.out.println("Account: " + account.getAccountNumber());
            System.out.println("Type: " + account.getAccountType());
            System.out.println("Balance: Rs." + account.getBalance());
            System.out.println("Interest: Rs." + account.calculateInterest());
            System.out.println("────────────────────────────────────");
        }

        // Deposit to all accounts
        System.out.println("\nDepositing Rs.5000 to all accounts:");
        for (BankAccount account : accounts) {
            System.out.println("\n" + account.getAccountType() + " Account:");
            account.deposit(5000);  // Each account handles differently!
        }
    }
}