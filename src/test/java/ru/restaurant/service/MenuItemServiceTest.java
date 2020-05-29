package ru.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.DishTestData;
import ru.restaurant.MenuTestData;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.model.Dish;
import ru.restaurant.model.MenuItem;
import ru.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.MenuTestData.*;
import static ru.restaurant.RestaurantTestData.REST_1;

class MenuItemServiceTest extends AbstractServiceTest{

    @Autowired
    MenuService service;

    @Autowired
    DishService dishService;

    @Test
    void get() throws Exception {
        MenuItem menuItem = service.get(MENU_1_ID);
        MENU_MATCHER.assertMatch(menuItem, MENU_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void getAll() throws Exception {
        List<MenuItem> all = service.getAll();
        MENU_MATCHER.assertMatch(all, MENUS);
    }

    @Test
    void getAllByDate() {
        MENU_MATCHER.assertMatch(service.getAllByDate(LocalDate.of(2020, Month.APRIL,1)), MENUS);
    }

    @Test
    void create() throws Exception {
        Dish dish = dishService.create(new Dish(null, "New", 50, LocalDate.now(), REST_1));
        MenuItem newMenuItem = new MenuItem(null, LocalDate.of(2020, Month.APRIL,1), dish);

        MenuItem created = service.create(new MenuItem(newMenuItem));
        int newId = created.getId();
        newMenuItem.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenuItem);
        MENU_MATCHER.assertMatch(service.get(newId), newMenuItem);
    }

    @Test
    void duplicateMenuCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new MenuItem(null,
                        LocalDate.of(2020, Month.APRIL,1),
                        DishTestData.DISH_1)));
    }

    @Test
    void update() throws Exception {
        MenuItem updated = MenuTestData.getUpdated();
        service.update(new MenuItem(updated));
        MENU_MATCHER.assertMatch(service.get(MENU_1_ID), updated);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MENU_1_ID);
        MENU_MATCHER.assertMatch(service.getAll(), List.of(MENU_12, MENU_11, MENU_10, MENU_9, MENU_8, MENU_7, MENU_6, MENU_5, MENU_4, MENU_3, MENU_2));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }
}