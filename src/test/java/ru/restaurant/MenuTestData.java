package ru.restaurant;

import ru.restaurant.model.Menu;
import ru.restaurant.to.MenuTo;
import ru.restaurant.to.RestaurantTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.restaurant.DishTestData.*;
import static ru.restaurant.RestaurantTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingEquals(Menu.class);
    public static TestMatcher<MenuTo> MENU_TO_MATCHER = TestMatcher.usingFieldsComparator(MenuTo.class, "dishes");

    public static final int MENU_1_ID = START_SEQ + 16;

    public static final Menu MENU_1 = new Menu(START_SEQ + 16, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_1);
    public static final Menu MENU_2 = new Menu(START_SEQ + 17, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_3);
    public static final Menu MENU_3 = new Menu(START_SEQ + 18, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_11);
    public static final Menu MENU_4 = new Menu(START_SEQ + 19, LocalDate.of(2020, Month.APRIL,1), REST_2, DISH_2);
    public static final Menu MENU_5 = new Menu(START_SEQ + 20, LocalDate.of(2020, Month.APRIL,1), REST_2, DISH_4);
    public static final Menu MENU_6 = new Menu(START_SEQ + 21, LocalDate.of(2020, Month.APRIL,1), REST_2, DISH_10);
    public static final Menu MENU_7 = new Menu(START_SEQ + 22, LocalDate.of(2020, Month.APRIL,1), REST_3, DISH_5);
    public static final Menu MENU_8 = new Menu(START_SEQ + 23, LocalDate.of(2020, Month.APRIL,1), REST_3, DISH_6);
    public static final Menu MENU_9 = new Menu(START_SEQ + 24, LocalDate.of(2020, Month.APRIL,1), REST_3, DISH_9);

    public static final List<Menu> MENUS = List.of(MENU_9, MENU_8, MENU_7, MENU_6, MENU_5, MENU_4, MENU_3, MENU_2, MENU_1);

    public static final MenuTo MENU_TO_1 = new MenuTo(LocalDate.of(2020, Month.APRIL,1),
            new RestaurantTo(REST_1), List.of(DISH_1, DISH_3, DISH_11));
    public static final MenuTo MENU_TO_2 = new MenuTo(LocalDate.of(2020, Month.APRIL,1),
            new RestaurantTo(REST_2), List.of(DISH_2, DISH_4, DISH_10));
    public static final MenuTo MENU_TO_3 = new MenuTo(LocalDate.of(2020, Month.APRIL,1),
            new RestaurantTo(REST_3), List.of(DISH_5, DISH_6, DISH_9));

    public static final List<MenuTo> MENUS_TO = List.of(MENU_TO_1, MENU_TO_2, MENU_TO_3);

    public static Menu getNew(){
        return new Menu(null, LocalDate.of(2020, Month.APRIL,1), REST_1, DISH_8);
    }

    public static Menu getUpdated() {
        Menu menu = new Menu(MENU_1);
        menu.setDate(LocalDate.of(2020, Month.MAY,9));
        menu.setRestaurant(REST_3);
        menu.setDish(DISH_7);
        return menu;
    }
}
