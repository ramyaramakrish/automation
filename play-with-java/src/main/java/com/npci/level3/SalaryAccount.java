package com.npci.level3;

/**
 * Level 3: Inheritance - Child Class (Salary Account)
 *
 * SalaryAccount IS-A BankAccount (extends SavingsAccount)
 * - Special type of Savings Account for salaried employees
 * - Zero minimum balance
 * - Higher free withdrawals
 * - Linked to employer
 */
public class SalaryAccount extends SavingsAccount {

    // ═══════════════════════════════════════════════════════════════
    // ADDITIONAL FIELDS
    // ═══════════════════════════════════════════════════════════════

    private String employerName;
    private String employeeId;
    private double monthlySalary;
    private boolean salaryReceivedThisMonth;

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public SalaryAccount(String accountNumber, String holderName, String employerName,
                         String employeeId, double monthlySalary) {
        // Call parent constructor (SavingsAccount)
        super(accountNumber, holderName, 0, 3.5);  // Zero initial balance, 3.5% interest

        this.employerName = employerName;
        this.employeeId = employeeId;
        this.monthlySalary = monthlySalary;
        this.salaryReceivedThisMonth = false;
        this.accountType = "Salary";

        System.out.println("[SalaryAccount] Salary account created for employee of " + employerName);
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Override withdraw - Zero minimum balance for salary accounts
     */
    @Override
    public void withdraw(double amount) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid withdrawal amount!");
            return;
        }
        if (amount > balance) {
            System.out.println("Error: Insufficient balance!");
            System.out.println("Available: Rs." + balance);
            return;
        }

        // No minimum balance requirement for salary accounts
        balance -= amount;
        System.out.println("Withdrawn: Rs." + amount);
        System.out.println("New Balance: Rs." + balance);
    }

    /**
     * Override displayInfo
     */
    @Override
    public void displayInfo() {
        // Call grandparent's (BankAccount) displayInfo, not parent's
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│          ACCOUNT INFORMATION            │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Account No   : " + accountNumber);
        System.out.println("  Holder Name  : " + holderName);
        System.out.println("  Account Type : " + accountType);
        System.out.println("  Balance      : Rs." + balance);
        System.out.println("  Branch       : " + branch);
        System.out.println("  Status       : " + (isActive ? "Active" : "Inactive"));
        System.out.println("└─────────────────────────────────────────┘");

        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│       SALARY ACCOUNT DETAILS            │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Employer        : " + employerName);
        System.out.println("  Employee ID     : " + employeeId);
        System.out.println("  Monthly Salary  : Rs." + monthlySalary);
        System.out.println("  Interest Rate   : " + getInterestRate() + "%");
        System.out.println("  Salary Status   : " + (salaryReceivedThisMonth ? "Received" : "Pending"));
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ═══════════════════════════════════════════════════════════════
    // NEW METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Credit salary (special deposit from employer)
     */
    public void creditSalary(double amount, String employerCode) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }

        balance += amount;
        salaryReceivedThisMonth = true;

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║       SALARY CREDITED                 ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Employer      : " + employerName);
        System.out.println("  Amount        : Rs." + amount);
        System.out.println("  New Balance   : Rs." + balance);
        System.out.println("╚═══════════════════════════════════════╝");
    }

    /**
     * Update salary
     */
    public void updateSalary(double newSalary) {
        System.out.println("Salary updated from Rs." + this.monthlySalary + " to Rs." + newSalary);
        this.monthlySalary = newSalary;
    }

    /**
     * Reset monthly status
     */
    public void resetMonthlyStatus() {
        this.salaryReceivedThisMonth = false;
        resetMonthlyWithdrawals();
    }

    /**
     * Check if eligible for personal loan based on salary
     */
    public double getMaxLoanEligibility() {
        // Typically 20x monthly salary
        return monthlySalary * 20;
    }
}