package com.npci.mini_examples;

import java.util.ArrayList;
import java.util.List;

public class Wrapper_Classes_Ex {
    public static void main(String[] args) {
        //int a = 10; // Value
        // Wrapper Class => to convert value into object
        // why we need to convert value into object?
        // because collection framework works with objects only
        //Integer i = Integer.valueOf(a); // Boxing => converting value into object
//        System.out.println("Value of i: " + i);

        //---------------

        int value = 12;
        Integer obj = value; // Auto Boxing
        List<Integer> list = new ArrayList<>();
        list.add(obj); // adding object to collection

        //------------------------

        Integer o1 = 12;
        Integer o2 = 13;

        int k1 = o1.intValue();
        int k2 = o2; // Auto Unboxing
        int k = k1 + k2;

        //------------------------

//        Wrapper classes

//        Byte
        // Short
//        Integer
//        Long
//        Float
//        Double
//        Character
//        Boolean

        //------------------------


        int num1 = 12;
        int num2 = 13;
        Integer.compare(num1, num2); // 0 if both are equal
        // +ve if num1>num2
        // -ve if num1<num2
    }
}
