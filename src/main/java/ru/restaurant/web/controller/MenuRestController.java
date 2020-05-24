package ru.restaurant.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.model.MenuItem;
import ru.restaurant.service.MenuService;
import ru.restaurant.to.MenuTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.util.ValidationUtil.assureIdConsistent;
import static ru.restaurant.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/menus";

    private final MenuService service;

    @Autowired
    public MenuRestController(MenuService service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}")
    public MenuItem get(@PathVariable int id) {
        log.info("get menu with id={}", id);
        return service.get(id);
    }

    @GetMapping
    public List<MenuItem> getAll() {
        log.info("get all menus");
        return service.getAll();
    }

    @GetMapping(params = "date")
    public List<MenuItem> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menus by date {}", date);
        return service.getAllByDate(date);
    }

    @GetMapping(value = "/offer", params = "date")
    public List<MenuTo> getOfferByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        date = date != null ? date : LocalDate.now();
        log.info("get offer by date {}", date);
        return service.getOfferByDate(date);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@Valid @RequestBody MenuItem menuItem) {
        log.info("create {}", menuItem);
        checkNew(menuItem);
        MenuItem created = service.create(menuItem);
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable Integer id) {
        log.info("update {} with id={}", menuItem, id);
        assureIdConsistent(menuItem, id);
        service.update(menuItem);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu {}", id);
        service.delete(id);
    }
}
