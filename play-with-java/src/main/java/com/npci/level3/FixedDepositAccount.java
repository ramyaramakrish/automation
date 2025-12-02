package com.npci.level3;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Level 3: Inheritance - Child Class (Fixed Deposit Account)
 *
 * FixedDepositAccount IS-A BankAccount
 * - Inherits all features from BankAccount
 * - Cannot deposit after creation
 * - Cannot withdraw before maturity (or with penalty)
 * - Higher interest rates
 */
public class FixedDepositAccount extends BankAccount {

    // ═══════════════════════════════════════════════════════════════
    // ADDITIONAL FIELDS
    // ═══════════════════════════════════════════════════════════════

    private double interestRate;
    private int tenureMonths;
    private LocalDate startDate;
    private LocalDate maturityDate;
    private boolean isMatured;
    private double maturityAmount;
    private double prematureWithdrawalPenalty;  // Percentage

    // ═══════════════════════════════════════════════════════════════
    // CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════════

    public FixedDepositAccount(String accountNumber, String holderName, double principal,
                               int tenureMonths, double interestRate) {
        super(accountNumber, holderName, principal);

        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.startDate = LocalDate.now();
        this.maturityDate = startDate.plusMonths(tenureMonths);
        this.isMatured = false;
        this.prematureWithdrawalPenalty = 1.0;  // 1% penalty
        this.accountType = "Fixed Deposit";

        // Calculate maturity amount (simple interest)
        this.maturityAmount = principal + (principal * interestRate * tenureMonths / 1200);

        System.out.println("[FixedDepositAccount] FD created for " + tenureMonths + " months @ " + interestRate + "%");
        System.out.println("  Maturity Date: " + maturityDate);
        System.out.println("  Maturity Amount: Rs." + String.format("%.2f", maturityAmount));
    }

    // ═══════════════════════════════════════════════════════════════
    // GETTERS
    // ═══════════════════════════════════════════════════════════════

    public double getInterestRate() {
        return interestRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public boolean isMatured() {
        // Auto-check maturity
        if (!isMatured && LocalDate.now().isAfter(maturityDate)) {
            matureFD();
        }
        return isMatured;
    }

    public double getMaturityAmount() {
        return maturityAmount;
    }

    public long getDaysRemaining() {
        if (isMatured) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), maturityDate);
    }

    // ═══════════════════════════════════════════════════════════════
    // OVERRIDDEN METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Override deposit - Not allowed after FD creation
     */
    @Override
    public void deposit(double amount) {
        System.out.println("Error: Cannot deposit to Fixed Deposit after creation!");
        System.out.println("Please create a new FD for additional deposits.");
    }

    /**
     * Override withdraw - Only after maturity or with penalty
     */
    @Override
    public void withdraw(double amount) {
        if (!isActive) {
            System.out.println("Error: Account is inactive!");
            return;
        }

        if (!isMatured) {
            System.out.println("Error: FD has not matured yet!");
            System.out.println("Maturity Date: " + maturityDate);
            System.out.println("Days Remaining: " + getDaysRemaining());
            System.out.println("Use prematureWithdraw() for early withdrawal with penalty.");
            return;
        }

        // After maturity, allow withdrawal
        if (amount > balance) {
            System.out.println("Error: Amount exceeds available balance!");
            return;
        }

        balance -= amount;
        System.out.println("Withdrawn: Rs." + amount);
        System.out.println("Remaining Balance: Rs." + balance);
    }

    /**
     * Override calculateInterest
     */
    @Override
    public double calculateInterest() {
        return balance * interestRate * tenureMonths / 1200;
    }

    /**
     * Override displayInfo
     */
    @Override
    public void displayInfo() {
        super.displayInfo();

        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│      FIXED DEPOSIT DETAILS              │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("  Principal Amount  : Rs." + balance);
        System.out.println("  Interest Rate     : " + interestRate + "% p.a.");
        System.out.println("  Tenure            : " + tenureMonths + " months");
        System.out.println("  Start Date        : " + startDate);
        System.out.println("  Maturity Date     : " + maturityDate);
        System.out.println("  Maturity Amount   : Rs." + String.format("%.2f", maturityAmount));
        System.out.println("  Status            : " + (isMatured ? "MATURED" : "Active"));
        if (!isMatured) {
            System.out.println("  Days Remaining    : " + getDaysRemaining());
        }
        System.out.println("  Interest Earned   : Rs." + String.format("%.2f", calculateInterest()));
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ═══════════════════════════════════════════════════════════════
    // NEW METHODS - Specific to Fixed Deposit
    // ═══════════════════════════════════════════════════════════════

    /**
     * Mature the FD (convert to maturity amount)
     */
    public void matureFD() {
        if (isMatured) {
            System.out.println("FD is already matured!");
            return;
        }

        double interest = calculateInterest();
        balance = maturityAmount;
        isMatured = true;

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║      FD MATURED SUCCESSFULLY!         ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Interest Earned : Rs." + String.format("%.2f", interest));
        System.out.println("  Maturity Amount : Rs." + String.format("%.2f", maturityAmount));
        System.out.println("╚═══════════════════════════════════════╝");
    }

    /**
     * Premature withdrawal with penalty
     */
    public void prematureWithdraw() {
        if (isMatured) {
            System.out.println("FD is already matured. Use regular withdraw().");
            return;
        }

        // Calculate interest till date
        long monthsCompleted = ChronoUnit.MONTHS.between(startDate, LocalDate.now());
        double interestTillDate = balance * interestRate * monthsCompleted / 1200;

        // Apply penalty
        double penalty = interestTillDate * prematureWithdrawalPenalty / 100;
        double finalAmount = balance + interestTillDate - penalty;

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║     PREMATURE WITHDRAWAL              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("  Principal         : Rs." + balance);
        System.out.println("  Months Completed  : " + monthsCompleted);
        System.out.println("  Interest Earned   : Rs." + String.format("%.2f", interestTillDate));
        System.out.println("  Penalty (" + prematureWithdrawalPenalty + "%)    : Rs." + String.format("%.2f", penalty));
        System.out.println("  Final Amount      : Rs." + String.format("%.2f", finalAmount));
        System.out.println("╚═══════════════════════════════════════╝");

        balance = 0;
        isMatured = true;
        isActive = false;
    }

    /**
     * Renew FD after maturity
     */
    public void renewFD(int newTenureMonths, double newInterestRate) {
        if (!isMatured) {
            System.out.println("Error: FD has not matured yet. Cannot renew.");
            return;
        }

        double principal = balance;
        this.interestRate = newInterestRate;
        this.tenureMonths = newTenureMonths;
        this.startDate = LocalDate.now();
        this.maturityDate = startDate.plusMonths(newTenureMonths);
        this.isMatured = false;
        this.maturityAmount = principal + (principal * newInterestRate * newTenureMonths / 1200);

        System.out.println("FD Renewed Successfully!");
        System.out.println("New Tenure: " + newTenureMonths + " months @ " + newInterestRate + "%");
        System.out.println("New Maturity Date: " + maturityDate);
        System.out.println("New Maturity Amount: Rs." + String.format("%.2f", maturityAmount));
    }
}