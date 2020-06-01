package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.VoteTestData;
import ru.restaurant.repository.VoteRepository;
import ru.restaurant.service.VoteService;
import ru.restaurant.to.VoteTo;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.UserTestData.*;
import static ru.restaurant.VoteTestData.*;
import static ru.restaurant.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.restaurant.web.TestUtil.readFromJson;
import static ru.restaurant.web.TestUtil.userHttpBasic;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    VoteService service;

    @Autowired
    VoteRepository voteRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_1_TO));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getVoteByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "?date=2020-04-01")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_1_TO));
    }

    @Test
    void getUsersAllVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of(VOTE_1_TO)))
                .andDo(print());
    }

    @Test
    void doVote() throws Exception {
        VoteTo newVoteTo = VoteTestData.getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER_2))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(newVoteTo.getRestaurantId())))
                .andDo(print())
                .andExpect(status().isCreated());

        VoteTo created = readFromJson(action, VoteTo.class);
        int newId = created.id();
        newVoteTo.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVoteTo);
        VOTE_TO_MATCHER.assertMatch(new VoteTo(service.get(newId)), newVoteTo);
    }

    @Test
    void doDuplicateVote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(RestaurantTestData.REST_1_ID)))
                .andDo(print())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateVoteForUser() throws Exception {
        VoteTo newVote = VoteTestData.getUpdatedToForUser();
        perform(MockMvcRequestBuilders.put(REST_URL + newVote.getId())
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isNoContent())
                .andDo(print());

        VoteTo updated = new VoteTo(service.getByDateAndUserId(newVote.getDate(), newVote.getUserId()));
        VOTE_TO_MATCHER.assertMatch(updated, newVote);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
    }

    @Test
    void getResults() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "results?date=2020-04-01")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(VOTERESULTS_TO_MATCHER.contentJson(List.of(VOTE_RESULTS_TO)));
    }
}