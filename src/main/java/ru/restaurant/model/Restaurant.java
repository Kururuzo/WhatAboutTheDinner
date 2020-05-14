package ru.restaurant.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;
import org.hibernate.annotations.Cache;
import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "restaurants",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private Set<Menu> menus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private Set<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName());
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = CollectionUtils.isEmpty(menus) ? Collections.emptySet() : menus;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = CollectionUtils.isEmpty(votes) ? Collections.emptySet() : votes;
    }
}
