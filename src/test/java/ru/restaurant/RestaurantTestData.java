package ru.restaurant;

import ru.restaurant.model.Restaurant;

import java.util.List;

import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> REST_MATCHER = TestMatcher.usingEquals(Restaurant.class);

    public static final int REST_1_ID = START_SEQ + 13;

    public static final Restaurant REST_1 = new Restaurant(START_SEQ + 13, "Lucky Pizza");
    public static final Restaurant REST_2 = new Restaurant(START_SEQ + 14, "Steak House");
    public static final Restaurant REST_3 = new Restaurant(START_SEQ + 15, "Green Garden");

    public static final List<Restaurant> RESTAURANTS = List.of(REST_3, REST_2, REST_1);

    public static Restaurant getNew() {
        return new Restaurant(null, "New");
    }

    public static Restaurant getUpdated() {
        Restaurant restauran = new Restaurant(REST_1);
        restauran.setName("Updated");
        return restauran;
    }
}
