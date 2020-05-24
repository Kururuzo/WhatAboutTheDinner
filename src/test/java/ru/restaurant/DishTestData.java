package ru.restaurant;

import ru.restaurant.model.Dish;
import ru.restaurant.model.User;

import java.util.List;

import static ru.restaurant.RestaurantTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingEquals(Dish.class);

    public static final int DISH_1_ID = START_SEQ + 5;

    public static final Dish DISH_1 = new Dish(START_SEQ + 5, "Meat Soup", 50, REST_1); // id:100005
    public static final Dish DISH_2 = new Dish(START_SEQ + 6, "Caesar", 50, REST_1);
    public static final Dish DISH_3 = new Dish(START_SEQ + 7, "BeefSteak", 150, REST_1);
    public static final Dish DISH_4 = new Dish(START_SEQ + 8, "Coffee", 15, REST_1);

    public static final Dish DISH_5 = new Dish(START_SEQ + 9, "Chicken Soup", 40, REST_2);
    public static final Dish DISH_6 = new Dish(START_SEQ + 10, "Salad with meat", 50, REST_2); // id:1000
    public static final Dish DISH_7 = new Dish(START_SEQ + 11, "Fried Chicken", 110, REST_2);
    public static final Dish DISH_8 = new Dish(START_SEQ + 12, "Tee", 10, REST_2); // id:1000

    public static final Dish DISH_9 = new Dish(START_SEQ + 13, "Gazpacho", 30, REST_3);
    public static final Dish DISH_10 = new Dish(START_SEQ + 14, "Vegetable salad", 40, REST_3);
    public static final Dish DISH_11 = new Dish(START_SEQ + 15, "IceCream", 30, REST_3);
    public static final Dish DISH_12 = new Dish(START_SEQ + 16, "Juice", 20, REST_3);

    public static final List<Dish> DISHES = List.of(DISH_12, DISH_11, DISH_10, DISH_9, DISH_8, DISH_7,
            DISH_6, DISH_5, DISH_4, DISH_3, DISH_2, DISH_1);

    public static Dish getNew() {
        return new Dish(null, "New", 50, REST_1);
    }


    public static Dish getUpdated() {
        Dish dish = new Dish(DISH_1);
        dish.setName("Updated");
        dish.setPrice(75);
        return dish;
    }
}
