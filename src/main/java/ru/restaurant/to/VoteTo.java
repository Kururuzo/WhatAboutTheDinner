package ru.restaurant.to;

import ru.restaurant.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VoteTo extends BaseTo {
    @NotNull
    private LocalDate date;

    @NotNull
    private Integer restaurantId;

    @NotNull
    private Integer userId;

    public VoteTo(){}

    public VoteTo(Vote vote) {
        this(vote.getId(), vote.getDate(), vote.getRestaurant().getId(), vote.getUser().id());
    }

    public VoteTo(LocalDate date, Integer restaurantId, Integer userId) {
        this.date = date;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public VoteTo(Integer id, LocalDate date, Integer restaurantId, Integer userId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "date=" + date +
                ", restaurantId=" + restaurantId +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}
