package ru.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.DishTestData;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.UserTestData;
import ru.restaurant.model.MenuItem;
import ru.restaurant.model.Vote;
import ru.restaurant.repository.MenuRepository;
import ru.restaurant.repository.VoteRepository;
import ru.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.UserTestData.USER_ID;
import static ru.restaurant.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest{

    @Autowired
    VoteService service;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    VoteRepository voteRepository;

    @Test
    void get() throws Exception {
        Vote vote = service.get(VOTE_1_ID, UserTestData.USER_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1, UserTestData.USER_ID));
    }

    @Test
    void getAll() throws Exception {
        List<Vote> all = service.getAll();
        VOTE_MATCHER.assertMatch(all, VOTES);
    }

    @Test
    void create() throws Exception {
        LocalDate nowDate = LocalDate.now();
        MenuItem menuItem = menuRepository.save(new MenuItem(nowDate, RestaurantTestData.REST_1, DishTestData.DISH_1));

        Vote newVote = new Vote();
        Vote created = service.doVote(
                nowDate,
                RestaurantTestData.REST_1_ID,
                UserTestData.USER_ID);
        int newId = created.getId();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, UserTestData.USER_ID), newVote);
    }

    @Test
    void update() throws Exception {
        Vote updated = getUpdated();
        service.update(new Vote(updated));
        Vote actual = service.get(VOTE_1_ID, UserTestData.USER_ID);
        VOTE_MATCHER.assertMatch(actual, updated);
    }

    @Test
    public void delete() throws Exception {
        service.delete(VOTE_1_ID, USER_ID);
        VOTE_MATCHER.assertMatch(service.getAll(), List.of(VOTE_2));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }
}