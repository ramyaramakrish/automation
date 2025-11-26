package com.example;

public class Application {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + " : Application started");

        Account a1 = new Account("A101");
        // later initialization
        a1.holderName = "Nag";
        a1.balance = 5000;

        Account a2 = new Account("A102", "Riya");
        a2.balance = 7000;

        Account a3 = new Account("A103", "dia", 9000);

    }

}
