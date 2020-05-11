package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.UserTestData;
import ru.restaurant.VoteTestData;
import ru.restaurant.model.Vote;
import ru.restaurant.service.VoteService;
import ru.restaurant.util.Exception.NotFoundException;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.VoteTestData.*;
import static ru.restaurant.web.TestUtil.readFromJson;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    VoteService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID)
//                .with(userHttpBasic(ADMIN))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
//                .with(userHttpBasic(ADMIN))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTES));
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "?date=2020-04-01")
//                .with(userHttpBasic(ADMIN))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(VOTE_MATCHER.contentJson(VOTES));
    }

    @Test
    void getResults() throws Exception {
                perform(MockMvcRequestBuilders.get(REST_URL + "results?date=2020-04-01")
//                .with(userHttpBasic(ADMIN))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of(VOTE_TO_1)));
    }

    @Test
    void createWithLocation() throws Exception {
        Vote vote = new Vote(null, LocalDate.of(3000, 1, 1), RestaurantTestData.REST_1, UserTestData.USER);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
//                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote returned = readFromJson(action, Vote.class);
        vote.setId(returned.getId());

        VOTE_MATCHER.assertMatch(service.getAll(), vote, VOTE_2, VOTE_1);
    }

    @Test
    void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated))
//                .with(userHttpBasic(ADMIN))
        )
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(service.get(VOTE_1_ID), updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_1_ID)
//                .with(userHttpBasic(ADMIN))
        )
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(VOTE_1_ID));
    }


}