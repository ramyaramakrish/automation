package com.npci.level4;

import java.time.LocalDate;

/**
 * Level 4: Polymorphism - Child Class (Fixed Deposit Account)
 *
 * Demonstrates Method Overriding:
 * - deposit() - blocks deposits after creation
 * - withdraw() - blocks withdrawal before maturity
 * - calculateInterest() - FD interest calculation
 * - processMonthEnd() - checks maturity
 */
public class FixedDepositAccount extends BankAccount {

    private double interestRate;
    private int tenureMonths;
    private LocalDate maturityDate;
    private boolean isMatured;

    public FixedDepositAccount(String accountNumber, String holderName, double principal,
                               int tenureMonths, double interestRate) {
        super(accountNumber, holderName, principal);
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.maturityDate = LocalDate.now().plusMonths(tenureMonths);
        this.isMatured = false;
        this.accountType = "Fixed Deposit";
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public double getInterestRate() { return interestRate; }
    public int getTenureMonths() { return tenureMonths; }
    public LocalDate getMaturityDate() { return maturityDate; }
    public boolean isMatured() { return isMatured; }

    public double getMaturityAmount() {
        return balance + calculateInterest();
    }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Override deposit - not allowed for FD
     */
    @Override
    public void deposit(double amount) {
        System.out.println("[FD] Error: Cannot deposit to Fixed Deposit after creation!");
        System.out.println("Create a new FD for additional deposits.");
    }

    /**
     * Override withdraw - only after maturity
     */
    @Override
    public void withdraw(double amount) {
        if (!isMatured) {
            System.out.println("[FD] Error: FD has not matured yet!");
            System.out.println("Maturity Date: " + maturityDate);
            System.out.println("Use matureFD() to mature early with penalty.");
            return;
        }

        if (amount > balance) {
            System.out.println("[FD] Error: Amount exceeds available balance!");
            return;
        }

        balance -= amount;
        System.out.println("[FD] Withdrawn: Rs." + amount);
        System.out.println("Remaining: Rs." + balance);
    }

    /**
     * Override calculateInterest - FD interest
     */
    @Override
    public double calculateInterest() {
        return balance * interestRate * tenureMonths / 1200;
    }

    /**
     * Override getWithdrawalLimit - 0 before maturity
     */
    @Override
    public double getWithdrawalLimit() {
        return isMatured ? balance : 0;
    }

    /**
     * Override processMonthEnd - check maturity
     */
    @Override
    public void processMonthEnd() {
        System.out.println("[FD] Month end processing...");

        if (!isMatured && LocalDate.now().isAfter(maturityDate)) {
            matureFD();
        } else if (!isMatured) {
            long daysRemaining = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), maturityDate);
            System.out.println("FD Active. Days to maturity: " + daysRemaining);
        } else {
            System.out.println("FD already matured. Balance: Rs." + balance);
        }
    }

    /**
     * Override getAccountCategory
     */
    @Override
    public String getAccountCategory() {
        return "Term Deposits";
    }

    /**
     * Override displayInfo
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│       FIXED DEPOSIT DETAILS             │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Principal       : Rs." + balance);
        System.out.println("  Interest Rate   : " + interestRate + "% p.a.");
        System.out.println("  Tenure          : " + tenureMonths + " months");
        System.out.println("  Maturity Date   : " + maturityDate);
        System.out.println("  Maturity Amount : Rs." + String.format("%.2f", getMaturityAmount()));
        System.out.println("  Status          : " + (isMatured ? "MATURED" : "Active"));
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ═══════════════════════════════════════════════════════════════
    // SPECIFIC METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Mature the FD
     */
    public void matureFD() {
        if (isMatured) {
            System.out.println("FD is already matured!");
            return;
        }

        double interest = calculateInterest();
        balance += interest;
        isMatured = true;

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║       FD MATURED SUCCESSFULLY!        ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Interest Earned : Rs." + String.format("%.2f", interest));
        System.out.println("  Maturity Amount : Rs." + String.format("%.2f", balance));
        System.out.println("╚═══════════════════════════════════════╝");
    }
}