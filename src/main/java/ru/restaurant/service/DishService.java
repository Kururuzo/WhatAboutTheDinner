package ru.restaurant.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.Dish;
import ru.restaurant.model.Restaurant;
import ru.restaurant.repository.DishRepository;
import ru.restaurant.to.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.getById(id), id);
    }

    public List<Dish> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Dish> getAllByDate(LocalDate date) {
        return repository.findAllByDateOrderByIdDesc(date);
    }

    public List<Dish> findAllByDateAndRestaurantId(LocalDate date, int restaurantId) {
        return checkNotFoundWithId(repository.findAllByDateAndRestaurantIdOrderById(date,restaurantId), restaurantId);
    }

    public OfferTo getOfferByDate(LocalDate date) {
        Map<Restaurant, List<Dish>> restsAndDishesMap = getAllByDate(date).stream()
                .collect(Collectors.groupingBy(Dish::getRestaurant));

        List<RestaurantTo> restaurantToWithDishes = restsAndDishesMap.entrySet().stream().map(en -> new RestaurantTo(
                en.getKey().getId(),
                en.getKey().getName(),
                en.getValue().stream().map(DishToForOffer::new).collect(Collectors.toSet())))
                .sorted(Comparator.comparing(RestaurantTo::getId))
                .collect(Collectors.toList());

        OfferTo offerTo = new OfferTo();
        offerTo.setDate(date);
        offerTo.setRestaurant(restaurantToWithDishes);
        return offerTo;
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
