package ru.restaurant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

//@Entity
//@Table(name = "menus")
public class Menu extends AbstractBaseEntity{

    @Column(name = "localDate", nullable = false)
    private LocalDate localDate;

    @Column(name = "dishes", nullable = false)
    private Set<Dish> dishes;
}
