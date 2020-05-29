package ru.restaurant.to;

import ru.restaurant.model.Restaurant;
import ru.restaurant.util.ToUtil;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RestaurantTo extends BaseTo{
    @NotBlank
    private String name;

    //
    private Set<DishToForOffer> dishes;

    public RestaurantTo(@NotBlank String name, Set<DishToForOffer> dishes) {
        this.name = name;
        this.dishes = dishes;
    }

    public RestaurantTo(Integer id, @NotBlank String name, Set<DishToForOffer> dishes) {
        super(id);
        this.name = name;
        this.dishes = dishes;
    }

    public RestaurantTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public RestaurantTo() {
    }

    public RestaurantTo(Restaurant r) {
        this.id = r.getId();
        this.name = r.getName();
        this.dishes = r.getDishes() != null ? ToUtil.tosFromModelSet(r.getDishes(), DishToForOffer.class) : null;
    }

    public RestaurantTo(Restaurant r, Set<DishToForOffer> dishToForOffer) {
        this.id = r.getId();
        this.name = r.getName();
        this.dishes = dishToForOffer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DishToForOffer> getDishes() {
        return dishes;
    }

    public void setDishes(Set<DishToForOffer> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(dishes, that.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dishes);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
