package ru.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.DishTestData;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.model.Dish;
import ru.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.DishTestData.*;
import static ru.restaurant.MenuTestData.MENUS;
import static ru.restaurant.MenuTestData.MENU_MATCHER;

class DishServiceTest extends AbstractServiceTest{

    @Autowired
    DishService service;

    @Test
    void get() throws Exception {
        Dish dish = service.get(DISH_1_ID);
        DISH_MATCHER.assertMatch(dish, DISH_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void getAll() throws Exception {
        List<Dish> all = service.getAll();
        DISH_MATCHER.assertMatch(all, DISHES);
    }

    @Test
    void getAllByDate() {
        DISH_MATCHER.assertMatch(service.getAllByDate(LocalDate.of(2020, Month.APRIL,1)), DISHES);
    }

    @Test
    void findAllByDateAndRestaurantId() {
        DISH_MATCHER.assertMatch(
                service.findAllByDateAndRestaurantId(
                        LocalDate.of(2020, Month.APRIL,1),
                        RestaurantTestData.REST_1_ID), DISH_1, DISH_2, DISH_3, DISH_4);
    }

    @Test
    void create() throws Exception {
        Dish newDish = getNew();
        Dish created = service.create(new Dish(newDish));
        int newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        service.update(new Dish(updated));
        DISH_MATCHER.assertMatch(service.get(DISH_1_ID), updated);
    }

    @Test
    public void delete() throws Exception {
        service.delete(DISH_1_ID);
        DISH_MATCHER.assertMatch(service.getAll(), List.of(DISH_12, DISH_11, DISH_10, DISH_9, DISH_8, DISH_7,
                DISH_6, DISH_5, DISH_4, DISH_3, DISH_2));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }
}