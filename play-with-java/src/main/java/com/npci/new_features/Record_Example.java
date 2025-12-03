package com.npci.new_features;


import java.util.Objects;

record AccountRecord(int id, String name, double balance) {
    public AccountRecord {
        Objects.requireNonNull(name, "Name cannot be null");
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }
}

public class Record_Example {
    public static void main(String[] args) {

        AccountRecord account = new AccountRecord(1, "John Doe", 1000.0);
        System.out.println(account);

    }
}
