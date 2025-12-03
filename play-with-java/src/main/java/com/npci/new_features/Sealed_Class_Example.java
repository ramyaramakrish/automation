package com.npci.new_features;

// Sealed Classes -
// A sealed class is a class that restricts which other classes or interfaces may extend or implement it.

// e.g example

sealed class Vehicle permits Car, Bike {
    void start() {
        System.out.println("Vehicle started");
    }
}

final class Car extends Vehicle {
    void drive() {
        System.out.println("Car is driving");
    }
}

final class Bike extends Vehicle {
    void ride() {
        System.out.println("Bike is riding");
    }
}


// e.g interface example

sealed interface Shape permits Circle, Rectangle {
    double area();
}

final class Circle implements Shape {
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

final class Rectangle implements Shape {
    private double length;
    private double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }
}

class Triangle /* extends Shape */ { // This will cause a compile-time error
    // Implementation
}


public class Sealed_Class_Example {
    public static void main(String[] args) {

    }
}
