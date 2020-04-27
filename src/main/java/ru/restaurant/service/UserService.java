package ru.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.User;
import ru.restaurant.repository.UserRepositoryJpaImpl;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

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
        return checkNotFoundWithId(repository.get(id), id);
    }

//    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

//    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

//    @CacheEvict(value = "users", allEntries = true)
//    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }

//    @CacheEvict(value = "users", allEntries = true)
//    @Transactional
    public void enable(int id, boolean enabled) {
        User user = checkNotFoundWithId(get(id), id);
        user.setEnabled(enabled);
        repository.save(user);  // !! need only for JDBC implementation
    }

//    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
