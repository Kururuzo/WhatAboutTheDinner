package ru.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.DishTestData;
import ru.restaurant.model.Dish;
import ru.restaurant.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.DishTestData.*;

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
    void create() throws Exception {
        Dish newDish = getNew();
        Dish created = service.create(new Dish(newDish));
        int newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

//    @Test
//    void duplicateDishCreate() throws Exception {
//        assertThrows(DataAccessException.class, () ->
//                service.create(new Dish(null, "Coffee", 50)));
//    }

    @Test
    void createWithException() throws Exception {
        //think about
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
        DISH_MATCHER.assertMatch(service.getAll(), List.of(DISH_11, DISH_10, DISH_9, DISH_8, DISH_7,
                DISH_6, DISH_5, DISH_4, DISH_3, DISH_2));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }
}