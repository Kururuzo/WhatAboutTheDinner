package ru.restaurant.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.model.Restaurant;
import ru.restaurant.model.User;
import ru.restaurant.model.Vote;
import ru.restaurant.repository.VoteRepository;
import ru.restaurant.service.RestaurantService;
import ru.restaurant.service.UserService;
import ru.restaurant.service.VoteService;
import ru.restaurant.to.VoteTo;

public abstract class AbstractVoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService service;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    protected Vote getVoteFromTo(VoteTo voteTo) {
        Restaurant restaurant = restaurantService.get(voteTo.getRestaurantId());
        User user = userService.get(voteTo.getUserId());
        return new Vote(voteTo.getId(), voteTo.getDate(), restaurant, user);
    }
}
