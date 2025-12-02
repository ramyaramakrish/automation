package com.npci.level1;

/**
 * Level 1: Basic Class & Objects - Bank Account
 *
 * Key Concepts:
 * - Class = Blueprint (like account opening form template)
 * - Object = Actual instance (like your real account)
 * - Constructor = Special method that runs when account is created
 * - this = Refers to the current object instance
 * - Fields/Attributes = Data stored in object (account number, balance)
 * - Methods = Actions object can perform (deposit, withdraw)
 */
public class BankAccount {

    // Fields (attributes) - data the object holds
    String accountNumber;
    String holderName;
    double balance;
    String branch;

    // Default Constructor - no parameters
    public BankAccount() {
        this.accountNumber = "NOT_ASSIGNED";
        this.holderName = "NOT_ASSIGNED";
        this.balance = 0.0;
        this.branch = "HEAD_OFFICE";
    }

    // Parameterized Constructor - with 3 parameters
    public BankAccount(String accNo, String name, double initialBalance) {
        this.accountNumber = accNo;
        this.holderName = name;
        this.balance = initialBalance;
        this.branch = "HEAD_OFFICE";
    }

    // Parameterized Constructor - with 4 parameters
    public BankAccount(String accNo, String name, double initialBalance, String branch) {
        this.accountNumber = accNo;
        this.holderName = name;
        this.balance = initialBalance;
        this.branch = branch;
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance = this.balance + amount;
            System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
        } else if (amount > this.balance) {
            System.out.println("Insufficient balance! Available: Rs." + this.balance);
        } else {
            this.balance = this.balance - amount;
            System.out.println("Withdrawn Rs." + amount + " | New Balance: Rs." + this.balance);
        }
    }

    // Method to check balance
    public void checkBalance() {
        System.out.println("Account: " + this.accountNumber + " | Balance: Rs." + this.balance);
    }

    // Method to display full account info
    public void displayInfo() {
        System.out.println("================================");
        System.out.println("Account Number : " + this.accountNumber);
        System.out.println("Holder Name    : " + this.holderName);
        System.out.println("Balance        : Rs." + this.balance);
        System.out.println("Branch         : " + this.branch);
        System.out.println("================================");
    }

    // Method to transfer money to another account
    public void transfer(BankAccount toAccount, double amount) {
        System.out.println("\n--- Fund Transfer ---");
        System.out.println("From: " + this.accountNumber + " To: " + toAccount.accountNumber);
        System.out.println("Amount: Rs." + amount);

        if (amount <= 0) {
            System.out.println("Status: FAILED - Invalid amount");
        } else if (amount > this.balance) {
            System.out.println("Status: FAILED - Insufficient balance");
        } else {
            this.balance = this.balance - amount;
            toAccount.balance = toAccount.balance + amount;
            System.out.println("Status: SUCCESS");
            System.out.println("Sender New Balance: Rs." + this.balance);
            System.out.println("Receiver New Balance: Rs." + toAccount.balance);
        }
    }
}