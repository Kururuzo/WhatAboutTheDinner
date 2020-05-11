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
import ru.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service("menuService")
public class MenuService {
    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public Menu get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    //    @Cacheable("menus")
    public List<Menu> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    //    @Cacheable("menus")
    public List<Menu> getAllByDate(LocalDate date) {
        List<Menu> allByDate = repository.findAllByDate(date);
        allByDate.sort(Comparator.comparing(Menu::getId).reversed());
        return allByDate;
    }

    //    @Cacheable("menus")
    public List<Menu> findByDateWithRestaurants(LocalDate date) {
//    public List<Menu> getOfferByDate findByDateWithRestaurants(LocalDate date) {
//        List<Menu> offerByDate = repository.findAllByDate(date);
//        offerByDate.sort(Comparator.comparing(Menu::getId).reversed());
//        return offerByDate;
//        return repository.findByDateWithDishes(date);
        return repository.findByDateWithRestaurants(date);
    }

    public List<MenuTo> getOfferByDate(LocalDate date) {
        List<Menu> allMenuByDate = findByDateWithRestaurants(date);
        if (allMenuByDate.isEmpty()) {
            throw new NotFoundException("Menus for this date not found.");
        }

        Map<Restaurant, List<Menu>> restsAndMenusMap = allMenuByDate.stream()
                .collect(Collectors.groupingBy(Menu::getRestaurant));

//        List<MenuTo> menuTos = new ArrayList<>();
//
//        restsAndMenusMap.forEach((key, value) -> {
//            List<Dish> dishes = value.stream().map(Menu::getDish).collect(Collectors.toList());
//            menuTos.add(new MenuTo(date, new RestaurantTo(key), dishes));
//        });
//
//        return menuTos.stream()
//                .sorted(Comparator.comparing(menuTo -> menuTo.getRestaurant().getId())).collect(Collectors.toList());
//
        return restsAndMenusMap.entrySet().stream().map(en -> new MenuTo(
                    date,
                    new RestaurantTo(en.getKey()),
                    en.getValue().stream().map(Menu::getDish).collect(Collectors.toList())))
                .sorted(Comparator.comparing(menuTo -> menuTo.getRestaurant().getId()))
                .collect(Collectors.toList());

    }

    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    //    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void update(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        repository.save(menu);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
