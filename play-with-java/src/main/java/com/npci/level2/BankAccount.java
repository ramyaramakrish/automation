package com.npci.level2;

/**
 * Level 2: Encapsulation - Protecting Account Data
 *
 * Key Concepts:
 * - Encapsulation = Wrapping data and methods, hiding internal details
 * - private = Accessible only within same class (like bank vault)
 * - public = Accessible from anywhere (like bank counter)
 * - protected = Accessible within package and subclasses
 * - default (no modifier) = Accessible within package only
 * - Getters = Methods to READ private data (like balance enquiry)
 * - Setters = Methods to WRITE/UPDATE private data with validation
 * - Why Encapsulation? = Prevents invalid data, controls access, security
 */
public class BankAccount {

    // ═══════════════════════════════════════════════════════════════
    // PRIVATE FIELDS - Cannot be accessed directly from outside
    // ═══════════════════════════════════════════════════════════════

    private String accountNumber;
    private String holderName;
    private String email;
    private String phone;
    private double balance;
    private String pin;
    private boolean isActive;
    private int failedPinAttempts;

    // ═══════════════════════════════════════════════════════════════
    // CONSTANTS - Business rules
    // ═══════════════════════════════════════════════════════════════

    private static final double MINIMUM_BALANCE = 1000.0;
    private static final double MAXIMUM_WITHDRAWAL_LIMIT = 50000.0;
    private static final double MAXIMUM_DEPOSIT_LIMIT = 200000.0;
    private static final int MAX_PIN_ATTEMPTS = 3;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR - With validation
    // ═══════════════════════════════════════════════════════════════

    public BankAccount(String accountNumber, String holderName, double initialBalance, String pin) {
        // Validate account number
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }

        // Validate holder name
        if (holderName == null || holderName.trim().length() < 3) {
            throw new IllegalArgumentException("Holder name must be at least 3 characters");
        }

        // Validate initial balance
        if (initialBalance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Initial balance must be at least Rs." + MINIMUM_BALANCE);
        }

        // Validate PIN
        if (pin == null || pin.length() != 4 || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits");
        }

        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
        this.pin = pin;
        this.isActive = true;
        this.failedPinAttempts = 0;
        this.email = "";
        this.phone = "";
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS - Read-only access to private fields
    // ═══════════════════════════════════════════════════════════════

    /**
     * Get account number (read-only, no setter provided)
     */
    public String getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * Get holder name
     */
    public String getHolderName() {
        return this.holderName;
    }

    /**
     * Get email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Get current balance
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Check if account is active
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Get minimum balance requirement
     */
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    // NOTE: No getter for PIN - sensitive data should never be exposed!

    // ═══════════════════════════════════════════════════════════════
    // SETTERS - Controlled write access with validation
    // ═══════════════════════════════════════════════════════════════

    /**
     * Update holder name with validation
     */
    public void setHolderName(String newName) {
        if (newName == null || newName.trim().length() < 3) {
            System.out.println("Error: Name must be at least 3 characters!");
            return;
        }
        this.holderName = newName.trim();
        System.out.println("Holder name updated successfully.");
    }

