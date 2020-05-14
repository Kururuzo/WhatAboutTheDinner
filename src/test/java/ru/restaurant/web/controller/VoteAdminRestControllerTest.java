package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.VoteTestData;
import ru.restaurant.model.Restaurant;
import ru.restaurant.model.Vote;
import ru.restaurant.service.VoteService;
import ru.restaurant.util.exception.NotFoundException;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.UserTestData.*;
import static ru.restaurant.VoteTestData.*;
import static ru.restaurant.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.restaurant.web.TestUtil.readFromJson;
import static ru.restaurant.web.TestUtil.userHttpBasic;

class VoteAdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteAdminRestController.REST_URL + '/';

    @Autowired
    VoteService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL+ VOTE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "1")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAllByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "byUser/" + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(VOTE_1)));
    }

    @Test
    void createWithLocation() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote))
                .with(userHttpBasic(ADMIN)))
                .andDo(print());

        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    void createInvalid() throws Exception {
        Vote invalid = new Vote(null, new Restaurant(), USER);
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
        Vote updated = VoteTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());
        Vote vote = service.get(VOTE_1_ID);
        VOTE_MATCHER.assertMatch(vote, updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> service.get(VOTE_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

}