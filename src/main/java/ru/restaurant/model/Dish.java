package ru.restaurant.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes",  uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "restaurant_id"},
        name = "dishes_unique_name_restaurant_idx")})
public class Dish extends AbstractNamedEntity{

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 10_000)
    @NotNull
    private Integer price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(Dish d) {
        this (d.id, d.name, d.price, d.restaurant);
    }

    public Dish() {}
    public Dish(Integer id, String name, Integer price ) {
        super(id, name);
        this.price = price;
    }

    public Dish(@Range(min = 0, max = 10_000) @NotNull Integer price, @NotNull Restaurant restaurant) {
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, @Range(min = 0, max = 10_000) @NotNull Integer price, @NotNull Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
