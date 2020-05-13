package ru.restaurant.to;

import ru.restaurant.model.Vote;

import java.time.LocalDate;

public class VoteTo extends BaseTo {
    private LocalDate date;
    private RestaurantTo restaurant;
    private UserForVoteTo user;

    public VoteTo(){}

    public VoteTo(LocalDate date, RestaurantTo restaurant, UserForVoteTo user) {
        this.date = date;
        this.restaurant = restaurant;
        this.user = user;
    }

    public VoteTo(Integer id, LocalDate date, RestaurantTo restaurant, UserForVoteTo user) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.user = user;
    }

    public VoteTo(Vote vote){
        super.id = vote.getId();
        this.date = vote.getDate();
        this.restaurant = new RestaurantTo(vote.getRestaurant());
        this.user = new UserForVoteTo(vote.getUser());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public RestaurantTo getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantTo restaurant) {
        this.restaurant = restaurant;
    }

    public UserForVoteTo getUser() {
        return user;
    }

    public void setUser(UserForVoteTo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "date=" + date +
                ", restaurant=" + restaurant +
                ", user=" + user +
                ", id=" + id +
                '}';
    }
}
