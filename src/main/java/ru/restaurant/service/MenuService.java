package ru.restaurant.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.MenuItem;
import ru.restaurant.model.Restaurant;
import ru.restaurant.repository.MenuRepository;
import ru.restaurant.to.MenuTo;
import ru.restaurant.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {
    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    @Cacheable("menuItem")
    public MenuItem get(int id) {
        return checkNotFoundWithId(repository.getById(id), id);
    }

//    public MenuItem findByDateAndRestaurantId(LocalDate date, int restaurantId) {
//        return checkNotFoundWithId(repository.findByDate(date,restaurantId), restaurantId);
//    }

    public List<MenuItem> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<MenuItem> getAllByDate(LocalDate date) {
        return repository.findAllByDateOrderByIdDesc(date);
    }

    public List<MenuItem> findByDateWithRestaurants(LocalDate date) {
        return repository.findByDateWithRestaurants(date);
    }

    @Cacheable("offer")
    public List<MenuTo> getOfferByDate(LocalDate date) {
        List<MenuItem> allMenuItemByDate = findByDateWithRestaurants(date);
//        ValidationUtil.checkIsEmpty(allMenuByDate, "Menus for this date not found.");

        Map<Restaurant, List<MenuItem>> restsAndMenusMap = allMenuItemByDate.stream()
                .collect(Collectors.groupingBy(menuItem -> menuItem.getDish().getRestaurant()));

        return restsAndMenusMap.entrySet().stream().map(en -> new MenuTo(
                date,
                new RestaurantTo(en.getKey()),
                en.getValue().stream().map(MenuItem::getDish).collect(Collectors.toList())))
                .sorted(Comparator.comparing(menuTo -> menuTo.getRestaurant().getId()))
                .collect(Collectors.toList());
    }

    @CacheEvict(value = {"offer", "menuItem"}, key = "#menuItem")
    @Transactional
    public MenuItem create(MenuItem menuItem) {
        Assert.notNull(menuItem, "menu must not be null");
        return repository.save(menuItem);
    }

    @CacheEvict(value = {"offer", "menuItem"}, key = "#menuItem")
    @Transactional
    public void update(MenuItem menuItem) {
        Assert.notNull(menuItem, "menu must not be null");
        repository.save(menuItem);
    }

    @CacheEvict(value = "menuItem", allEntries = true)
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
