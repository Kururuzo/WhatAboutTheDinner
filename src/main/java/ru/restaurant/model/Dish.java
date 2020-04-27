package ru.restaurant.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity{

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 10_000)
    @NotNull
    private Integer price;

    public Dish() {}

        public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
