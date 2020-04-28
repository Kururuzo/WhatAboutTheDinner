package ru.restaurant.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;
import ru.restaurant.model.Dish;
import ru.restaurant.repository.DishRepositoryJpaImpl;

import java.util.List;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Service("dishService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DishService {
    private final DishRepositoryJpaImpl repository;

    public DishService(DishRepositoryJpaImpl repository) {
        this.repository = repository;
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

//    @Cacheable("dishes")
    public List<Dish> getAll() {
        return repository.getAll();
    }

    public Dish create (Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

//    @CacheEvict(value = "dishes", allEntries = true)
    public void update (Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        repository.save(dish);
    }

    public void delete (int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
