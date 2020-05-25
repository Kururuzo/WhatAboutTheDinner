package ru.restaurant.to;

import ru.restaurant.model.Dish;

import javax.validation.constraints.*;

public class DishTo extends BaseTo{
    @NotBlank
    private String name;

    @PositiveOrZero
    private Integer price;

    @NotNull
    private Integer restaurantId;

    public DishTo(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getPrice(), dish.getRestaurant().getId());
    }

    public DishTo() {}

    public DishTo(@NotBlank String name, @PositiveOrZero Integer price, @NotNull Integer restaurantId) {
        super(null);
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public DishTo(Integer id, @NotBlank String name, @PositiveOrZero Integer price, @NotNull Integer restaurantId) {
        super(id);
        this.name = name;
        this.price = price;
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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
