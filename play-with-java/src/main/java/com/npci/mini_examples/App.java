package com.npci.mini_examples;

public class App {
    public static void main(String[] args) {
        eat();
    }

    public static void eat() {
        System.out.println("eat something");
        eat();
    }
}
