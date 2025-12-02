package com.npci.level6;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 6: Composition & Aggregation - Customer
 *
 * Customer demonstrates both relationships:
 * - COMPOSITION: Customer HAS-A Address, ContactInfo (part of customer)
 * - AGGREGATION: Customer HAS (references) BankAccounts (accounts exist independently)
 */
public class Customer {

    private String customerId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String panNumber;
    private String aadharNumber;
    private String customerType;  // INDIVIDUAL, CORPORATE
    private LocalDate registrationDate;
    private boolean isActive;

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION - These are PART OF Customer
    // Created with Customer, destroyed with Customer
    // ═══════════════════════════════════════════════════════════════
    private Address permanentAddress;
    private Address correspondenceAddress;
    private ContactInfo contactInfo;

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION - Customer references Accounts but doesn't own them
    // Accounts can exist independently (e.g., joint accounts, transferred accounts)
    // ═══════════════════════════════════════════════════════════════
    private List<BankAccount> accounts;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public Customer(String customerId, String firstName, String lastName,
                    LocalDate dateOfBirth, String panNumber,
                    String phone, String email,
                    String addressLine1, String city, String state, String pincode) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.panNumber = panNumber;
        this.customerType = "INDIVIDUAL";
        this.registrationDate = LocalDate.now();
        this.isActive = true;

        // COMPOSITION - Creating objects internally (owned by Customer)
        this.permanentAddress = new Address(addressLine1, city, state, pincode);
        this.correspondenceAddress = null;  // Optional, can be set later
        this.contactInfo = new ContactInfo(phone, email);

        // AGGREGATION - Empty list, accounts linked later
        this.accounts = new ArrayList<>();

        System.out.println("[Customer] Created: " + getFullName() + " (" + customerId + ")");
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getPanNumber() { return panNumber; }
    public String getAadharNumber() { return aadharNumber; }
    public String getCustomerType() { return customerType; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public boolean isActive() { return isActive; }

    // Composed objects
    public Address getPermanentAddress() { return permanentAddress; }
    public Address getCorrespondenceAddress() { return correspondenceAddress; }
    public ContactInfo getContactInfo() { return contactInfo; }

    // Aggregated accounts
    public List<BankAccount> getAccounts() { return new ArrayList<>(accounts); }
    public int getAccountCount() { return accounts.size(); }

    // ═══════════════════════════════════════════════════════════════
    // SETTERS
    // ═══════════════════════════════════════════════════════════════

    public void setAadharNumber(String aadhar) {
        if (aadhar != null && aadhar.matches("\\d{12}")) {
            this.aadharNumber = aadhar;
        }
    }
    public void setCustomerType(String type) { this.customerType = type; }
    public void setActive(boolean active) { this.isActive = active; }

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION METHODS - Managing composed objects
    // ═══════════════════════════════════════════════════════════════

    /**
     * Set correspondence address (COMPOSITION)
     * Creates new Address internally
     */
    public void setCorrespondenceAddress(String addressLine1, String addressLine2,
                                         String city, String state, String pincode) {
        this.correspondenceAddress = new Address(addressLine1, addressLine2,
                city, state, pincode, "CORRESPONDENCE");
        System.out.println("[Customer] Correspondence address added for " + getFullName());
    }

    /**
     * Update permanent address (modify composed object)
     */
    public void updatePermanentAddress(String addressLine1, String city,
                                       String state, String pincode) {
        this.permanentAddress.setAddressLine1(addressLine1);
        this.permanentAddress.setCity(city);
        this.permanentAddress.setState(state);
        this.permanentAddress.setPincode(pincode);
        System.out.println("[Customer] Permanent address updated for " + getFullName());
    }

    /**
     * Update contact info
     */
    public void updateContact(String phone, String email) {
        this.contactInfo.setPrimaryPhone(phone);
        this.contactInfo.setEmail(email);
    }

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION METHODS - Managing accounts
    // ═══════════════════════════════════════════════════════════════

    /**
     * Link account to customer (AGGREGATION)
     * Account is not created here - it exists independently
     */
    public void linkAccount(BankAccount account) {
        if (account != null && !accounts.contains(account)) {
            accounts.add(account);
            System.out.println("[Customer] Account " + account.getAccountNumber() +
                    " linked to " + getFullName());
        }
    }

    /**
     * Unlink account from customer
     * Account continues to exist after unlinking
     */
    public void unlinkAccount(BankAccount account) {
        if (accounts.remove(account)) {
            System.out.println("[Customer] Account " + account.getAccountNumber() +
                    " unlinked from " + getFullName());
        }
    }

    /**
     * Get total balance across all accounts
     */
    public double getTotalBalance() {
        double total = 0;
        for (BankAccount account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    /**
     * Get account by number
     */
    public BankAccount getAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Check if customer has account
     */
    public boolean hasAccount(String accountNumber) {
        return getAccount(accountNumber) != null;
    }

    // ═══════════════════════════════════════════════════════════════
    // DISPLAY METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Display customer profile
     */
    public void displayProfile() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                 CUSTOMER PROFILE                          ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("  Customer ID      : " + customerId);
        System.out.println("  Name             : " + getFullName());
        System.out.println("  Date of Birth    : " + dateOfBirth);
        System.out.println("  PAN Number       : " + panNumber);
        System.out.println("  Aadhar           : " + (aadharNumber != null ? maskAadhar() : "Not Provided"));
        System.out.println("  Customer Type    : " + customerType);
        System.out.println("  Registration Date: " + registrationDate);
        System.out.println("  Status           : " + (isActive ? "Active" : "Inactive"));
        System.out.println("├───────────────────────────────────────────────────────────┤");
        System.out.println("  CONTACT INFO (Composed):");
        contactInfo.displayContactInfo();
        System.out.println("├───────────────────────────────────────────────────────────┤");
        System.out.println("  PERMANENT ADDRESS (Composed):");
        permanentAddress.displayAddress();
        if (correspondenceAddress != null) {
            System.out.println("├───────────────────────────────────────────────────────────┤");
            System.out.println("  CORRESPONDENCE ADDRESS (Composed):");
            correspondenceAddress.displayAddress();
        }
        System.out.println("├───────────────────────────────────────────────────────────┤");
        System.out.println("  LINKED ACCOUNTS (Aggregated): " + accounts.size());
        for (BankAccount account : accounts) {
            System.out.println("    • " + account.getAccountNumber() + " | " +
                    account.getAccountType() + " | Rs." + account.getBalance());
        }
        System.out.println("  TOTAL BALANCE    : Rs." + getTotalBalance());
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

    /**
     * Display mini profile
     */
    public void displayMiniProfile() {
        System.out.println(customerId + " | " + getFullName() + " | Accounts: " +
                accounts.size() + " | Total: Rs." + getTotalBalance());
    }

    private String maskAadhar() {
        if (aadharNumber == null || aadharNumber.length() < 4) return aadharNumber;
        return "XXXX-XXXX-" + aadharNumber.substring(aadharNumber.length() - 4);
    }

    @Override
    public String toString() {
        return customerId + " | " + getFullName() + " | " + customerType;
    }
}