    /**
     * Update email with validation
     */
    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            System.out.println("Error: Invalid email format!");
            return;
        }
        this.email = email;
        System.out.println("Email updated successfully.");
    }

    /**
     * Update phone with validation
     */
    public void setPhone(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) {
            System.out.println("Error: Phone must be exactly 10 digits!");
            return;
        }
        this.phone = phone;
        System.out.println("Phone updated successfully.");
    }

    // NOTE: No setter for accountNumber - should never change after creation
    // NOTE: No setter for balance - can only change via deposit/withdraw
    // NOTE: No setter for PIN here - separate secure method below

    // ═══════════════════════════════════════════════════════════════
    // BUSINESS METHODS - Controlled operations with validation
    // ═══════════════════════════════════════════════════════════════

    /**
     * Deposit money into account
     */
    public void deposit(double amount) {
        // Check if account is active
        if (!this.isActive) {
            System.out.println("Error: Account is inactive. Cannot deposit.");
            return;
        }

        // Validate amount
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be positive!");
            return;
        }

        // Check maximum limit
        if (amount > MAXIMUM_DEPOSIT_LIMIT) {
            System.out.println("Error: Single deposit cannot exceed Rs." + MAXIMUM_DEPOSIT_LIMIT);
            return;
        }

        // Perform deposit
        this.balance += amount;
        System.out.println("Deposited: Rs." + amount);
        System.out.println("New Balance: Rs." + this.balance);
    }

    /**
     * Withdraw money - requires PIN verification
     */
    public void withdraw(double amount, String enteredPin) {
        // Check if account is active
        if (!this.isActive) {
            System.out.println("Error: Account is inactive. Cannot withdraw.");
            return;
        }

        // Verify PIN
        if (!verifyPin(enteredPin)) {
            return;  // PIN verification failed
        }

        // Validate amount
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive!");
            return;
        }

        // Check maximum limit
        if (amount > MAXIMUM_WITHDRAWAL_LIMIT) {
            System.out.println("Error: Single withdrawal cannot exceed Rs." + MAXIMUM_WITHDRAWAL_LIMIT);
            return;
        }

        // Check minimum balance
        if (this.balance - amount < MINIMUM_BALANCE) {
            System.out.println("Error: Insufficient balance!");
            System.out.println("Available for withdrawal: Rs." + (this.balance - MINIMUM_BALANCE));
            return;
        }

        // Perform withdrawal
        this.balance -= amount;
        System.out.println("Withdrawn: Rs." + amount);
        System.out.println("New Balance: Rs." + this.balance);
    }

    /**
     * Transfer money to another account - requires PIN
     */
    public void transfer(BankAccount toAccount, double amount, String enteredPin) {
        System.out.println("\n--- Fund Transfer ---");

        // Validate destination account
        if (toAccount == null) {
            System.out.println("Error: Invalid destination account!");
            return;
        }

        // Check self-transfer
        if (toAccount.getAccountNumber().equals(this.accountNumber)) {
            System.out.println("Error: Cannot transfer to same account!");
            return;
        }

        // Check if destination is active
        if (!toAccount.isActive()) {
            System.out.println("Error: Destination account is inactive!");
            return;
        }

        // Check if source is active
        if (!this.isActive) {
            System.out.println("Error: Your account is inactive!");
            return;
        }

        // Verify PIN
        if (!verifyPin(enteredPin)) {
            return;
        }

        // Validate amount
        if (amount <= 0) {
            System.out.println("Error: Transfer amount must be positive!");
            return;
        }

        // Check balance
        if (this.balance - amount < MINIMUM_BALANCE) {
            System.out.println("Error: Insufficient balance for transfer!");
            return;
        }

        // Perform transfer
        this.balance -= amount;
        toAccount.balance += amount;  // Can access because same class

        System.out.println("Transfer Successful!");
        System.out.println("Amount: Rs." + amount);
        System.out.println("To Account: " + toAccount.getAccountNumber());
        System.out.println("Your New Balance: Rs." + this.balance);
    }

    /**
     * Change PIN - requires old PIN verification
     */
    public void changePin(String oldPin, String newPin) {
        System.out.println("\n--- Change PIN ---");

        // Check if account is active
        if (!this.isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }

        // Verify old PIN
        if (!verifyPin(oldPin)) {
            return;
        }

        // Validate new PIN
        if (newPin == null || newPin.length() != 4 || !newPin.matches("\\d{4}")) {
            System.out.println("Error: New PIN must be exactly 4 digits!");
            return;
        }

        // Check if new PIN is same as old
        if (newPin.equals(oldPin)) {
            System.out.println("Error: New PIN cannot be same as old PIN!");
            return;
        }

        // Update PIN
        this.pin = newPin;
        System.out.println("PIN changed successfully!");
    }

    /**
     * Deactivate account - requires PIN
     */
    public void deactivateAccount(String enteredPin) {
        System.out.println("\n--- Account Deactivation ---");

        if (!this.isActive) {
            System.out.println("Account is already inactive!");
            return;
        }

        if (!verifyPin(enteredPin)) {
            return;
        }

        this.isActive = false;
        System.out.println("Account " + this.accountNumber + " has been deactivated.");
    }

    /**
     * Reactivate account - for bank staff (no PIN required)
     */
    public void reactivateAccount() {
        if (this.isActive) {
            System.out.println("Account is already active!");
            return;
        }

        this.isActive = true;
        this.failedPinAttempts = 0;
        System.out.println("Account " + this.accountNumber + " has been reactivated.");
    }

    // ═══════════════════════════════════════════════════════════════
    // PRIVATE HELPER METHODS - Internal use only
    // ═══════════════════════════════════════════════════════════════

    /**
     * Verify PIN - private method, not accessible from outside
     */
    private boolean verifyPin(String enteredPin) {
        // Check if account is locked
        if (this.failedPinAttempts >= MAX_PIN_ATTEMPTS) {
            System.out.println("Error: Account is locked due to too many failed attempts!");
            System.out.println("Please contact bank to unlock.");
            this.isActive = false;
            return false;
        }

        // Verify PIN
        if (!this.pin.equals(enteredPin)) {
            this.failedPinAttempts++;
            int remaining = MAX_PIN_ATTEMPTS - this.failedPinAttempts;
            System.out.println("Error: Invalid PIN!");
            if (remaining > 0) {
                System.out.println("Attempts remaining: " + remaining);
            } else {
                System.out.println("Account has been locked!");
                this.isActive = false;
            }
            return false;
        }

        // Reset failed attempts on successful verification
        this.failedPinAttempts = 0;
        return true;
    }

    /**
     * Mask account number for display (show only last 4 digits)
     */
    private String getMaskedAccountNumber() {
        if (this.accountNumber.length() <= 4) {
            return this.accountNumber;
        }
        String masked = "XXXX" + this.accountNumber.substring(this.accountNumber.length() - 4);
        return masked;
    }

    // ═══════════════════════════════════════════════════════════════
    // DISPLAY METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Display account summary (safe - no sensitive data)
     */
    public void displaySummary() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║         ACCOUNT SUMMARY               ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Account No  : " + this.accountNumber);
        System.out.println("  Holder Name : " + this.holderName);
        System.out.println("  Balance     : Rs." + this.balance);
        System.out.println("  Status      : " + (this.isActive ? "Active" : "Inactive"));
        System.out.println("╚═══════════════════════════════════════╝");
    }

    /**
     * Display mini statement (masked account number)
     */
    public void displayMiniStatement() {
        System.out.println("┌───────────────────────────────────────┐");
        System.out.println("│         MINI STATEMENT                │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("  Account     : " + getMaskedAccountNumber());
        System.out.println("  Holder      : " + this.holderName);
        System.out.println("  Balance     : Rs." + this.balance);
        System.out.println("  Min Balance : Rs." + MINIMUM_BALANCE);
        System.out.println("  Available   : Rs." + (this.balance - MINIMUM_BALANCE));
        System.out.println("└───────────────────────────────────────┘");
    }

    /**
     * Display full profile
     */
    public void displayProfile() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║         ACCOUNT PROFILE               ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Account No  : " + this.accountNumber);
        System.out.println("  Holder Name : " + this.holderName);
        System.out.println("  Email       : " + (this.email.isEmpty() ? "Not Set" : this.email));
        System.out.println("  Phone       : " + (this.phone.isEmpty() ? "Not Set" : this.phone));
        System.out.println("  Balance     : Rs." + this.balance);
        System.out.println("  Status      : " + (this.isActive ? "Active" : "Inactive"));
        System.out.println("╚═══════════════════════════════════════╝");
    }
}