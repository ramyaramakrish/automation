package com.npci.mini_examples.stream;

import java.util.Arrays;
import java.util.List;

public class Stream_Transform_Operations {
    public static void main(String[] args) {

        // stream transform operations
        // - map()
        // - flatMap()

        // 1. map()
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");
        names.stream()
                .map(name -> name.toUpperCase())
                .forEach(System.out::println);

        names
                .stream()
                .map(name -> name.length())
                .forEach(length -> System.out.println("Length: " + length));

        // 2. flatMap()

        String[] menu={
                "idly,vada,sambar",
                "dosa,chapati,pongal",
                "rice,curd,lemon,rice"
        };

        Arrays.stream(menu)
                .flatMap(line-> Arrays.stream(line.split(",")))
                .map(item->item.toUpperCase())
                .filter(item->item.length()>4)
                .forEach(System.out::println);




    }
}
