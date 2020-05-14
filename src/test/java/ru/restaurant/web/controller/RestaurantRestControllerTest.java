package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.model.Restaurant;
import ru.restaurant.service.RestaurantService;
import ru.restaurant.util.exception.NotFoundException;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.RestaurantTestData.*;
import static ru.restaurant.UserTestData.ADMIN;
import static ru.restaurant.UserTestData.USER;
import static ru.restaurant.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.restaurant.web.TestUtil.readFromJson;
import static ru.restaurant.web.TestUtil.userHttpBasic;

class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Autowired
    RestaurantService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST_1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(REST_MATCHER.contentJson(REST_1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
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
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(REST_MATCHER.contentJson(RESTAURANTS));
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newDRest = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDRest))
                .with(userHttpBasic(ADMIN)))
                .andDo(print());

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.id();
        newDRest.setId(newId);
        REST_MATCHER.assertMatch(created, newDRest);
        REST_MATCHER.assertMatch(service.get(newId), newDRest);
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, "");
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
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + REST_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        REST_MATCHER.assertMatch(service.get(REST_1_ID), updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(REST_1);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + REST_1_ID)
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
        perform(MockMvcRequestBuilders.delete(REST_URL + REST_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(REST_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 0)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}