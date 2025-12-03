package com.npci.mini_examples.stream;

import com.npci.mini_examples.stream.model.Dish;

import java.util.List;
import java.util.Map;

public class Stream_Terminal_Operations {

    public static void main(String[] args) {


        List<Dish> menu = Dish.menu;

        // 1. forEach
        menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .forEach(dish -> System.out.println(dish.getName()));

        // 2. collect
        // a. toList
        List<Dish> highCalorieDishes = menu.stream()
                .filter(dish -> dish.getCalories() > 400)
                .toList();
        System.out.println("High Calorie Dishes (>400): " + highCalorieDishes);

        // b. toSet
        var vegetarianDishesSet = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(java.util.stream.Collectors.toSet());
        System.out.println("Vegetarian Dishes Set: " + vegetarianDishesSet);

        // 3. toMap
        var dishMap = menu.stream()
                .collect(java.util.stream.Collectors.toMap(Dish::getName, dish -> dish));
        System.out.println("Dish Map: " + dishMap);

        // 4. reduce -> e.g need total calories of all dishes
        int totalCalories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, (acc, cal) -> acc + cal);
        System.out.println("Total Calories of all Dishes: " + totalCalories);

        // 5. count
        long vegetarianCount = menu.stream()
                .filter(Dish::isVegetarian)
                .count();
        System.out.println("Number of Vegetarian Dishes: " + vegetarianCount);

        // 6. findFirst
        var firstVegetarianDish = menu.stream()
                .filter(Dish::isVegetarian)
                .findFirst();
        System.out.println("First Vegetarian Dish: " + firstVegetarianDish.orElse(null));

        // 7. anyMatch
        boolean hasHealthyDish = menu.stream()
                .anyMatch(dish -> dish.getCalories() < 200);
        System.out.println("Is there any healthy dish (<200 calories)? " + hasHealthyDish);

        // 8. allMatch
        boolean allLowCalorie = menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000);
        System.out.println("Are all dishes low calorie (<1000 calories)? " + allLowCalorie);

        // 9. noneMatch
        boolean noSpicyDishes = menu.stream()
                .noneMatch(dish -> dish.getType() == Dish.Type.MEAT);
        System.out.println("Are there no spicy dishes? " + noSpicyDishes);

        // 10. max
        var maxCalorieDish = menu.stream()
                .max(java.util.Comparator.comparingInt(Dish::getCalories));
        System.out.println("Dish with maximum calories: " + maxCalorieDish.orElse(null));

        // 11. min
        var minCalorieDish = menu.stream()
                .min(java.util.Comparator.comparingInt(Dish::getCalories));
        System.out.println("Dish with minimum calories: " + minCalorieDish.orElse(null));

        // GroupBy

        java.util.Map<Dish.Type, List<Dish>> map = menu.stream()
                .collect(java.util.stream.Collectors.groupingBy(Dish::getType));
        System.out.println("Dishes grouped by type: " + map);

        //..

        // PartitioningBy

        Map<Boolean, List<Dish>> partitionedMenu = menu.stream()
                .collect(java.util.stream.Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println("Partitioned Menu (Vegetarian/Non-Vegetarian): " + partitionedMenu);

    }

}
