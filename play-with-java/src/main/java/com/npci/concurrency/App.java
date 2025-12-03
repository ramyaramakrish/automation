package com.npci.concurrency;

import java.util.Scanner;

public class App {


    private static void computationTask() {
        // Simulate a computation task
        long sum = 0;
        for (long i = 0; i < 1_000_000L; i++) {
            sum += i;
        }
        System.out.println("Computation result: " + sum);
    }

    private static void ioTask() {
        // Simulate an I/O task
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter something: ");
        String input = scanner.nextLine();
        System.out.println("You entered: " + input);
        scanner.close();
    }

    public static void main(String[] args) {

        String threadName = Thread.currentThread().getName();
        System.out.println("Main thread: " + threadName + " started.");

        Thread ioThread = new Thread(() -> {
            System.out.println("I/O thread started.");
            ioTask();
            System.out.println("I/O thread finished.");
        });

        Thread computationThread = new Thread(() -> {
            System.out.println("Computation thread started.");
            computationTask();
            System.out.println("Computation thread finished.");
        });

        ioThread.start(); // New Stack allocated
        computationThread.start(); // New Stack allocated
    }
}
