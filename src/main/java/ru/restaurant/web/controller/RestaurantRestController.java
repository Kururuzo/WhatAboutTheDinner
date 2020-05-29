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
import ru.restaurant.model.Restaurant;
import ru.restaurant.service.DishService;
import ru.restaurant.service.RestaurantService;
import ru.restaurant.to.DishTo;
import ru.restaurant.to.OfferTo;
import ru.restaurant.to.RestaurantTo;
import ru.restaurant.util.ToUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.util.ValidationUtil.assureIdConsistent;
import static ru.restaurant.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/restaurants";

    final RestaurantService service;

    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public RestaurantTo get(@PathVariable int id) {
        log.info("get restaurant with id={}", id);
        return new RestaurantTo(service.get(id));
    }

    // think about
    //https://stackoverflow.com/questions/7312436/spring-mvc-how-to-get-all-request-params-in-a-map-in-spring-controller
    //!!replace all spaces to '-'
    @GetMapping(params = "restaurant")
    public RestaurantTo getByName(@RequestParam @NotBlank @Size(min = 2, max = 100) String restaurant) {
        restaurant = restaurant.replaceAll("-", " ");
        log.info("get restaurant with name={}", restaurant);
        return new RestaurantTo(service.findByName(restaurant));
    }

    @GetMapping(value = "/{id}", params = {"addDishes, date"})
    public RestaurantTo get(@PathVariable int id,
                            @RequestParam boolean addDishes,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get restaurant with id={}, addDishes={}, date={}", id, addDishes, date);
        return addDishes ? new RestaurantTo(service.findByIdWithDishesByDate(id, date)) :
                new RestaurantTo(service.get(id));
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return ToUtil.tosFromModel(service.getAll(), RestaurantTo.class);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        checkNew(restaurantTo);
        log.info("create restaurant {}", restaurantTo);

        Restaurant created = service.create(new Restaurant(restaurantTo.getId(), restaurantTo.getName()));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(new RestaurantTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update {}", restaurantTo);
        assureIdConsistent(restaurantTo, id);
        service.update(new Restaurant(restaurantTo.getId(), restaurantTo.getName()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish {}", id);
        service.delete(id);
    }
}
