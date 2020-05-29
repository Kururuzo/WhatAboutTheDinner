package ru.restaurant.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant.service.DishService;
import ru.restaurant.to.OfferTo;

import java.time.LocalDate;

@RestController
@RequestMapping(value = OfferController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OfferController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/offer";

    final
    DishService dishService;

    public OfferController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public OfferTo getOfferByDate(@RequestParam(value = "date", required = false,
            defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get offer by date {}", date);
        return dishService.getOfferByDate(date);
    }
}
