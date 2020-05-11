package ru.restaurant.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.model.User;
import ru.restaurant.service.UserService;
import ru.restaurant.to.UserTo;
import ru.restaurant.util.UserUtil;
import ru.restaurant.util.ValidationUtil;

import java.util.List;

import static ru.restaurant.util.ValidationUtil.*;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }
//
//    public User create(UserTo userTo) {
//        log.info("create from to {}", userTo);
//        return create(UserUtil.createNewFromTo(userTo));
//    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }

}