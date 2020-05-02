package ru.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.DishTestData;
import ru.restaurant.MenuTestData;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.model.Menu;
import ru.restaurant.util.Exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.MenuTestData.*;

class MenuServiceTest extends AbstractServiceTest{

    @Autowired
    MenuService service;

    @Test
    void get() throws Exception {
        Menu menu = service.get(MENU_1_ID);
        MENU_MATCHER.assertMatch(menu, MENU_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void getAll() throws Exception {
        List<Menu> all = service.getAll();
        MENU_MATCHER.assertMatch(all, MENUS);
    }

    @Test
    void create() throws Exception {
        Menu newMenu = getNew();
        Menu created = service.create(new Menu(newMenu));
        int newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(service.get(newId), newMenu);
    }

    @Test
    void duplicateMenuCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Menu(null,
                        LocalDate.of(2020, Month.APRIL,1),
                        RestaurantTestData.REST_1,
                        DishTestData.DISH_1)));
    }

    @Test
    void createWithException() throws Exception {
        //think about
    }

    @Test
    void update() throws Exception {
        Menu updated = MenuTestData.getUpdated();
        service.update(new Menu(updated));
        MENU_MATCHER.assertMatch(service.get(MENU_1_ID), updated);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MENU_1_ID);
        MENU_MATCHER.assertMatch(service.getAll(), List.of(MENU_9, MENU_8, MENU_7, MENU_6, MENU_5, MENU_4, MENU_3, MENU_2));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }
}