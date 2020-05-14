package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.DishTestData;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.VoteTestData;
import ru.restaurant.model.Menu;
import ru.restaurant.model.Vote;
import ru.restaurant.repository.VoteRepository;
import ru.restaurant.service.MenuService;
import ru.restaurant.service.VoteService;
import ru.restaurant.to.VoteTo;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.UserTestData.USER;
import static ru.restaurant.VoteTestData.*;
import static ru.restaurant.web.TestUtil.readFromJson;
import static ru.restaurant.web.TestUtil.userHttpBasic;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    VoteService service;

    @Autowired
    MenuService menuService;

    @Autowired
    VoteRepository voteRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_1));
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
                .andExpect(VOTE_MATCHER.contentJson(List.of(VOTE_1)));
    }

    @Test
    void getUsersAllVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(VOTE_1)));
    }

    @Test
    void doVote() throws Exception {
        LocalDate nowDate = LocalDate.now();
        menuService.create(new Menu(nowDate, RestaurantTestData.REST_1, DishTestData.DISH_1));

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.REST_1_ID)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void UpdateVoteForUser() throws Exception {
        if(LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            LocalDate nowDate = LocalDate.now();
            Menu menu1 = menuService.create(new Menu(nowDate, RestaurantTestData.REST_1, DishTestData.DISH_1));
            Vote newVote = voteRepository.save(new Vote(nowDate, RestaurantTestData.REST_1, USER));

            Menu menu2 = menuService.create(new Menu(nowDate, RestaurantTestData.REST_2, DishTestData.DISH_2));

            ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.REST_2.getId())
                    .with(userHttpBasic(USER))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated());

            VoteTo returned = readFromJson(action, VoteTo.class);
            newVote.setId(returned.getId());
            VOTE_TO_MATCHER.assertMatch(new VoteTo(newVote), returned);

        } else {
            LocalDate nowDate = LocalDate.now();
            voteRepository.save(new Vote(nowDate, RestaurantTestData.REST_1, USER));

            ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.REST_2.getId())
                    .with(userHttpBasic(USER))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
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