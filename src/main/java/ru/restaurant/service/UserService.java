package ru.restaurant.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.User;
import ru.restaurant.repository.UserRepository;

import java.util.List;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User getWithVotes(int id) {
        return checkNotFoundWithId(repository.getWithVotes(id).orElse(null), id);
    }

//    @Cacheable("users")
    public List<User> getAll() {
        return repository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "name"));
    }

//    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

//    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }

//    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = checkNotFoundWithId(get(id), id);
        user.setEnabled(enabled);
        repository.save(user);  // !! need only for JDBC implementation
    }

//    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
