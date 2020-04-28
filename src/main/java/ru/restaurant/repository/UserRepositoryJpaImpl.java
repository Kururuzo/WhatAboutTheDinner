package ru.restaurant.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.restaurant.model.User;

import java.util.List;

@Repository
public class UserRepositoryJpaImpl {
    private final UserRepository repository;

    public UserRepositoryJpaImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public User get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<User> getAll() {
        return repository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "name"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }
}
