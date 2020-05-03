package ru.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.restaurant.DishTestData;
import ru.restaurant.VoteTestData;
import ru.restaurant.model.Role;
import ru.restaurant.model.User;
import ru.restaurant.util.Exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.UserTestData.*;


class UserServiceTest extends AbstractServiceTest{

    @Autowired
    UserService service;

    @Test
    void get() throws Exception {
        User user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, ADMIN);
    }

//    @Test
//    void getWithVotes() throws Exception {
//        User user = service.getWithVotes(USER_ID);
//        USER_MATCHER.assertMatch(user, USER);
//        assertEquals(1, user.getVotes().size());
//        VoteTestData.VOTE_MATCHER.assertMatch(user.getVotes().iterator().next(), VoteTestData.VOTE_1);
//    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }

    @Test
    void create() throws Exception {
        User newUser = getNew();
        User created = service.create(new User(newUser));
        int newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    void createWithException() throws Exception {
        //think about
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        service.update(new User(updated));
        USER_MATCHER.assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void enable() throws Exception {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID);
        USER_MATCHER.assertMatch(service.getAll(), List.of(ADMIN));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }

}