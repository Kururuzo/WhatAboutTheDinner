package ru.restaurant.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.HasId;
import ru.restaurant.model.Menu;
import ru.restaurant.repository.MenuRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

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

    public Menu create (Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    //    @CacheEvict(value = "menus", allEntries = true)
    @Transactional
    public void update (Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        repository.save(menu);
    }

    public void delete (int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
