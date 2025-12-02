package com.npci.level1;

/**
 * Level 1: Demo - Basic Class & Objects
 *
 * Run this file to see all Level 1 concepts in action.
 */
public class Level1Demo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     LEVEL 1: BASIC CLASS & OBJECTS                   ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Demo 1: Creating Objects with Different Constructors
        System.out.println("\n▶ DEMO 1: Creating Objects with Constructors");
        System.out.println("─────────────────────────────────────────────\n");
        demo1_Constructors();

        // Demo 2: Using Methods
        System.out.println("\n▶ DEMO 2: Using Methods (Deposit, Withdraw)");
        System.out.println("─────────────────────────────────────────────\n");
        demo2_Methods();

        // Demo 3: Multiple Objects
        System.out.println("\n▶ DEMO 3: Multiple Objects");
        System.out.println("─────────────────────────────────────────────\n");
        demo3_MultipleObjects();

        // Demo 4: Fund Transfer Between Accounts
        System.out.println("\n▶ DEMO 4: Fund Transfer Between Accounts");
        System.out.println("─────────────────────────────────────────────\n");
        demo4_FundTransfer();

        // Demo 5: Object References
        System.out.println("\n▶ DEMO 5: Object References");
        System.out.println("─────────────────────────────────────────────\n");
        demo5_ObjectReferences();
    }

    /**
     * Demo 1: Different ways to create objects using constructors
     */
    static void demo1_Constructors() {
        // Using default constructor
        BankAccount account1 = new BankAccount();
        System.out.println("Account 1 (Default Constructor):");
        account1.displayInfo();

        // Using 3-parameter constructor
        BankAccount account2 = new BankAccount("SAV001", "Ramesh Kumar", 5000.0);
        System.out.println("Account 2 (3-param Constructor):");
        account2.displayInfo();

        // Using 4-parameter constructor
        BankAccount account3 = new BankAccount("SAV002", "Priya Sharma", 10000.0, "Mumbai Branch");
        System.out.println("Account 3 (4-param Constructor):");
        account3.displayInfo();
    }

    /**
     * Demo 2: Using deposit and withdraw methods
     */
    static void demo2_Methods() {
        BankAccount account = new BankAccount("SAV003", "Amit Singh", 5000.0);

        System.out.println("Initial State:");
        account.checkBalance();

        System.out.println("\nPerforming Transactions:");
        account.deposit(3000);      // Valid deposit
        account.deposit(-500);      // Invalid deposit
        account.withdraw(2000);     // Valid withdrawal
        account.withdraw(10000);    // Insufficient balance
        account.withdraw(-100);     // Invalid amount

        System.out.println("\nFinal State:");
        account.checkBalance();
    }

    /**
     * Demo 3: Creating and managing multiple account objects
     */
    static void demo3_MultipleObjects() {
        // Creating array of accounts
        BankAccount[] accounts = new BankAccount[3];

        accounts[0] = new BankAccount("SAV101", "Customer A", 15000.0, "Delhi Branch");
        accounts[1] = new BankAccount("SAV102", "Customer B", 25000.0, "Mumbai Branch");
        accounts[2] = new BankAccount("SAV103", "Customer C", 35000.0, "Chennai Branch");

        // Display all accounts
        System.out.println("All Accounts:");
        for (int i = 0; i < accounts.length; i++) {
            accounts[i].displayInfo();
        }

        // Calculate total deposits
        double totalDeposits = 0;
        for (BankAccount acc : accounts) {
            totalDeposits += acc.balance;
        }
        System.out.println("Total Deposits in Bank: Rs." + totalDeposits);
    }

    /**
     * Demo 4: Transferring funds between accounts
     */
    static void demo4_FundTransfer() {
        BankAccount sender = new BankAccount("SAV201", "Ramesh Kumar", 50000.0);
        BankAccount receiver = new BankAccount("SAV202", "Priya Sharma", 10000.0);

        System.out.println("Before Transfer:");
        sender.checkBalance();
        receiver.checkBalance();

        // Successful transfer
        sender.transfer(receiver, 15000);

        // Failed transfer - insufficient balance
        sender.transfer(receiver, 100000);

        System.out.println("\nAfter Transfers:");
        sender.checkBalance();
        receiver.checkBalance();
    }

    /**
     * Demo 5: Understanding object references
     */
    static void demo5_ObjectReferences() {
        // Creating an account
        BankAccount original = new BankAccount("SAV301", "Test User", 10000.0);

        // Reference copy (both point to same object)
        BankAccount reference = original;

        System.out.println("Original balance: Rs." + original.balance);
        System.out.println("Reference balance: Rs." + reference.balance);

        // Modify through reference
        reference.deposit(5000);

        System.out.println("\nAfter deposit through reference:");
        System.out.println("Original balance: Rs." + original.balance);
        System.out.println("Reference balance: Rs." + reference.balance);
        System.out.println("Both show same value because they point to same object!");

        // Check if same object
        System.out.println("\nAre they same object? " + (original == reference));
    }
}