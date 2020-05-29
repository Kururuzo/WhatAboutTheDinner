package ru.restaurant.to;

import ru.restaurant.model.Dish;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Objects;

public class DishToForOffer extends BaseTo{
    @NotBlank
    private String name;

    @PositiveOrZero
    private Integer price;

    public DishToForOffer(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getPrice());
    }

    public DishToForOffer() {}

    public DishToForOffer(@NotBlank String name, @PositiveOrZero Integer price) {
        super(null);
        this.name = name;
        this.price = price;
    }

    public DishToForOffer(Integer id, @NotBlank String name, @PositiveOrZero Integer price) {
        super(id);
        this.name = name;
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishToForOffer that = (DishToForOffer) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
