package ru.restaurant.to;

import ru.restaurant.model.Dish;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class DishTo extends BaseTo{
    @NotBlank
    private String name;

    @PositiveOrZero
    private Integer price;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer restaurantId;

    public DishTo(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getPrice(), dish.getDate(), dish.getRestaurant().getId());
    }

    public DishTo() {}

    public DishTo(@NotBlank String name, @PositiveOrZero Integer price, @NotNull LocalDate date, @NotNull Integer restaurantId) {
        super(null);
        this.name = name;
        this.price = price;
        this.date = date;
        this.restaurantId = restaurantId;
    }

    public DishTo(Integer id, @NotBlank String name, @PositiveOrZero Integer price, @NotNull LocalDate date, @NotNull Integer restaurantId) {
        super(id);
        this.name = name;
        this.price = price;
        this.date = date;
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
