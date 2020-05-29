package ru.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.DishTestData;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.model.Restaurant;
import ru.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest{

    @Autowired
    RestaurantService service;

    @Test
    void get() throws Exception {
        Restaurant restaurant = service.get(REST_1_ID);
        REST_MATCHER.assertMatch(restaurant, REST_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void findByName() throws Exception {
        Restaurant restaurant = service.findByName(REST_1.getName());
        REST_MATCHER.assertMatch(restaurant, REST_1);
    }

    @Test
    void findByIdWithDishesByDate() throws Exception {
        Restaurant restaurant = service.findByIdWithDishesByDate(REST_1_ID, LocalDate.of(2020, Month.APRIL,1));
        Restaurant r = new Restaurant(REST_1);
        r.setDishes(DishTestData.DISHES_FOR_REST_1);
        REST_MATCHER.assertMatch(restaurant, r);
    }

    @Test
    void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        REST_MATCHER.assertMatch(all, RESTAURANTS);
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        Restaurant created = service.create(new Restaurant(newRestaurant));
        int newId = created.getId();
        newRestaurant.setId(newId);
        REST_MATCHER.assertMatch(created, newRestaurant);
        REST_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void duplicateRestaurantCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, "Lucky Pizza")));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        service.update(new Restaurant(updated));
        REST_MATCHER.assertMatch(service.get(REST_1_ID), updated);
    }

    @Test
    public void delete() throws Exception {
        service.delete(REST_1_ID);
        REST_MATCHER.assertMatch(service.getAll(), List.of(REST_2, REST_3));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }
}