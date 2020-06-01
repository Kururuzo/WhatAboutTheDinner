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
import ru.restaurant.model.Dish;
import ru.restaurant.model.Restaurant;
import ru.restaurant.service.DishService;
import ru.restaurant.service.RestaurantService;
import ru.restaurant.to.DishTo;
import ru.restaurant.util.ToUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.util.ValidationUtil.assureIdConsistent;
import static ru.restaurant.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/dishes";

    @Autowired
    DishService service;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping(value = "/{id}")
    public DishTo get(@PathVariable int id) {
        log.info("get dish with id={}", id);
        return new DishTo(service.get(id));
    }

    @GetMapping
    public List<DishTo> getAll() {
        log.info("getAll");
        return ToUtil.tosFromModel(service.getAll(), DishTo.class);
    }

    @GetMapping(params = "date")
    public List<DishTo> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get DishTo by date {}", date);
        return ToUtil.tosFromModel(service.getAllByDate(date), DishTo.class);
    }

    @GetMapping(params = {"date", "restaurantId"})
    public List<DishTo> findAllByDateAndRestaurantId(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, int restaurantId) {
        log.info("get DishTo by date {} and restaurantId {}", date, restaurantId);
        return ToUtil.tosFromModel(service.findAllByDateAndRestaurantId(date, restaurantId), DishTo.class);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@Valid @RequestBody DishTo dishTo) {
        checkNew(dishTo);
        log.info("create {}", dishTo);
        Dish created = service.create(getDishFromTo(dishTo));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(new DishTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update {}", dishTo);
        assureIdConsistent(dishTo, id);
        service.update(getDishFromTo(dishTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish {}", id);
        service.delete(id);
    }

    //Service methods
    //todo move to service layer
    private Dish getDishFromTo (DishTo dishTo) {
        Restaurant restaurant = restaurantService.get(dishTo.getRestaurantId());
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), dishTo.getDate(), restaurant);
    }
}
