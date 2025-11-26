package com.example; // package declaration

// import statements (if any) would go here
// import java.lang.*;

// Public Type ( class declaration )
public class Account {
    // fields
    String accountNumber;// null
    String holderName; // null
    double balance; // 0.0

    // constructor(s)
    Account(String accountNumber) {
        // validation logics
        // init logics
        // this.accountNumber = accountNumber;
        this(accountNumber, "Unknown", 0.0);
    }

    Account(String accountNumber, String holderName) {
        // validation logics
        // init logics
        // this.accountNumber = accountNumber;
        // this.holderName = holderName;
        this(accountNumber, holderName, 0.0);
    }

    Account(String accountNumber, String holderName, double balance) {
        // validation logics
        // init logics
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    // methods

    // Method to deposit money
    void deposit(double amount) {
        this.balance = this.balance + amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    // Method to withdraw money
    // Method to withdraw money
    void withdraw(double amount) {
        if (amount <= this.balance) {
            this.balance = this.balance - amount;
            System.out.println("Withdrawn Rs." + amount + " | New Balance: Rs." + this.balance);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    // Method to check balance
    void checkBalance() {
        System.out.println("Current Balance: Rs." + this.balance);
    }

}

// default Type(s) ( class declaration )