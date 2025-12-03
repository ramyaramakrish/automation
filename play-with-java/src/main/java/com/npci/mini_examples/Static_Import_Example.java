package com.npci.mini_examples;

import java.util.List;
import static java.util.stream.Collectors.*;

public class Static_Import_Example {
    public static void main(String[] args) {

        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        System.out.println(
                numbers
                        .stream()
                        .map(n -> n * 5)
                        .collect(toList())
        );

    }
}
