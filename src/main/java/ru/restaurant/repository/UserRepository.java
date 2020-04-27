package ru.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

}
