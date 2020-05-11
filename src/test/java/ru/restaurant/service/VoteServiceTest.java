package ru.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.UserTestData;
import ru.restaurant.model.Vote;
import ru.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest{

    @Autowired
    VoteService service;

    @Test
    void get() throws Exception {
        Vote vote = service.get(VOTE_1_ID);
        VOTE_MATCHER.assertMatch(vote, VOTE_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void getAll() throws Exception {
        List<Vote> all = service.getAll();
        VOTE_MATCHER.assertMatch(all, VOTES);
    }

    @Test
    void create() throws Exception {
        Vote newVote = getNew();
        Vote created = service.create(new Vote(newVote));
        int newId = created.getId();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId), newVote);
    }

    @Test
    void duplicateVoteCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Vote(null,
                        LocalDate.of(2020, Month.APRIL,1),
                        RestaurantTestData.REST_1,
                        UserTestData.USER)));
    }

    @Test
    void createWithException() throws Exception {
        //think about
    }

    @Test
    void update() throws Exception {
        Vote updated = getUpdated();
        service.update(new Vote(updated));
        VOTE_MATCHER.assertMatch(service.get(VOTE_1_ID), updated);
    }

    @Test
    public void delete() throws Exception {
        service.delete(VOTE_1_ID);
        VOTE_MATCHER.assertMatch(service.getAll(), List.of(VOTE_2));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }
}