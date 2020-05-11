package ru.restaurant.to;

import ru.restaurant.model.AbstractNamedEntity;
import ru.restaurant.model.Restaurant;

public class RestaurantTo extends AbstractNamedEntity{

    public RestaurantTo() {
    }

    public RestaurantTo(Restaurant r) {
        this.id = r.getId();
        this.name = r.getName();
    }

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
