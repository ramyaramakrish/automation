package com.example;

import java.util.Optional;

class Insurance{
    String name;
    Insurance(String name){
        this.name=name;
    }
}
class Car{
    String model;
    Optional<Insurance> insurance= Optional.empty();
    Car(String model){
        this.model=model;
    }
}
class Customer{
    String name;
    Optional<Car> car= Optional.empty();
    Customer(String name){
        this.name=name;
    }
}

public class Q {

    public static void main(String[] args) {

        // scenario 1: customer has car with insurance
        Customer customer=new Customer("John");
        Car car=new Car("Toyota");
        Insurance insurance=new Insurance("Allianz");
        car.insurance=Optional.of(insurance);
        customer.car=Optional.of(car);

        //scenario 2: customer has car without insurance
//        customer=new Customer("Alice");
//        car=new Car("Honda");
//        customer.car=Optional.of(car);


        //scenario 3: customer without car
        customer=new Customer("Bob");

        //-------------
        // read insurance name safely
        //--------------

        String insuranceName=customer.car
                .flatMap(c->c.insurance)
                .map(i->i.name)
                .orElse("Unknown");
        System.out.println("Insurance Name: "+insuranceName);

    }

}
