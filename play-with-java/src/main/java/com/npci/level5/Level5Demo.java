package com.npci.level5;

/**
 * Level 5: Demo - Abstraction
 *
 * Run this file to see all Level 5 concepts in action.
 */
public class Level5Demo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     LEVEL 5: ABSTRACTION                             ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Demo 1: Abstract Class
        System.out.println("\n▶ DEMO 1: Abstract Class (PaymentService)");
        System.out.println("─────────────────────────────────────────────\n");
        demo1_AbstractClass();

        // Demo 2: Interface
        System.out.println("\n▶ DEMO 2: Interface (NPCIPaymentStandard)");
        System.out.println("─────────────────────────────────────────────\n");
        demo2_Interface();

        // Demo 3: Multiple Interface Implementation
        System.out.println("\n▶ DEMO 3: Multiple Interface Implementation");
        System.out.println("─────────────────────────────────────────────\n");
        demo3_MultipleInterfaces();

        // Demo 4: Interface Default and Static Methods
        System.out.println("\n▶ DEMO 4: Interface Default and Static Methods");
        System.out.println("─────────────────────────────────────────────\n");
        demo4_DefaultStaticMethods();

        // Demo 5: Programming to Interface
        System.out.println("\n▶ DEMO 5: Programming to Interface");
        System.out.println("─────────────────────────────────────────────\n");
        demo5_ProgrammingToInterface();

        // Demo 6: Template Method Pattern
        System.out.println("\n▶ DEMO 6: Template Method Pattern");
        System.out.println("─────────────────────────────────────────────\n");
        demo6_TemplateMethod();

        // Demo 7: Abstract Class vs Interface
        System.out.println("\n▶ DEMO 7: Abstract Class vs Interface Comparison");
        System.out.println("─────────────────────────────────────────────\n");
        demo7_Comparison();
    }

    /**
     * Demo 1: Abstract Class - cannot instantiate, provides partial implementation
     */
    static void demo1_AbstractClass() {
        // PaymentService service = new PaymentService();  // ERROR! Cannot instantiate abstract class

        // Must use concrete implementations
        PaymentService upi = new UPIPayment();
        PaymentService imps = new IMPSPayment();
        PaymentService neft = new NEFTPayment();
        PaymentService rtgs = new RTGSPayment();

        System.out.println("Created payment services using abstract class reference:\n");

        // Display limits (abstract method - each implements differently)
        upi.displayServiceLimits();

        System.out.println();

        rtgs.displayServiceLimits();

        // Using concrete methods from abstract class
        System.out.println("\nUsing concrete methods from abstract class:");
        System.out.println("UPI Transaction ID: " + upi.generateTransactionId());
        System.out.println("IMPS Transaction ID: " + imps.generateTransactionId());
    }

    /**
     * Demo 2: Interface - defines contract that classes must follow
     */
    static void demo2_Interface() {
        // Interface reference, concrete implementation
        NPCIPaymentStandard upiChannel = new UPIPayment();
        NPCIPaymentStandard impsChannel = new IMPSPayment();

        System.out.println("Using NPCIPaymentStandard interface:\n");

        // All implementing classes MUST have these methods
        String txnId = upiChannel.initiateTransaction("user1@upi", "user2@upi", 5000);
        System.out.println("Initiated: " + txnId);

        upiChannel.validateTransaction(txnId);
        upiChannel.executeTransaction(txnId);

        System.out.println("Status: " + upiChannel.getTransactionStatus(txnId));
        System.out.println("Details: " + upiChannel.getTransactionDetails(txnId));

        // Interface constants
        System.out.println("\nInterface Constants:");
        System.out.println("NPCI Version: " + NPCIPaymentStandard.NPCI_VERSION);
        System.out.println("Country Code: " + NPCIPaymentStandard.NPCI_COUNTRY_CODE);
        System.out.println("Timeout: " + NPCIPaymentStandard.TRANSACTION_TIMEOUT_SECONDS + " seconds");
    }

    /**
     * Demo 3: Class implementing multiple interfaces
     */
    static void demo3_MultipleInterfaces() {
        UPIPayment upi = new UPIPayment();

        System.out.println("UPIPayment implements multiple interfaces:\n");

        // As PaymentService (abstract class)
        System.out.println("1. As PaymentService:");
        System.out.println("   Service Name: " + upi.getServiceName());

        // As NPCIPaymentStandard (interface)
        System.out.println("\n2. As NPCIPaymentStandard:");
        System.out.println("   " + upi.getNPCIVersion());

        // As Auditable (interface)
        System.out.println("\n3. As Auditable:");
        System.out.println("   Entity Type: " + upi.getAuditEntityType());
        upi.recordAuditEvent("TEST", "Testing audit functionality");
        System.out.println(upi.getAuditLog());

        // As Notifiable (interface)
        System.out.println("4. As Notifiable:");
        upi.sendSMS("9876543210", "Test notification");
    }

    /**
     * Demo 4: Default and Static methods in interfaces (Java 8+)
     */
    static void demo4_DefaultStaticMethods() {
        System.out.println("Interface Static Methods (called via interface name):\n");

        // Static methods - called without instance
        System.out.println("Validating IFSC 'SBIN0001234': " +
                NPCIPaymentStandard.validateIFSC("SBIN0001234"));
        System.out.println("Validating IFSC 'INVALID': " +
                NPCIPaymentStandard.validateIFSC("INVALID"));

        System.out.println("\nValidating Account '123456789012': " +
                NPCIPaymentStandard.validateAccountNumber("123456789012"));

        System.out.println("Validating UPI 'user@upi': " +
                NPCIPaymentStandard.validateUPIId("user@upi"));
        System.out.println("Validating UPI 'invalid': " +
                NPCIPaymentStandard.validateUPIId("invalid"));

        System.out.println("\nTransaction Type for 'UPI123': " +
                NPCIPaymentStandard.getTransactionType("UPI123"));
        System.out.println("Transaction Type for 'RTGS456': " +
                NPCIPaymentStandard.getTransactionType("RTGS456"));

        System.out.println("\n--- Default Methods (called on instance) ---\n");

        NPCIPaymentStandard payment = new UPIPayment();

        // Default methods - called on instance
        System.out.println("Formatted TxnID: " + payment.formatTransactionId("TXN123"));
        System.out.println("Within NPCI limits (Rs.5000): " + payment.isWithinNPCILimits(5000));
        System.out.println("Within NPCI limits (Rs.2Cr): " + payment.isWithinNPCILimits(20000000));
        System.out.println("NPCI Reference: " + payment.generateNPCIReference());
    }

    /**
     * Demo 5: Programming to interface - flexible, loosely coupled code
     */
    static void demo5_ProgrammingToInterface() {
        PaymentProcessor processor = new PaymentProcessor();

        // Compare all services
        processor.compareServices();

        // Recommend based on amount
        PaymentService recommended1 = processor.recommendChannel(5000);
        PaymentService recommended2 = processor.recommendChannel(75000);
        PaymentService recommended3 = processor.recommendChannel(500000);

        System.out.println("\n--- Processing with recommended channels ---");

        // Process with interface/abstract reference
        processor.processPayment(recommended1, "user1@upi", "user2@upi", 5000);

        // Using NPCI standard interface
        processor.processNPCITransaction(new IMPSPayment(),
                "123456789012", "987654321098", 25000);
    }

    /**
     * Demo 6: Template Method Pattern - algorithm defined in abstract class
     */
    static void demo6_TemplateMethod() {
        System.out.println("Template Method Pattern Demo:\n");
        System.out.println("executePayment() defines the algorithm structure,");
        System.out.println("but calls abstract methods implemented by children.\n");

        PaymentService upi = new UPIPayment();
        PaymentService rtgs = new RTGSPayment();

        // Same method call, different implementations
        System.out.println("--- UPI Payment ---");
        upi.executePayment("user1@upi", "user2@upi", 5000);

        System.out.println("\n--- RTGS Payment ---");
        rtgs.executePayment("123456789012", "987654321098", 500000);

        // Fails validation (RTGS min is 2 Lakhs)
        System.out.println("\n--- RTGS with small amount (will fail) ---");
        rtgs.executePayment("123456789012", "987654321098", 5000);
    }

    /**
     * Demo 7: Abstract Class vs Interface comparison
     */
    static void demo7_Comparison() {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║       ABSTRACT CLASS vs INTERFACE COMPARISON                  ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Feature          │ Abstract Class     │ Interface             ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Methods          │ Abstract + Concrete│ Abstract (+ default)  ║");
        System.out.println("║ Variables        │ Any type           │ public static final   ║");
        System.out.println("║ Constructor      │ Can have           │ Cannot have           ║");
        System.out.println("║ Inheritance      │ Single (extends)   │ Multiple (implements) ║");
        System.out.println("║ Access Modifiers │ Any                │ public (implicit)     ║");
        System.out.println("║ When to use      │ Shared code +      │ Pure contract/        ║");
        System.out.println("║                  │ partial impl       │ standard definition   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

        System.out.println("\n--- Practical Example ---\n");
        System.out.println("PaymentService (Abstract Class):");
        System.out.println("  ✓ Has common code (generateTransactionId, validateAmount)");
        System.out.println("  ✓ Has abstract methods (processPayment, calculateCharges)");
        System.out.println("  ✓ Children share common implementation");

        System.out.println("\nNPCIPaymentStandard (Interface):");
        System.out.println("  ✓ Defines standard contract for all payment channels");
        System.out.println("  ✓ Multiple unrelated classes can implement");
        System.out.println("  ✓ Ensures interoperability across ecosystem");

        System.out.println("\nAuditable, Notifiable (Interfaces):");
        System.out.println("  ✓ Cross-cutting concerns");
        System.out.println("  ✓ Can be added to any class");
        System.out.println("  ✓ Single class implements multiple interfaces");

        System.out.println("\n--- Bank Account Example ---");

        BankAccount savings = new SavingsAccount("SAV001", "Ramesh", 10000, 4.0);
        BankAccount current = new CurrentAccount("CUR001", "ABC Corp", 50000, 100000);

        // Same abstract reference, different behavior
        BankAccount[] accounts = {savings, current};

        for (BankAccount account : accounts) {
            System.out.println("\nAccount: " + account.getAccountNumber());
            System.out.println("Type: " + account.getAccountType());
            System.out.println("Category: " + account.getAccountCategory());
            System.out.println("Withdrawal Limit: Rs." + account.getWithdrawalLimit());
            account.processMonthEnd();
        }
    }
}