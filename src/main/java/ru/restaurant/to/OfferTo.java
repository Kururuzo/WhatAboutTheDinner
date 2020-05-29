package ru.restaurant.to;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class OfferTo extends BaseTo {

    @NotNull
    private LocalDate date;

    @NotNull
    private List<RestaurantTo> restaurants;

    public OfferTo() {
    }

    public OfferTo(@NotNull LocalDate date, @NotNull List<RestaurantTo> restaurants) {
        this.date = date;
        this.restaurants = restaurants;
    }

    public OfferTo(Integer id, @NotNull LocalDate date, @NotNull List<RestaurantTo> restaurants) {
        super(id);
        this.date = date;
        this.restaurants = restaurants;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<RestaurantTo> getRestaurants() {
        return restaurants;
    }

    public void setRestaurant(List<RestaurantTo> restaurants) {
        this.restaurants = restaurants;
    }

    //    public List<DishToForOffer> getDishes() {
//        return dishes;
//    }
//
//    public void setDishes(List<DishToForOffer> dishes) {
//        this.dishes = dishes;
//    }

    @Override
    public String toString() {
        return "OfferTo{" +
                "date=" + date +
                ", restaurants=" + restaurants +
//                ", dishes=" + dishes +
                '}';
    }
}
