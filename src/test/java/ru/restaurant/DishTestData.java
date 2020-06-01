package ru.restaurant;

import ru.restaurant.model.Dish;
import ru.restaurant.to.DishTo;
import ru.restaurant.to.DishToForOffer;
import ru.restaurant.util.ToUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static ru.restaurant.RestaurantTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingEquals(Dish.class);
    public static TestMatcher<DishTo> DISH_TO_MATCHER = TestMatcher.usingFieldsComparator(DishTo.class);

    public static final int DISH_1_ID = START_SEQ + 6;

    public static final Dish DISH_1 = new Dish(START_SEQ + 6, "Meat Soup", 50, LocalDate.of(2020, Month.APRIL,1), REST_1); // id:100005
    public static final Dish DISH_2 = new Dish(START_SEQ + 7, "Caesar", 50, LocalDate.of(2020, Month.APRIL,1), REST_1);
    public static final Dish DISH_3 = new Dish(START_SEQ + 8, "BeefSteak", 150, LocalDate.of(2020, Month.APRIL,1), REST_1);
    public static final Dish DISH_4 = new Dish(START_SEQ + 9, "Coffee", 15, LocalDate.of(2020, Month.APRIL,1), REST_1);

    public static final Dish DISH_5 = new Dish(START_SEQ + 10, "Chicken Soup", 40, LocalDate.of(2020, Month.APRIL,1), REST_2);
    public static final Dish DISH_6 = new Dish(START_SEQ + 11, "Salad with meat", 50, LocalDate.of(2020, Month.APRIL,1), REST_2); // id:1000
    public static final Dish DISH_7 = new Dish(START_SEQ + 12, "Fried Chicken", 110, LocalDate.of(2020, Month.APRIL,1), REST_2);
    public static final Dish DISH_8 = new Dish(START_SEQ + 13, "Tee", 10, LocalDate.of(2020, Month.APRIL,1), REST_2); // id:1000

    public static final Dish DISH_9 = new Dish(START_SEQ + 14, "Gazpacho", 30, LocalDate.of(2020, Month.APRIL,1), REST_3);
    public static final Dish DISH_10 = new Dish(START_SEQ + 15, "Vegetable salad", 40, LocalDate.of(2020, Month.APRIL,1), REST_3);
    public static final Dish DISH_11 = new Dish(START_SEQ + 16, "IceCream", 30, LocalDate.of(2020, Month.APRIL,1), REST_3);
    public static final Dish DISH_12 = new Dish(START_SEQ + 17, "Juice", 20, LocalDate.of(2020, Month.APRIL,1), REST_3);

    public static final List<Dish> DISHES = List.of(DISH_12, DISH_11, DISH_10, DISH_9, DISH_8, DISH_7,
            DISH_6, DISH_5, DISH_4, DISH_3, DISH_2, DISH_1);

    public static final Set<Dish> DISHES_FOR_REST_1 = Set.of(DISH_1, DISH_2, DISH_3, DISH_4);

    public static final DishTo DISH_TO_1 = new DishTo(DISH_1);

    public static final List<DishTo> DISHES_TO = ToUtil.tosFromModel(DISHES, DishTo.class);

    public static final List<DishTo> DISHES_TO_FOR_REST_1 = ToUtil.tosFromModel(List.of(DISH_1, DISH_2, DISH_3, DISH_4), DishTo.class);
//    public static final Set<DishTo> DISHES_TO_FOR_REST_1 = ToUtil.tosFromModelSet(Set.of(DISH_1, DISH_2, DISH_3, DISH_4), DishTo.class);

    public static final Set<DishToForOffer> DISHES_TO_FOR_OFFER_REST_1 = ToUtil.tosFromModelSet(Set.of(DISH_1, DISH_2, DISH_3, DISH_4), DishToForOffer.class);
    public static final Set<DishToForOffer> DISHES_TO_FOR_OFFER_REST_2 = ToUtil.tosFromModelSet(Set.of(DISH_5, DISH_6, DISH_7, DISH_8), DishToForOffer.class);
    public static final Set<DishToForOffer> DISHES_TO_FOR_OFFER_REST_3 = ToUtil.tosFromModelSet(Set.of(DISH_9, DISH_10, DISH_11, DISH_12), DishToForOffer.class);

    public static Dish getNew() {
        return new Dish(null, "New", 50, LocalDate.now(), REST_1);
    }

    public static Dish getUpdated() {
        Dish dish = new Dish(DISH_1);
        dish.setName("Updated");
        dish.setPrice(75);
        return dish;
    }
}
