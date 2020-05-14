package ru.restaurant.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.Dish;
import ru.restaurant.repository.DishRepository;

import java.util.List;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service("dishService")
public class DishService {
    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Dish> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Dish create (Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    @Transactional
    public void update (Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish), dish.getId());
    }

    @Transactional
    public void delete (int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
