package com.example.mini_example;

public class ExampleApplication {
    public static void main(String[] args) {

        Actor actor = new Actor();
        // static/compile-time polymorphism - method overloading
        actor.act();
        actor.act(1000);

        // -----------------------
        System.out.println(1);
        System.out.println(1.0);
        System.out.println(true);
        System.out.println("string");
        // -----------------------

    }

}
