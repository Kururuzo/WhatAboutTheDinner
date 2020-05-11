package ru.restaurant;

import ru.restaurant.model.Dish;
import ru.restaurant.model.User;

import java.util.List;

import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingEquals(Dish.class);

    public static final int DISH_1_ID = START_SEQ + 2;

    public static final Dish DISH_1 = new Dish(START_SEQ + 2, "Meat Soup", 50); // id:100002
    public static final Dish DISH_2 = new Dish(START_SEQ + 3, "Chicken Soup", 40);
    public static final Dish DISH_3 = new Dish(START_SEQ + 4, "Salad with meat", 50); // id:100004
    public static final Dish DISH_4 = new Dish(START_SEQ + 5, "Vegetable salad", 40);
    public static final Dish DISH_5 = new Dish(START_SEQ + 6, "Caesar", 50);
    public static final Dish DISH_6 = new Dish(START_SEQ + 7, "BeefSteak", 150);
    public static final Dish DISH_7 = new Dish(START_SEQ + 8, "Fried Chicken", 110);
    public static final Dish DISH_8 = new Dish(START_SEQ + 9, "IceCream", 30);
    public static final Dish DISH_9 = new Dish(START_SEQ + 10, "Juice", 20);
    public static final Dish DISH_10 = new Dish(START_SEQ + 11, "Coffee", 15);
    public static final Dish DISH_11 = new Dish(START_SEQ + 12, "Tee", 10); // id:100012

    public static final List<Dish> DISHES = List.of(DISH_11, DISH_10, DISH_9, DISH_8, DISH_7,
            DISH_6, DISH_5, DISH_4, DISH_3, DISH_2, DISH_1);

    public static Dish getNew() {
        return new Dish(null, "New", 50);
    }

    public static Dish getUpdated() {
        Dish dish = new Dish(DISH_1);
        dish.setName("Updated");
        dish.setPrice(75);
        return dish;
    }
}
