package ru.restaurant.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.restaurant.model.User;

import java.util.List;

@Repository
public class UserRepositoryJpaImpl {
    private final UserRepository userRepository;

    public UserRepositoryJpaImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "name"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean delete(int id) {
        return userRepository.delete(id) != 0;
    }
}
