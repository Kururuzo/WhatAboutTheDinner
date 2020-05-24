package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.DishTestData;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.model.MenuItem;
import ru.restaurant.model.Vote;
import ru.restaurant.repository.VoteRepository;
import ru.restaurant.service.MenuService;
import ru.restaurant.service.VoteService;
import ru.restaurant.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.UserTestData.USER;
import static ru.restaurant.VoteTestData.*;
import static ru.restaurant.util.exception.ErrorType.VALIDATION_ERROR;
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
                .andExpect(VOTE_MATCHER.contentJson(List.of(VOTE_1)))
                .andDo(print());
    }

    @Test
    void doVote() throws Exception {
        LocalDate nowDate = LocalDate.now();
        menuService.create(new MenuItem(nowDate, DishTestData.DISH_1));

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(RestaurantTestData.REST_1_ID)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void doDuplicateVote() throws Exception {
        LocalDate nowDate = LocalDate.now();
        MenuItem menuItem1 = menuService.create(new MenuItem(nowDate, DishTestData.DISH_1));
        Vote newVote = voteRepository.save(new Vote(nowDate, RestaurantTestData.REST_1, USER));

        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(RestaurantTestData.REST_1_ID)))
                .andDo(print())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateVoteForUser() throws Exception {
        if(LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            LocalDate nowDate = LocalDate.now();
            MenuItem menu1 = menuService.create(new MenuItem(nowDate, DishTestData.DISH_1));
            Vote newVote = voteRepository.save(new Vote(nowDate, RestaurantTestData.REST_1, USER));

            MenuItem menu2 = menuService.create(new MenuItem(nowDate, DishTestData.DISH_2));

            perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.REST_2.getId())
                    .with(userHttpBasic(USER))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            Vote updated = voteRepository.getOne(newVote.getId());
            newVote.setRestaurant(RestaurantTestData.REST_2);
            VOTE_MATCHER.assertMatch(updated, newVote);
        } else {
            LocalDate nowDate = LocalDate.now();
            voteRepository.save(new Vote(nowDate, RestaurantTestData.REST_1, USER));

            perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.REST_2.getId())
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