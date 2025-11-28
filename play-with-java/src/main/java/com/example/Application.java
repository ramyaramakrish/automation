package com.example;

public class Application {

    public static void main(String[] args) {

        // Account account = new Account("A123", "John Doe", 500.0);
        SavingsAccount sa = new SavingsAccount("SA123", "Alice", 1000.0, 5.0);
        CurrentAccount ca = new CurrentAccount("CA123", "Bob", 2000.0, 500.0);
        FixedDepositAccount fda = new FixedDepositAccount("FDA123", "Charlie", 5000.0, 12, 5);

        PaymentProcessor processor = new PaymentProcessor();
        processor.withdrawFunds(200.0, sa); // Withdraw from SavingsAccount
        processor.withdrawFunds(2500.0, ca); // Withdraw from CurrentAccount
        processor.withdrawFunds(1000.0, fda); // Attempt to withdraw from Fixed

    }

}
