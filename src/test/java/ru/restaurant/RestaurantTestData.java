package ru.restaurant;

import ru.restaurant.model.Restaurant;
import ru.restaurant.to.DishToForOffer;
import ru.restaurant.to.RestaurantTo;
import ru.restaurant.util.ToUtil;

import java.util.List;
import java.util.Set;

import static ru.restaurant.DishTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> REST_MATCHER = TestMatcher.usingEquals(Restaurant.class);
    public static TestMatcher<RestaurantTo> REST_TO_MATCHER = TestMatcher.usingFieldsComparator(RestaurantTo.class, "dishes");

    public static final int REST_1_ID = START_SEQ + 3;


    public static final Restaurant REST_1 = new Restaurant(START_SEQ + 3, "Lucky Pizza"); // 100_003
//    public static final Restaurant REST_1_WITH_DISHES = new Restaurant(START_SEQ + 2, "Lucky Pizza", Set.of(DISH_1, DISH_2, DISH_3, DISH_4)); // 100_002
    public static final Restaurant REST_2 = new Restaurant(START_SEQ + 4, "Steak House");
    public static final Restaurant REST_3 = new Restaurant(START_SEQ + 5, "Green Garden");

    public static final List<Restaurant> RESTAURANTS = List.of(REST_1, REST_2, REST_3);

    public static final RestaurantTo REST_TO_1 = new RestaurantTo(REST_1);
//    public static final RestaurantTo REST_TO_1_WITH_DISHES = new RestaurantTo(REST_1_WITH_DISHES);
//    public static final Set<DishToForOffer> DISH_TO_FOR_OFFERS = ToUtil.tosFromModelSet(Set.of(DISH_1, DISH_2, DISH_3, DISH_4), DishToForOffer.class);
//    public static final RestaurantTo REST_TO_1_WITH_DISHES_TO = new RestaurantTo(REST_1.getName(), DISH_TO_FOR_OFFERS);


    public static final List<RestaurantTo> RESTAURANTS_TO = ToUtil.tosFromModel(RESTAURANTS, RestaurantTo.class);

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static RestaurantTo getNewTo() {
        return new RestaurantTo(null, "New");
    }

    public static Restaurant getUpdated() {
        Restaurant restauran = new Restaurant(REST_1);
        restauran.setName("Updated");
        return restauran;
    }

    public static RestaurantTo getUpdatedTo() {
        RestaurantTo restauran = new RestaurantTo(REST_1);
        restauran.setName("Updated");
        return restauran;
    }
}
