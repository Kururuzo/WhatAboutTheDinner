package ru.restaurant.repository;

import org.springframework.stereotype.Repository;
import ru.restaurant.model.User;

@Repository
public class UserRepositoryJpaImpl {
    private final UserRepository userRepository;

    public UserRepositoryJpaImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(int id) {
        return userRepository.findById(id).orElse(null);
    }

}
