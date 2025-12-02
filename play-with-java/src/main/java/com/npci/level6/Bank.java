package com.npci.level6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Level 6: Composition & Aggregation - Bank
 *
 * Bank is the top-level entity demonstrating:
 * - COMPOSITION with Branches (branches are PART OF bank)
 * - AGGREGATION with Customers (customers can exist across banks)
 * - AGGREGATION with Accounts (accounts are managed, not owned)
 */
public class Bank {

    private String bankCode;
    private String bankName;
    private String headquartersCity;

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION - Branches are PART OF Bank
    // When Bank is dissolved, branches cease to exist
    // ═══════════════════════════════════════════════════════════════
    private List<Branch> branches;

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION - Bank manages Customers and Accounts
    // These entities can potentially exist independently (e.g., customer
    // could be customer of multiple banks in real world)
    // ═══════════════════════════════════════════════════════════════
    private Map<String, Customer> customers;  // customerId -> Customer
    private Map<String, BankAccount> accounts; // accountNumber -> BankAccount

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public Bank(String bankCode, String bankName, String headquartersCity) {
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.headquartersCity = headquartersCity;

        // COMPOSITION - Initialize branch list (owned by bank)
        this.branches = new ArrayList<>();

        // AGGREGATION - Initialize customer and account maps
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();

        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║            BANK INITIALIZED                   ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("  Bank Code : " + bankCode);
        System.out.println("  Bank Name : " + bankName);
        System.out.println("  HQ City   : " + headquartersCity);
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getBankCode() { return bankCode; }
    public String getBankName() { return bankName; }
    public String getHeadquartersCity() { return headquartersCity; }

    public int getBranchCount() { return branches.size(); }
    public int getCustomerCount() { return customers.size(); }
    public int getAccountCount() { return accounts.size(); }

    public List<Branch> getBranches() { return new ArrayList<>(branches); }

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION - Branch Management
    // Bank OWNS branches - creates and manages them
    // ═══════════════════════════════════════════════════════════════

    /**
     * Create a new branch (COMPOSITION)
     * Branch is created BY the bank and is PART OF the bank
     */
    public Branch createBranch(String branchCode, String branchName,
                               String addressLine1, String city, String state, String pincode,
                               String phone, String email) {
        // Generate IFSC code
        String ifscCode = bankCode + "0" + branchCode;

        // Create branch (COMPOSITION - branch owned by bank)
        Branch branch = new Branch(branchCode, branchName, ifscCode,
                addressLine1, city, state, pincode, phone, email);

        branches.add(branch);

        System.out.println("[Bank] Branch created: " + branchName + " (" + ifscCode + ")");
        return branch;
    }

    /**
     * Get branch by code
     */
    public Branch getBranch(String branchCode) {
        for (Branch branch : branches) {
            if (branch.getBranchCode().equals(branchCode)) {
                return branch;
            }
        }
        return null;
    }

    /**
     * Close a branch (COMPOSITION - destroying part of bank)
     */
    public void closeBranch(String branchCode) {
        Branch branch = getBranch(branchCode);
        if (branch != null) {
            if (branch.getAccountCount() > 0) {
                System.out.println("[Bank] Cannot close branch - has " +
                        branch.getAccountCount() + " active accounts!");
                return;
            }
            branch.setActive(false);
            System.out.println("[Bank] Branch " + branchCode + " closed");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION - Customer Management
    // Bank references customers but doesn't strictly own them
    // ═══════════════════════════════════════════════════════════════

    /**
     * Register a customer (AGGREGATION)
     */
    public void registerCustomer(Customer customer) {
        if (!customers.containsKey(customer.getCustomerId())) {
            customers.put(customer.getCustomerId(), customer);
            System.out.println("[Bank] Customer registered: " + customer.getFullName());
        }
    }

    /**
     * Get customer by ID
     */
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    /**
     * Remove customer (AGGREGATION - customer can exist elsewhere)
     */
    public void removeCustomer(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            if (customer.getAccountCount() > 0) {
                System.out.println("[Bank] Cannot remove customer - has active accounts!");
                return;
            }
            customers.remove(customerId);
            System.out.println("[Bank] Customer removed: " + customerId);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION - Account Management
    // Bank manages accounts through branches
    // ═══════════════════════════════════════════════════════════════

    /**
     * Open a new account
     */
    public BankAccount openAccount(String customerId, String branchCode,
                                   String accountType, double initialDeposit) {
        // Validate customer
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("[Bank] Error: Customer not found!");
            return null;
        }

        // Validate branch
        Branch branch = getBranch(branchCode);
        if (branch == null) {
            System.out.println("[Bank] Error: Branch not found!");
            return null;
        }

        // Generate account number
        String accountNumber = generateAccountNumber(branchCode, accountType);

        // Create account
        BankAccount account = new BankAccount(accountNumber, accountType, initialDeposit,
                customerId, branch);

        // Register account in bank's map
        accounts.put(accountNumber, account);

        // Link account to customer (AGGREGATION)
        customer.linkAccount(account);

        System.out.println("[Bank] Account opened: " + accountNumber + " for " + customer.getFullName());

        return account;
    }

    /**
     * Get account by number
     */
    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /**
     * Close account
     */
    public void closeAccount(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("[Bank] Account not found!");
            return;
        }

        if (account.getBalance() > 0) {
            System.out.println("[Bank] Please withdraw balance before closing!");
            return;
        }

        // Remove from customer's list
        Customer customer = customers.get(account.getCustomerId());
        if (customer != null) {
            customer.unlinkAccount(account);
        }

        // Remove from branch
        Branch branch = account.getHomeBranch();
        if (branch != null) {
            branch.removeAccount(accountNumber);
        }

        accounts.remove(accountNumber);
        System.out.println("[Bank] Account " + accountNumber + " closed");
    }

    /**
     * Generate account number
     */
    private String generateAccountNumber(String branchCode, String accountType) {
        String typeCode = "GEN";
        if (accountType.equals("Savings")) typeCode = "SAV";
        else if (accountType.equals("Current")) typeCode = "CUR";
        else if (accountType.equals("FD")) typeCode = "FD";

        return branchCode + typeCode + String.format("%06d", accounts.size() + 1);
    }

    // ═══════════════════════════════════════════════════════════════
    // REPORTING
    // ═══════════════════════════════════════════════════════════════

    /**
     * Get total deposits
     */
    public double getTotalDeposits() {
        double total = 0;
        for (BankAccount account : accounts.values()) {
            total += account.getBalance();
        }
        return total;
    }

    /**
     * Display bank summary
     */
    public void displayBankSummary() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                    BANK SUMMARY                           ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("  Bank Code      : " + bankCode);
        System.out.println("  Bank Name      : " + bankName);
        System.out.println("  Headquarters   : " + headquartersCity);
        System.out.println("├───────────────────────────────────────────────────────────┤");
        System.out.println("  BRANCHES (Composed): " + branches.size());
        for (Branch branch : branches) {
            System.out.println("    • " + branch);
        }
        System.out.println("├───────────────────────────────────────────────────────────┤");
        System.out.println("  CUSTOMERS (Aggregated): " + customers.size());
        for (Customer customer : customers.values()) {
            customer.displayMiniProfile();
        }
        System.out.println("├───────────────────────────────────────────────────────────┤");
        System.out.println("  ACCOUNTS (Aggregated): " + accounts.size());
        for (BankAccount account : accounts.values()) {
            System.out.println("    • " + account);
        }
        System.out.println("├───────────────────────────────────────────────────────────┤");
        System.out.println("  TOTAL DEPOSITS : Rs." + String.format("%,.2f", getTotalDeposits()));
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

    /**
     * Display relationship diagram
     */
    public void displayRelationshipDiagram() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║              COMPOSITION vs AGGREGATION DIAGRAM                   ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                   ║");
        System.out.println("║    BANK (" + bankName + ")                                        ");
        System.out.println("║    │                                                              ║");
        System.out.println("║    ├── COMPOSES ──┬── Branch (owned, dies with bank)             ║");
        System.out.println("║    │              │   └── COMPOSES ── Address                    ║");
        System.out.println("║    │              │   └── COMPOSES ── ContactInfo                ║");
        System.out.println("║    │              │   └── AGGREGATES ── AccountNumbers (refs)    ║");
        System.out.println("║    │              │                                              ║");
        System.out.println("║    ├── AGGREGATES ┬── Customer (independent)                     ║");
        System.out.println("║    │              │   └── COMPOSES ── Address                    ║");
        System.out.println("║    │              │   └── COMPOSES ── ContactInfo                ║");
        System.out.println("║    │              │   └── AGGREGATES ── Accounts (refs)          ║");
        System.out.println("║    │              │                                              ║");
        System.out.println("║    └── AGGREGATES ┬── Account (independent)                      ║");
        System.out.println("║                   │   └── COMPOSES ── Transactions               ║");
        System.out.println("║                   │       └── COMPOSES ── TransactionDetails     ║");
        System.out.println("║                   │   └── AGGREGATES ── Branch (ref)             ║");
        System.out.println("║                   │   └── AGGREGATES ── Customer (ref)           ║");
        System.out.println("║                                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
    }
}