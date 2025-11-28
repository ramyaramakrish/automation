package com.example;

// OCP => Open for extension, closed for modification
public class PaymentProcessor {

    // Polymorphic method to withdraw funds from any account type
    public void withdrawFunds(double amount, Account account) {
        account.withdraw(amount); //
    }

}
