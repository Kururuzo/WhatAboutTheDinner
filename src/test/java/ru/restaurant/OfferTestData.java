package ru.restaurant;

import ru.restaurant.model.Restaurant;
import ru.restaurant.to.OfferTo;
import ru.restaurant.to.RestaurantTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.restaurant.RestaurantTestData.*;

public class OfferTestData {
    public static TestMatcher<OfferTo> OFFER_MATCHER = TestMatcher.usingFieldsComparator(OfferTo.class, "restaurants", "dishes");

//    public static final int OFFER_ID_1 = START_SEQ + 100;

    public static final RestaurantTo r1 = new RestaurantTo(REST_1, DishTestData.DISHES_TO_FOR_OFFER_REST_1);
    public static final RestaurantTo r2 = new RestaurantTo(REST_2, DishTestData.DISHES_TO_FOR_OFFER_REST_2);
    public static final RestaurantTo r3 = new RestaurantTo(REST_3, DishTestData.DISHES_TO_FOR_OFFER_REST_3);

    public static final OfferTo OFFER_1 = new OfferTo(LocalDate.of(2020, Month.APRIL,1),
                List.of(r1, r2, r3));
}
