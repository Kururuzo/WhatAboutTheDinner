package ru.restaurant.util;

import ru.restaurant.model.Dish;
import ru.restaurant.to.DishTo;

import java.util.List;
import java.util.stream.Collectors;

public class DishUtil {
    private DishUtil() {
    }

    public static List<DishTo> tosFromDishes (List<Dish> dishes) {
        return dishes.stream().map(DishTo::new).collect(Collectors.toList());
    }
}
