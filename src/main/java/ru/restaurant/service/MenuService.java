package ru.restaurant.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.Menu;
import ru.restaurant.model.Restaurant;
import ru.restaurant.repository.MenuRepository;
import ru.restaurant.to.MenuTo;
import ru.restaurant.to.RestaurantTo;
import ru.restaurant.util.ValidationUtil;

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

    public Menu get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public List<Menu> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Menu> getAllByDate(LocalDate date) {
        List<Menu> allByDate = repository.findAllByDate(date);
        allByDate.sort(Comparator.comparing(Menu::getId).reversed());
        return allByDate;
    }

    public List<Menu> findByDateWithRestaurants(LocalDate date) {
        return repository.findByDateWithRestaurants(date);
    }

    public List<MenuTo> getOfferByDate(LocalDate date) {
        List<Menu> allMenuByDate = findByDateWithRestaurants(date);
        ValidationUtil.checkIsEmpty(allMenuByDate, "Menus for this date not found.");

        Map<Restaurant, List<Menu>> restsAndMenusMap = allMenuByDate.stream()
                .collect(Collectors.groupingBy(Menu::getRestaurant));

        return restsAndMenusMap.entrySet().stream().map(en -> new MenuTo(
                date,
                new RestaurantTo(en.getKey()),
                en.getValue().stream().map(Menu::getDish).collect(Collectors.toList())))
                .sorted(Comparator.comparing(menuTo -> menuTo.getRestaurant().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    @Transactional
    public void update(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        repository.save(menu);
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
