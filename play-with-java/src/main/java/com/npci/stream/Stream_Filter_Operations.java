package com.npci.stream;

import java.util.List;

public class Stream_Filter_Operations {

    public static void main(String[] args) {


        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // stream => intermediate operations => terminal operation

        // 1. filtering
        // . limit()
        // . skip()
        // . distinct()
        // . filter()
        // . takeWhile()
        // . dropWhile()

        //-----------------------
        // 1. limit() +  skip()
        //-----------------------

        System.out.println("Using limit()");
        numbers.stream()
                .limit(5) // limits the stream to first 5 elements
                .forEach(System.out::println);

        System.out.println("Using skip()");
        numbers.stream()
                .skip(5) // skips the first 5 elements
                .forEach(System.out::println);

        //-----------------------
        // 2. distinct() + filter()
        //-----------------------

        System.out.println("Using distinct()");
        List<Integer> numbersWithDuplicates = List.of(1, 2, 2, 3, 4, 4, 5);
        numbersWithDuplicates.stream()
                .distinct() // removes duplicate elements
                .forEach(System.out::println);

        System.out.println("Using filter()");
        numbers.stream()
                .filter(n -> n % 2 == 0) // filters even numbers
                .forEach(System.out::println);

        //-----------------------
        // 3. takeWhile() + dropWhile()
        //-----------------------

        List<Integer> sortedNumbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("Using takeWhile()");
        sortedNumbers.stream()
                .takeWhile(n -> n < 6) // takes elements while the condition is true
                .forEach(System.out::println);

        System.out.println("Using dropWhile()");
        sortedNumbers.stream()
                .dropWhile(n -> n < 6) // drops elements while the condition is true
                .forEach(System.out::println);

    }

}
