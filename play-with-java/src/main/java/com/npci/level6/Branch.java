package com.npci.level6;

import java.util.ArrayList;
import java.util.List;

/**
 * Level 6: Composition & Aggregation - Branch
 *
 * Branch demonstrates both relationships:
 * - COMPOSITION: Branch HAS-A Address (address is PART OF branch)
 * - AGGREGATION: Branch HAS (manages) Accounts (accounts can exist independently)
 */
public class Branch {

    private String branchCode;
    private String branchName;
    private String ifscCode;
    private String branchType;  // MAIN, SUB, EXTENSION

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION - Address is PART OF Branch
    // Created internally, dies with Branch
    // ═══════════════════════════════════════════════════════════════
    private Address branchAddress;
    private ContactInfo branchContact;

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION - Branch manages Accounts but doesn't own them
    // Accounts can exist independently of Branch reference
    // ═══════════════════════════════════════════════════════════════
    private List<String> accountNumbers;  // List of account numbers at this branch

    private String managerName;
    private boolean isActive;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR - Creates composed objects internally
    // ═══════════════════════════════════════════════════════════════

    public Branch(String branchCode, String branchName, String ifscCode,
                  String addressLine1, String city, String state, String pincode,
                  String phone, String email) {
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.ifscCode = ifscCode;
        this.branchType = "SUB";
        this.isActive = true;

        // COMPOSITION - Creating Address internally (owned by Branch)
        this.branchAddress = new Address(addressLine1, city, state, pincode);
        this.branchAddress = new Address(addressLine1, "", city, state, pincode, "OFFICE");

        // COMPOSITION - Creating ContactInfo internally
        this.branchContact = new ContactInfo(phone, email);

        // AGGREGATION - Empty list, accounts added later
        this.accountNumbers = new ArrayList<>();

        System.out.println("[Branch] Created: " + branchName + " (" + branchCode + ")");
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getBranchCode() { return branchCode; }
    public String getBranchName() { return branchName; }
    public String getIfscCode() { return ifscCode; }
    public String getBranchType() { return branchType; }
    public String getManagerName() { return managerName; }
    public boolean isActive() { return isActive; }

    // Return composed objects (read-only access recommended)
    public Address getBranchAddress() { return branchAddress; }
    public ContactInfo getBranchContact() { return branchContact; }

    public int getAccountCount() { return accountNumbers.size(); }
    public List<String> getAccountNumbers() { return new ArrayList<>(accountNumbers); }

    // ═══════════════════════════════════════════════════════════════
    // SETTERS
    // ═══════════════════════════════════════════════════════════════

    public void setBranchType(String type) { this.branchType = type; }
    public void setManagerName(String name) { this.managerName = name; }
    public void setActive(boolean active) { this.isActive = active; }

    // ═══════════════════════════════════════════════════════════════
    // AGGREGATION METHODS - Managing accounts
    // ═══════════════════════════════════════════════════════════════

    /**
     * Register account at this branch
     */
    public void registerAccount(String accountNumber) {
        if (!accountNumbers.contains(accountNumber)) {
            accountNumbers.add(accountNumber);
            System.out.println("[Branch] Account " + accountNumber + " registered at " + branchName);
        }
    }

    /**
     * Remove account from this branch
     */
    public void removeAccount(String accountNumber) {
        if (accountNumbers.remove(accountNumber)) {
            System.out.println("[Branch] Account " + accountNumber + " removed from " + branchName);
        }
    }

    /**
     * Check if account belongs to this branch
     */
    public boolean hasAccount(String accountNumber) {
        return accountNumbers.contains(accountNumber);
    }

    /**
     * Transfer account to another branch
     */
    public void transferAccount(String accountNumber, Branch targetBranch) {
        if (accountNumbers.contains(accountNumber)) {
            accountNumbers.remove(accountNumber);
            targetBranch.registerAccount(accountNumber);
            System.out.println("[Branch] Account " + accountNumber + " transferred from " +
                    branchName + " to " + targetBranch.getBranchName());
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // COMPOSITION METHODS - Managing composed objects
    // ═══════════════════════════════════════════════════════════════

    /**
     * Update branch address (modifies composed object)
     */
    public void updateAddress(String addressLine1, String city, String state, String pincode) {
        this.branchAddress.setAddressLine1(addressLine1);
        this.branchAddress.setCity(city);
        this.branchAddress.setState(state);
        this.branchAddress.setPincode(pincode);
        System.out.println("[Branch] Address updated for " + branchName);
    }

    /**
     * Update branch contact
     */
    public void updateContact(String phone, String email) {
        this.branchContact.setPrimaryPhone(phone);
        this.branchContact.setEmail(email);
        System.out.println("[Branch] Contact updated for " + branchName);
    }

    // ═══════════════════════════════════════════════════════════════
    // DISPLAY METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Display branch info
     */
    public void displayBranchInfo() {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║            BRANCH INFORMATION                 ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("  Branch Code  : " + branchCode);
        System.out.println("  Branch Name  : " + branchName);
        System.out.println("  IFSC Code    : " + ifscCode);
        System.out.println("  Branch Type  : " + branchType);
        System.out.println("  Manager      : " + (managerName != null ? managerName : "Not Assigned"));
        System.out.println("  Status       : " + (isActive ? "Active" : "Inactive"));
        System.out.println("  Accounts     : " + accountNumbers.size());
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("  ADDRESS (Composed):");
        branchAddress.displayAddress();
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("  CONTACT (Composed):");
        branchContact.displayContactInfo();
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return branchCode + " | " + branchName + " | " + ifscCode + " | Accounts: " + accountNumbers.size();
    }
}