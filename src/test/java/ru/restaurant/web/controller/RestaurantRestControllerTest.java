package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.model.Restaurant;
import ru.restaurant.service.RestaurantService;
import ru.restaurant.util.Exception.NotFoundException;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.RestaurantTestData.*;
import static ru.restaurant.web.TestUtil.readFromJson;

class RestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Autowired
    RestaurantService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST_1_ID)
//                .with(userHttpBasic(ADMIN))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(REST_MATCHER.contentJson(REST_1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
//                .with(userHttpBasic(ADMIN))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(REST_MATCHER.contentJson(RESTAURANTS));
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newDish = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newDish))
//                .with(userHttpBasic(ADMIN))
        );

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.id();
        newDish.setId(newId);
        REST_MATCHER.assertMatch(created, newDish);
        REST_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + REST_1_ID).contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated))
//                .with(userHttpBasic(ADMIN))
        )
                .andExpect(status().isNoContent());

        REST_MATCHER.assertMatch(service.get(REST_1_ID), updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + REST_1_ID)
//                .with(userHttpBasic(USER))
        )
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(REST_1_ID));
    }
}