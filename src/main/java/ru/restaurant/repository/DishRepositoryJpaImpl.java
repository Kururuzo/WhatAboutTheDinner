package ru.restaurant.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.restaurant.model.Dish;
import ru.restaurant.model.User;

import java.util.List;

@Repository
public class DishRepositoryJpaImpl {
    private final DishRepository repository;

    public DishRepositoryJpaImpl(DishRepository repository) {
        this.repository = repository;
    }

    public Dish get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Dish> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Dish save (Dish dish) {
        return repository.save(dish);
    }

    public boolean delete (int id) {
        return repository.delete(id) != 0;
    }


}
