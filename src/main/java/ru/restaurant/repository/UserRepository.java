package ru.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.restaurant.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    List<User> getAll();
}
