package ru.restaurant.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "menuItems",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "dish_id"},
                name = "menus_unique_date_dish_idx")}
)
public class MenuItem extends AbstractBaseEntity {

    @NotNull
    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dish dish;

    public MenuItem() {
    }

    public MenuItem(MenuItem m) {
        this(m.getId(), m.getDate(), m.getDish());
    }

    public MenuItem(@NotNull LocalDate date, @NotNull Dish dish) {
        this.date = date;
        this.dish = dish;
    }

    public MenuItem(Integer id, @NotNull LocalDate date, @NotNull Dish dish) {
        super(id);
        this.date = date;
        this.dish = dish;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
