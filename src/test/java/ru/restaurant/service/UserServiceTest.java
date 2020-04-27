package ru.restaurant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.model.User;

class UserServiceTest extends AbstractServiceTest{

    @Autowired
    UserService userService;

    @Test
    public void get() {
        User user = userService.get(100_000);
        Assertions.assertEquals("user@yandex.ru", user.getEmail());
        Assertions.assertEquals("{noop}password", user.getPassword());
        Assertions.assertTrue(user.isEnabled());
        System.out.println(user);
    }

}