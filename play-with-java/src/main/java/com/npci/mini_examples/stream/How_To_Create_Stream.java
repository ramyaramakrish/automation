package com.npci.mini_examples.stream;

import java.util.List;

public class How_To_Create_Stream {

    public static void main(String[] args) {

        // How to create Stream in Java?

        // 1. From Collections ( most common way )
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        numbers.stream().forEach(n-> System.out.println(n));

        // 2. From Arrays
        Integer[] arr = {6, 7, 8, 9, 10};
        java.util.Arrays.stream(arr).forEach(n-> System.out.println(n));

        // 3. Using Stream.of()
        java.util.stream.Stream.of(11, 12, 13, 14, 15).forEach(n-> System.out.println(n));





    }

}
