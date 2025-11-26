package com.example;

public class Application {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + " : Application started");

        Account a1 = new Account("A101");
        a1.holderName = "Nag";
        a1.balance = 5000;

        Account a2 = new Account("A102", "Riya");
        a2.balance = 7000;

        Account a3 = new Account("A103", "dia", 9000);

        a1.deposit(2000);
        a1.withdraw(1000);
        a1.checkBalance();

        a2.deposit(3000);
        a2.withdraw(12000);
        a2.checkBalance();

        a3.deposit(4000);
        a3.withdraw(5000);
        a3.checkBalance();

    }

}
