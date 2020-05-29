package ru.restaurant.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "date", "restaurant_id"},
        name = "dishes_unique_name_date_restaurant_idx")})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 10_000)
    @NotNull
    private Integer price;

    @NotNull
    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(Dish d) {
        this(d.id, d.name, d.price, d.date, d.restaurant);
    }

    public Dish() {
    }

    public Dish(String name, @Range(min = 0, max = 10_000) @NotNull Integer price, @NotNull LocalDate date, @NotNull Restaurant restaurant) {
        this(null, name, price, date, restaurant);
    }

    public Dish(Integer id, String name, @Range(min = 0, max = 10_000) @NotNull Integer price, @NotNull LocalDate date, @NotNull Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
