package ru.restaurant;

import ru.restaurant.model.MenuItem;
import ru.restaurant.to.MenuTo;
import ru.restaurant.to.RestaurantTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.restaurant.DishTestData.*;
import static ru.restaurant.RestaurantTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static TestMatcher<MenuItem> MENU_MATCHER = TestMatcher.usingEquals(MenuItem.class);
    public static TestMatcher<MenuTo> MENU_TO_MATCHER = TestMatcher.usingFieldsComparator(MenuTo.class, "restaurant");

    public static final int MENU_1_ID = START_SEQ + 17;

    public static final MenuItem MENU_1 = new MenuItem(START_SEQ + 17, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_1);
    public static final MenuItem MENU_2 = new MenuItem(START_SEQ + 18, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_2);
    public static final MenuItem MENU_3 = new MenuItem(START_SEQ + 19, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_3);
    public static final MenuItem MENU_4 = new MenuItem(START_SEQ + 20, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_4);

    public static final MenuItem MENU_5 = new MenuItem(START_SEQ + 21, LocalDate.of(2020, Month.APRIL,1), REST_2, DISH_5);
    public static final MenuItem MENU_6 = new MenuItem(START_SEQ + 22, LocalDate.of(2020, Month.APRIL,1), REST_2, DISH_6);
    public static final MenuItem MENU_7 = new MenuItem(START_SEQ + 23, LocalDate.of(2020, Month.APRIL,1), REST_2, DISH_7);
    public static final MenuItem MENU_8 = new MenuItem(START_SEQ + 24, LocalDate.of(2020, Month.APRIL,1), REST_2, DISH_8);

    public static final MenuItem MENU_9 = new MenuItem(START_SEQ + 25, LocalDate.of(2020, Month.APRIL,1), REST_3, DISH_9);
    public static final MenuItem MENU_10 = new MenuItem(START_SEQ + 26, LocalDate.of(2020, Month.APRIL,1), REST_3, DISH_10);
    public static final MenuItem MENU_11 = new MenuItem(START_SEQ + 27, LocalDate.of(2020, Month.APRIL,1), REST_3, DISH_11);
    public static final MenuItem MENU_12 = new MenuItem(START_SEQ + 28, LocalDate.of(2020, Month.APRIL,1), REST_3, DISH_12);

    public static final List<MenuItem> MENUS = List.of(MENU_12, MENU_11, MENU_10, MENU_9, MENU_8, MENU_7, MENU_6,
            MENU_5, MENU_4, MENU_3, MENU_2, MENU_1);

    public static final MenuTo MENU_TO_1 = new MenuTo(LocalDate.of(2020, Month.APRIL,1),
            new RestaurantTo(REST_1), List.of(DISH_1, DISH_2, DISH_3, DISH_4));
    public static final MenuTo MENU_TO_2 = new MenuTo(LocalDate.of(2020, Month.APRIL,1),
            new RestaurantTo(REST_2), List.of(DISH_5, DISH_6, DISH_7, DISH_8));
    public static final MenuTo MENU_TO_3 = new MenuTo(LocalDate.of(2020, Month.APRIL,1),
            new RestaurantTo(REST_3), List.of(DISH_9, DISH_10, DISH_11, DISH_12));

    public static final List<MenuTo> MENUS_TO = List.of(MENU_TO_1, MENU_TO_2, MENU_TO_3);

    public static MenuItem getNew(){
        return new MenuItem(null, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_8);
    }

    public static MenuItem getUpdated() {
        MenuItem menuItem = new MenuItem(MENU_1);
        menuItem.setDate(LocalDate.of(2020, Month.MAY,9));
        menuItem.setRestaurant(REST_3);
        menuItem.setDish(DISH_7);
        return menuItem;
    }
}
