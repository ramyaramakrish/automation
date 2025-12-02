package com.npci.level6;

import java.time.LocalDate;

/**
 * Level 6: Demo - Composition & Aggregation
 *
 * Run this file to see all Level 6 concepts in action.
 */
public class Level6Demo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     LEVEL 6: COMPOSITION & AGGREGATION               ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Demo 1: Understanding Composition
        System.out.println("\n▶ DEMO 1: Composition (Strong HAS-A)");
        System.out.println("─────────────────────────────────────────────\n");
        demo1_Composition();

        // Demo 2: Understanding Aggregation
        System.out.println("\n▶ DEMO 2: Aggregation (Weak HAS-A)");
        System.out.println("─────────────────────────────────────────────\n");
        demo2_Aggregation();

        // Demo 3: Complete Banking System
        System.out.println("\n▶ DEMO 3: Complete Banking System");
        System.out.println("─────────────────────────────────────────────\n");
        demo3_CompleteBankingSystem();

        // Demo 4: Lifecycle Demonstration
        System.out.println("\n▶ DEMO 4: Object Lifecycle (Composition vs Aggregation)");
        System.out.println("─────────────────────────────────────────────\n");
        demo4_LifecycleDemonstration();

        // Demo 5: Transaction History (Composition)
        System.out.println("\n▶ DEMO 5: Transaction History (Composition Example)");
        System.out.println("─────────────────────────────────────────────\n");
        demo5_TransactionHistory();

        // Demo 6: Comparison Summary
        System.out.println("\n▶ DEMO 6: Composition vs Aggregation Summary");
        System.out.println("─────────────────────────────────────────────\n");
        demo6_ComparisonSummary();
    }

    /**
     * Demo 1: Composition - Objects that are PART OF another
     */
    static void demo1_Composition() {
        System.out.println("COMPOSITION = Strong ownership. Child cannot exist without parent.\n");

        // Create a Customer - Address and ContactInfo are COMPOSED
        System.out.println("Creating Customer (Address & ContactInfo are COMPOSED internally)...\n");

        Customer customer = new Customer(
                "CUST001",
                "Ramesh",
                "Kumar",
                LocalDate.of(1990, 5, 15),
                "ABCDE1234F",
                "9876543210",
                "ramesh@email.com",
                "123, MG Road",
                "Mumbai",
                "Maharashtra",
                "400001"
        );

        // The Address and ContactInfo were created INSIDE the Customer constructor
        // They are PART OF the customer

        System.out.println("\nComposed objects created internally:");
        System.out.println("1. Address (created inside Customer constructor)");
        customer.getPermanentAddress().displayAddress();

        System.out.println("\n2. ContactInfo (created inside Customer constructor)");
        customer.getContactInfo().displayContactInfo();

        System.out.println("\n--- Key Point ---");
        System.out.println("If Customer is deleted, Address and ContactInfo are also gone.");
        System.out.println("They don't make sense without the Customer.");

        // Add correspondence address (another COMPOSITION)
        System.out.println("\nAdding Correspondence Address (another composed object)...");
        customer.setCorrespondenceAddress(
                "456, Business Park",
                "Building A, Floor 5",
                "Bangalore",
                "Karnataka",
                "560001"
        );

        customer.displayProfile();
    }

    /**
     * Demo 2: Aggregation - Objects that are REFERENCED
     */
    static void demo2_Aggregation() {
        System.out.println("AGGREGATION = Weak ownership. Child can exist independently.\n");

        // First, create a Branch (which will be aggregated)
        System.out.println("Creating Branch first (independent entity)...");
        Branch branch = new Branch(
                "MUM001",
                "Mumbai Main Branch",
                "NPCI0MUM001",
                "100, Fort Area",
                "Mumbai",
                "Maharashtra",
                "400001",
                "022-12345678",
                "mumbai.main@npcibank.com"
        );

        // Now create Customer (also independent)
        System.out.println("\nCreating Customer (independent entity)...");
        Customer customer = new Customer(
                "CUST002",
                "Priya",
                "Sharma",
                LocalDate.of(1985, 8, 20),
                "XYZAB5678K",
                "9988776655",
                "priya@email.com",
                "789, Link Road",
                "Mumbai",
                "Maharashtra",
                "400050"
        );

        // Create Account - it AGGREGATES both Branch and Customer
        System.out.println("\nCreating Account (AGGREGATES Branch and Customer)...");
        BankAccount account = new BankAccount(
                "SAV001",
                "Savings",
                25000,
                customer.getCustomerId(),
                branch
        );

        // Link account to customer (AGGREGATION)
        customer.linkAccount(account);

        System.out.println("\n--- Aggregation Relationships ---");
        System.out.println("Account " + account.getAccountNumber() + " AGGREGATES:");
        System.out.println("  - Branch: " + branch.getBranchName() + " (exists independently)");
        System.out.println("  - Customer: " + customer.getFullName() + " (exists independently)");

        System.out.println("\n--- Key Point ---");
        System.out.println("If Account is deleted:");
        System.out.println("  - Branch still exists (can have other accounts)");
        System.out.println("  - Customer still exists (can have other accounts)");

        // Demonstrate independence
        System.out.println("\n--- Demonstrating Independence ---");
        System.out.println("Unlinking account from customer...");
        customer.unlinkAccount(account);

        System.out.println("Customer still exists: " + customer.getFullName());
        System.out.println("Account still exists: " + account.getAccountNumber());
        System.out.println("Branch still exists: " + branch.getBranchName());
    }

    /**
     * Demo 3: Complete Banking System with all relationships
     */
    static void demo3_CompleteBankingSystem() {
        System.out.println("Building a complete banking system...\n");

        // Create Bank (top-level entity)
        Bank bank = new Bank("NPCI", "NPCI Bank Ltd", "Mumbai");

        // Create Branches (COMPOSED by Bank)
        System.out.println("\n--- Creating Branches (COMPOSITION) ---");
        Branch mumbaiBranch = bank.createBranch(
                "MUM001", "Mumbai Main Branch",
                "100, Fort Area", "Mumbai", "Maharashtra", "400001",
                "022-11111111", "mumbai@npcibank.com"
        );

        Branch delhiBranch = bank.createBranch(
                "DEL001", "Delhi Main Branch",
                "200, Connaught Place", "Delhi", "Delhi", "110001",
                "011-22222222", "delhi@npcibank.com"
        );

        // Create Customers (AGGREGATED by Bank)
        System.out.println("\n--- Creating Customers (AGGREGATION) ---");
        Customer customer1 = new Customer(
                "CUST101", "Ramesh", "Kumar", LocalDate.of(1990, 1, 15), "ABCD1234E",
                "9876543210", "ramesh@email.com",
                "123, MG Road", "Mumbai", "Maharashtra", "400001"
        );
        bank.registerCustomer(customer1);

        Customer customer2 = new Customer(
                "CUST102", "Priya", "Sharma", LocalDate.of(1988, 6, 20), "EFGH5678K",
                "9876543211", "priya@email.com",
                "456, CP Street", "Delhi", "Delhi", "110001"
        );
        bank.registerCustomer(customer2);

        // Open Accounts (AGGREGATED by Bank, linked to Customer and Branch)
        System.out.println("\n--- Opening Accounts (AGGREGATION) ---");
        BankAccount acc1 = bank.openAccount("CUST101", "MUM001", "Savings", 50000);
        BankAccount acc2 = bank.openAccount("CUST101", "MUM001", "Current", 100000);
        BankAccount acc3 = bank.openAccount("CUST102", "DEL001", "Savings", 75000);

        // Perform transactions (Transactions are COMPOSED in Account)
        System.out.println("\n--- Performing Transactions (Creates COMPOSED Transactions) ---");
        acc1.deposit(10000, "Cash Deposit");
        acc1.withdraw(5000, "ATM Withdrawal");
        acc1.transfer(acc3, 15000, "Friend Payment");

        acc2.deposit(50000, "Business Receipt");
        acc3.deposit(25000, "Salary Credit");

        // Display bank summary
        bank.displayBankSummary();

        // Display relationship diagram
        bank.displayRelationshipDiagram();

        // Display customer profile
        System.out.println("\n--- Customer 1 Profile ---");
        customer1.displayProfile();

        // Display account mini statement
        System.out.println("\n--- Account 1 Mini Statement ---");
        acc1.displayMiniStatement(5);
    }

    /**
     * Demo 4: Lifecycle differences
     */
    static void demo4_LifecycleDemonstration() {
        System.out.println("Demonstrating lifecycle differences...\n");

        System.out.println("COMPOSITION Lifecycle:");
        System.out.println("─────────────────────");
        System.out.println("1. Parent CREATES child internally");
        System.out.println("2. Child LIVES as long as parent lives");
        System.out.println("3. Parent DESTROYS child when parent is destroyed");
        System.out.println("4. Child has NO meaning without parent");

        System.out.println("\nExample: Transaction inside BankAccount");
        System.out.println("  - Transaction created when deposit/withdraw happens");
        System.out.println("  - Transaction belongs to ONE account only");
        System.out.println("  - If account is deleted, transactions are deleted too");

        System.out.println("\n\nAGGREGATION Lifecycle:");
        System.out.println("────────────────────");
        System.out.println("1. Child EXISTS independently");
        System.out.println("2. Parent RECEIVES reference to existing child");
        System.out.println("3. Child SURVIVES even if parent is destroyed");
        System.out.println("4. Child CAN be shared between multiple parents");

        System.out.println("\nExample: Customer linked to BankAccount");
        System.out.println("  - Customer exists before account is created");
        System.out.println("  - Customer can have multiple accounts");
        System.out.println("  - If account is closed, customer still exists");
        System.out.println("  - Customer can open new accounts");

        // Practical demonstration
        System.out.println("\n\n--- Practical Demonstration ---\n");

        // Create independent entities
        Branch branch = new Branch("BR001", "Test Branch", "TEST0BR001",
                "Address", "City", "State", "123456", "1234567890", "test@test.com");

        Customer customer = new Customer("CU001", "Test", "User", LocalDate.now(), "TEST1234A",
                "9999999999", "test@test.com", "Address", "City", "State", "123456");

        // Create account
        BankAccount account = new BankAccount("ACC001", "Savings", 10000, "CU001", branch);
        customer.linkAccount(account);

        System.out.println("Initial State:");
        System.out.println("  Customer: " + customer.getFullName() + " with " + customer.getAccountCount() + " accounts");
        System.out.println("  Account: " + account.getAccountNumber() + " with " + account.getTransactionCount() + " transactions");

        // Add transactions (COMPOSITION - created inside account)
        account.deposit(5000, "Test deposit 1");
        account.deposit(3000, "Test deposit 2");

        System.out.println("\nAfter Transactions:");
        System.out.println("  Account has " + account.getTransactionCount() + " transactions (COMPOSED)");

        // Unlink account from customer (AGGREGATION - customer survives)
        customer.unlinkAccount(account);

        System.out.println("\nAfter Unlinking Account:");
        System.out.println("  Customer still exists: " + customer.getFullName());
        System.out.println("  Customer accounts: " + customer.getAccountCount());
        System.out.println("  Account still exists: " + account.getAccountNumber());
        System.out.println("  Account transactions still exist: " + account.getTransactionCount());

        System.out.println("\n--- Key Observation ---");
        System.out.println("Customer and Account can exist independently (AGGREGATION)");
        System.out.println("But Transactions ONLY exist within Account (COMPOSITION)");
    }

    /**
     * Demo 5: Transaction history showing composition
     */
    static void demo5_TransactionHistory() {
        System.out.println("Transactions are COMPOSED inside Account.\n");

        // Create entities
        Branch branch = new Branch("BR001", "Demo Branch", "TEST0BR001",
                "123 Demo Street", "Mumbai", "Maharashtra", "400001",
                "022-12345678", "demo@bank.com");

        BankAccount account = new BankAccount("SAV001", "Savings", 50000, "CUST001", branch);

        // Perform various transactions
        System.out.println("Performing transactions...\n");
        account.deposit(10000, "Initial Cash Deposit");
        account.deposit(25000, "Salary Credit - ABC Corp");
        account.withdraw(5000, "ATM Withdrawal - Mall ATM");
        account.deposit(15000, "UPI Credit from Priya");
        account.withdraw(8000, "Bill Payment - Electricity");
        account.deposit(3000, "Cashback Reward");
        account.withdraw(2000, "Online Shopping");

        // Display mini statement
        account.displayMiniStatement(10);

        // Show transaction details for one transaction
        System.out.println("\n--- Detailed Transaction View ---");
        Transaction lastTxn = account.getTransactions().get(account.getTransactionCount() - 1);
        lastTxn.displayFullDetails();

        System.out.println("\n--- COMPOSITION Insight ---");
        System.out.println("Each Transaction contains TransactionDetails (COMPOSED)");
        System.out.println("Transaction is created INSIDE account.deposit()/withdraw()");
        System.out.println("Transactions cannot exist without their BankAccount");
        System.out.println("TransactionDetails cannot exist without their Transaction");
        System.out.println("This is a chain of COMPOSITION relationships");
    }

    /**
     * Demo 6: Summary and comparison
     */
    static void demo6_ComparisonSummary() {
        System.out.println("╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║          COMPOSITION vs AGGREGATION COMPARISON                         ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Aspect            │ Composition           │ Aggregation               ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Relationship      │ Strong HAS-A (owns)   │ Weak HAS-A (uses)         ║");
        System.out.println("║ Ownership         │ Parent owns child     │ Parent references child   ║");
        System.out.println("║ Lifecycle         │ Same as parent        │ Independent               ║");
        System.out.println("║ Creation          │ Inside parent         │ Outside, passed to parent ║");
        System.out.println("║ Destruction       │ With parent           │ Survives parent           ║");
        System.out.println("║ Sharing           │ Cannot share          │ Can be shared             ║");
        System.out.println("║ UML Symbol        │ Filled diamond (◆)    │ Empty diamond (◇)         ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                         BANKING EXAMPLES                               ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ COMPOSITION Examples:                                                  ║");
        System.out.println("║   • Customer ◆── Address (address is part of customer)                ║");
        System.out.println("║   • Customer ◆── ContactInfo (contact is part of customer)            ║");
        System.out.println("║   • Account ◆── Transaction (transaction is part of account)          ║");
        System.out.println("║   • Transaction ◆── TransactionDetails (details part of txn)          ║");
        System.out.println("║   • Bank ◆── Branch (branch is part of bank)                          ║");
        System.out.println("║   • Branch ◆── Address (address is part of branch)                    ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ AGGREGATION Examples:                                                  ║");
        System.out.println("║   • Customer ◇── Account (customer uses account)                      ║");
        System.out.println("║   • Account ◇── Branch (account references branch)                    ║");
        System.out.println("║   • Account ◇── Customer (account references customer)                ║");
        System.out.println("║   • Bank ◇── Customer (bank manages customers)                        ║");
        System.out.println("║   • Branch ◇── AccountNumbers (branch tracks accounts)                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝");

        System.out.println("\n--- Code Pattern Recognition ---\n");

        System.out.println("COMPOSITION Pattern:");
        System.out.println("┌────────────────────────────────────────────┐");
        System.out.println("│ class Parent {                             │");
        System.out.println("│     private Child child;  // owned         │");
        System.out.println("│                                            │");
        System.out.println("│     Parent() {                             │");
        System.out.println("│         // Created internally              │");
        System.out.println("│         this.child = new Child();          │");
        System.out.println("│     }                                      │");
        System.out.println("│ }                                          │");
        System.out.println("└────────────────────────────────────────────┘");

        System.out.println("\nAGGREGATION Pattern:");
        System.out.println("┌────────────────────────────────────────────┐");
        System.out.println("│ class Parent {                             │");
        System.out.println("│     private Child child;  // referenced    │");
        System.out.println("│                                            │");
        System.out.println("│     Parent(Child existingChild) {          │");
        System.out.println("│         // Passed from outside             │");
        System.out.println("│         this.child = existingChild;        │");
        System.out.println("│     }                                      │");
        System.out.println("│                                            │");
        System.out.println("│     void setChild(Child c) {               │");
        System.out.println("│         this.child = c;                    │");
        System.out.println("│     }                                      │");
        System.out.println("│ }                                          │");
        System.out.println("└────────────────────────────────────────────┘");

        System.out.println("\n--- When to Use ---\n");

        System.out.println("Use COMPOSITION when:");
        System.out.println("  ✓ Child is meaningless without parent");
        System.out.println("  ✓ Child's lifecycle is controlled by parent");
        System.out.println("  ✓ Child should not be shared");
        System.out.println("  ✓ Example: Transaction in Account, Address in Customer");

        System.out.println("\nUse AGGREGATION when:");
        System.out.println("  ✓ Child can exist independently");
        System.out.println("  ✓ Child can be shared across parents");
        System.out.println("  ✓ Relationship can change at runtime");
        System.out.println("  ✓ Example: Customer has Accounts, Account at Branch");

        System.out.println("\n--- Remember ---");
        System.out.println("\"COMPOSITION over INHERITANCE\" - prefer HAS-A over IS-A for flexibility!");
    }
}