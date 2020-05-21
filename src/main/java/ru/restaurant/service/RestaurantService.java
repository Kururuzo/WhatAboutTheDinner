package ru.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.Restaurant;
import ru.restaurant.repository.RestaurantRepository;

import java.util.List;

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

    public List<Restaurant> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @CacheEvict(value = "restaurants", key = "#restaurant")
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
