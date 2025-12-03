package com.npci.mini_examples.stream;

import java.util.List;

public class How_To_Work_With_Stream {

    public static void main(String[] args) {

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // stream ==> data pipeline ==> source + intermediate(s) + terminal

        numbers.stream() // source
               .filter(n -> n % 2 == 0) // intermediate
               .map(n -> n * n) // intermediate
               .forEach(n -> System.out.println(n)); // terminal

    }

}
