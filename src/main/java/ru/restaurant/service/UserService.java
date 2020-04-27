package ru.restaurant.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.restaurant.model.User;
import ru.restaurant.repository.UserRepositoryJpaImpl;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

    private final UserRepositoryJpaImpl repository;

    public UserService(UserRepositoryJpaImpl repository) {
        this.repository = repository;
    }

    public User get(int id) {
        return repository.get(id);
    }
}
