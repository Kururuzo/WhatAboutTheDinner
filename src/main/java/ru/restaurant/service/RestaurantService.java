package ru.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.Restaurant;
import ru.restaurant.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.util.ValidationUtil.checkNotFound;
import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Cacheable("restaurants")
    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.getById(id), id);
    }

    public Restaurant findByName(String name) {
        Assert.notNull(name, "name must not be null");
        return checkNotFound(repository.findByName(name), name);
    }

    public Restaurant findByIdWithDishesByDate(int id, LocalDate date) {
        Restaurant restaurant = repository.findByIdWithDishesByDate(id, date);


        return restaurant;
//        return checkNotFoundWithId(repository.findByIdWithDishesByDate(id, date), id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(Sort.by("id"));
    }

    @CachePut(value = "restaurants", key = "#restaurant")
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", key = "#restaurant")
    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
