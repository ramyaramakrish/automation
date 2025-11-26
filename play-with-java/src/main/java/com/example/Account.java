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
    // inner class(es)
}

// default Type(s) ( class declaration )