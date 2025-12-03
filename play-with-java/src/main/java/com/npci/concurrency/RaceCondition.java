package com.npci.concurrency;

class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class RaceCondition {
    public static void main(String[] args) {

        Counter counter = new Counter(); // in heap..

        // create 1000 threads
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
        }
        // start all threads
        for (int i = 0; i < 1000; i++) {
            threads[i].start();
        }
        // wait for all threads to finish
        for (int i = 0; i < 1000; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Final count: " + counter.getCount()); //1000*1000=100000

    }
}
