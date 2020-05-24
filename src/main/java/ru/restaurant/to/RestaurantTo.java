package ru.restaurant.to;

import ru.restaurant.model.AbstractNamedEntity;
import ru.restaurant.model.Restaurant;

public class RestaurantTo extends BaseTo{
    private String name;

    public RestaurantTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public RestaurantTo() {
    }

    public RestaurantTo(Restaurant r) {
        this.id = r.getId();
        this.name = r.getName();
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
