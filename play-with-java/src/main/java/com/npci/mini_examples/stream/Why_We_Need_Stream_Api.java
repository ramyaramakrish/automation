package com.npci.mini_examples.stream;


import com.npci.mini_examples.stream.model.Dish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Why_We_Need_Stream_Api {

    public static void main(String[] args) {

        List<Dish> menu = Dish.menu; // read  from file | database
        // find Lowest Calorie Dish's names sorted by calories into a List
        List<String> lowCalorieDishesNames = getLowCalorieDishesNamesSortedByCalories(menu);
        System.out.println("Low Calorie Dishes Names Sorted By Calories : " + lowCalorieDishesNames);


        List<String> lowCalorieDishesNames_StreamApi = getLowCalorieDishesNamesSortedByCalories_StreamApi(menu);
        System.out.println("Low Calorie Dishes Names Sorted By Calories ( Stream Api ) : " + lowCalorieDishesNames_StreamApi);

    }

    // Modern Approach ( Java-8 Stream Api )
    private static List<String> getLowCalorieDishesNamesSortedByCalories_StreamApi(List<Dish> menu) {
        return menu.stream()
                .filter(d -> d.getCalories() < 400)
                //.sorted((d1, d2) -> Integer.compare(d1.getCalories(), d2.getCalories()))
                //.map(d -> d.getName())
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName) // Method Reference
                .collect(Collectors.toList());
    }


    // Traditional Approach ( Pre-Java-8 )
    private static List<String> getLowCalorieDishesNamesSortedByCalories(List<Dish> menu) {
        // step-1 : filter
        List<Dish> lowCalorieDishes = new ArrayList<>();
        for (Dish d : menu) {
            if (d.getCalories() < 400) {
                lowCalorieDishes.add(d);
            }
        }
        // step-2 : sort
        //from java-8 onwards we can use lambda expression
        Comparator<Dish> dishCalComparatorLambda = (o1, o2) -> Integer.compare(o1.getCalories(), o2.getCalories());

        Collections.sort(lowCalorieDishes, dishCalComparatorLambda);
        // step-3 : map ( transform )
        List<String> lowCalorieDishesNames = new ArrayList<>();
        for (Dish d : lowCalorieDishes) {
            lowCalorieDishesNames.add(d.getName());
        }
        return lowCalorieDishesNames;
    }

}
