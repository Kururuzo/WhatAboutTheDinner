package ru.restaurant.to;

import ru.restaurant.model.AbstractBaseEntity;
import ru.restaurant.model.Dish;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class MenuTo extends BaseTo {

    @NotNull
    private LocalDate date;

    @NotNull
    private RestaurantTo restaurant;

    @NotNull
    private List<Dish> dishes;

    public MenuTo() {
    }

    public MenuTo(@NotNull LocalDate date, @NotNull RestaurantTo restaurant, @NotNull List<Dish> dishes) {
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

    public MenuTo(Integer id, @NotNull LocalDate date, @NotNull RestaurantTo restaurant, @NotNull List<Dish> dishes) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.dishes = dishes;
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

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "date=" + date +
                ", restaurant=" + restaurant +
                ", dishes=" + dishes +
                '}';
    }
}
