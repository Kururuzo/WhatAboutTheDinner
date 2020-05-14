package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.DishTestData;
import ru.restaurant.MenuTestData;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.model.Menu;
import ru.restaurant.service.MenuService;
import ru.restaurant.util.exception.NotFoundException;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.web.json.JsonUtil;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.MenuTestData.*;
import static ru.restaurant.UserTestData.ADMIN;
import static ru.restaurant.UserTestData.USER;
import static ru.restaurant.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.restaurant.web.TestUtil.readFromJson;
import static ru.restaurant.web.TestUtil.userHttpBasic;

class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL + '/';

    @Autowired
    MenuService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENU_1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + 0)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MENU_MATCHER.contentJson(MENUS));
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "?date=2020-04-01")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MENU_MATCHER.contentJson(MENUS));
    }

    @Test
    void getOfferByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "offer?date=2020-04-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MENU_TO_MATCHER.contentJson(MENUS_TO));
    }

    @Test
    void createWithLocation() throws Exception {
        Menu menu = new Menu(null, LocalDate.of(3000, 1, 1), RestaurantTestData.REST_1, DishTestData.DISH_1);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menu)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu returned = readFromJson(action, Menu.class);
        menu.setId(returned.getId());

        MENU_MATCHER.assertMatch(service.getAll(), menu, MENU_9, MENU_8, MENU_7, MENU_6, MENU_5, MENU_4, MENU_3, MENU_2, MENU_1);
    }

    @Test
    void createInvalid() throws Exception {
        Menu invalid = new Menu(null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Menu updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        MENU_MATCHER.assertMatch(service.get(MENU_1_ID), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Menu invalid = new Menu(MENU_1_ID, null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL + MENU_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(MENU_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 0)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}