package com.npci.level2;

/**
 * Level 2: Demo - Encapsulation
 *
 * Run this file to see all Level 2 concepts in action.
 */
public class Level2Demo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     LEVEL 2: ENCAPSULATION                           ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Demo 1: Creating Account with Validation
        System.out.println("\n▶ DEMO 1: Constructor Validation");
        System.out.println("─────────────────────────────────────────────\n");
        demo1_ConstructorValidation();

        // Demo 2: Getters and Setters
        System.out.println("\n▶ DEMO 2: Getters and Setters");
        System.out.println("─────────────────────────────────────────────\n");
        demo2_GettersSetters();

        // Demo 3: Controlled Deposit and Withdraw
        System.out.println("\n▶ DEMO 3: Controlled Transactions");
        System.out.println("─────────────────────────────────────────────\n");
        demo3_ControlledTransactions();

        // Demo 4: PIN Verification and Security
        System.out.println("\n▶ DEMO 4: PIN Verification and Security");
        System.out.println("─────────────────────────────────────────────\n");
        demo4_PinSecurity();

        // Demo 5: Fund Transfer with Validation
        System.out.println("\n▶ DEMO 5: Secure Fund Transfer");
        System.out.println("─────────────────────────────────────────────\n");
        demo5_SecureTransfer();

        // Demo 6: Account Status Management
        System.out.println("\n▶ DEMO 6: Account Status Management");
        System.out.println("─────────────────────────────────────────────\n");
        demo6_AccountStatus();
    }

    /**
     * Demo 1: Constructor validation prevents invalid data
     */
    static void demo1_ConstructorValidation() {
        System.out.println("Creating valid account:");
        BankAccount validAccount = new BankAccount("SAV001", "Ramesh Kumar", 5000.0, "1234");
        validAccount.displaySummary();

        System.out.println("\nTrying to create invalid accounts:\n");

        // Try empty account number
        try {
            BankAccount invalid1 = new BankAccount("", "Test User", 5000.0, "1234");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Empty account number: " + e.getMessage());
        }

        // Try short name
        try {
            BankAccount invalid2 = new BankAccount("SAV002", "AB", 5000.0, "1234");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Short name: " + e.getMessage());
        }

        // Try low balance
        try {
            BankAccount invalid3 = new BankAccount("SAV003", "Test User", 500.0, "1234");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Low balance: " + e.getMessage());
        }

        // Try invalid PIN
        try {
            BankAccount invalid4 = new BankAccount("SAV004", "Test User", 5000.0, "12");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Invalid PIN: " + e.getMessage());
        }

        // Try non-numeric PIN
        try {
            BankAccount invalid5 = new BankAccount("SAV005", "Test User", 5000.0, "abcd");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Non-numeric PIN: " + e.getMessage());
        }
    }

    /**
     * Demo 2: Using getters and setters
     */
    static void demo2_GettersSetters() {
        BankAccount account = new BankAccount("SAV010", "Ramesh Kumar", 10000.0, "1234");

        // Using getters (read access)
        System.out.println("Using Getters:");
        System.out.println("  Account Number: " + account.getAccountNumber());
        System.out.println("  Holder Name: " + account.getHolderName());
        System.out.println("  Balance: Rs." + account.getBalance());
        System.out.println("  Is Active: " + account.isActive());
        System.out.println("  Min Balance: Rs." + account.getMinimumBalance());

        // Note: Cannot access PIN - no getter provided!
        // System.out.println(account.pin);  // ERROR - private
        // System.out.println(account.getPin());  // ERROR - method doesn't exist

        // Using setters (write access with validation)
        System.out.println("\nUsing Setters:");

        // Valid updates
        account.setHolderName("Ramesh K. Singh");
        account.setEmail("ramesh@email.com");
        account.setPhone("9876543210");

        // Invalid updates (rejected)
        System.out.println("\nTrying invalid updates:");
        account.setHolderName("AB");           // Too short
        account.setEmail("invalid-email");     // No @ symbol
        account.setPhone("12345");             // Not 10 digits

        // Display updated profile
        System.out.println("\nUpdated Profile:");
        account.displayProfile();

        // Cannot directly modify fields
        // account.balance = 9999999;  // ERROR - private
        // account.accountNumber = "HACKED";  // ERROR - private
    }

    /**
     * Demo 3: Controlled deposit and withdrawal
     */
    static void demo3_ControlledTransactions() {
        BankAccount account = new BankAccount("SAV020", "Priya Sharma", 10000.0, "5678");

        System.out.println("Initial Balance: Rs." + account.getBalance());

        // Valid deposit
        System.out.println("\n--- Valid Deposit ---");
        account.deposit(5000);

        // Invalid deposits
        System.out.println("\n--- Invalid Deposits ---");
        account.deposit(-1000);     // Negative amount
        account.deposit(0);         // Zero amount
        account.deposit(500000);    // Exceeds limit

        // Valid withdrawal
        System.out.println("\n--- Valid Withdrawal ---");
        account.withdraw(3000, "5678");

        // Invalid withdrawals
        System.out.println("\n--- Invalid Withdrawals ---");
        account.withdraw(-500, "5678");     // Negative amount
        account.withdraw(100000, "5678");   // Exceeds limit
        account.withdraw(15000, "5678");    // Would break min balance

        System.out.println("\nFinal Balance: Rs." + account.getBalance());
        account.displayMiniStatement();
    }

    /**
     * Demo 4: PIN verification and account locking
     */
    static void demo4_PinSecurity() {
        BankAccount account = new BankAccount("SAV030", "Amit Singh", 20000.0, "9999");

        System.out.println("Testing PIN Security:\n");

        // Correct PIN
        System.out.println("--- Attempt with correct PIN ---");
        account.withdraw(1000, "9999");

        // Wrong PINs - account will lock after 3 attempts
        System.out.println("\n--- Attempts with wrong PIN ---");
        account.withdraw(1000, "0000");  // Attempt 1
        account.withdraw(1000, "1111");  // Attempt 2
        account.withdraw(1000, "2222");  // Attempt 3 - Account locks!

        // Even correct PIN won't work now
        System.out.println("\n--- Attempt after account locked ---");
        account.withdraw(1000, "9999");

        account.displaySummary();

        // Reactivate account (bank staff action)
        System.out.println("\n--- Bank Reactivates Account ---");
        account.reactivateAccount();
        account.displaySummary();

        // Now it works again
        System.out.println("\n--- After Reactivation ---");
        account.withdraw(1000, "9999");
    }

    /**
     * Demo 5: Secure fund transfer between accounts
     */
    static void demo5_SecureTransfer() {
        BankAccount sender = new BankAccount("SAV040", "Ramesh Kumar", 50000.0, "1234");
        BankAccount receiver = new BankAccount("SAV041", "Priya Sharma", 10000.0, "5678");

        System.out.println("Before Transfer:");
        System.out.println("Sender Balance: Rs." + sender.getBalance());
        System.out.println("Receiver Balance: Rs." + receiver.getBalance());

        // Valid transfer
        System.out.println("\n--- Valid Transfer ---");
        sender.transfer(receiver, 15000, "1234");

        // Invalid transfers
        System.out.println("\n--- Invalid Transfers ---");

        // Wrong PIN
        sender.transfer(receiver, 5000, "0000");

        // Transfer to self
        sender.transfer(sender, 5000, "1234");

        // Transfer to null
        sender.transfer(null, 5000, "1234");

        // Insufficient balance
        sender.transfer(receiver, 100000, "1234");

        System.out.println("\nAfter All Transfers:");
        System.out.println("Sender Balance: Rs." + sender.getBalance());
        System.out.println("Receiver Balance: Rs." + receiver.getBalance());
    }

    /**
     * Demo 6: Account activation/deactivation
     */
    static void demo6_AccountStatus() {
        BankAccount account = new BankAccount("SAV050", "Test User", 15000.0, "1111");

        System.out.println("Account Status: " + (account.isActive() ? "Active" : "Inactive"));

        // Deactivate account
        System.out.println("\n--- Deactivating Account ---");
        account.deactivateAccount("1111");

        // Try operations on inactive account
        System.out.println("\n--- Operations on Inactive Account ---");
        account.deposit(5000);
        account.withdraw(1000, "1111");

        // Reactivate
        System.out.println("\n--- Reactivating Account ---");
        account.reactivateAccount();

        // Now operations work
        System.out.println("\n--- Operations After Reactivation ---");
        account.deposit(5000);
        account.displaySummary();
    }
}