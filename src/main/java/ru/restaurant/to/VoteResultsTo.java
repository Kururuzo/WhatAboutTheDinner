package ru.restaurant.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class VoteResultsTo {
    @JsonProperty("restaurant")
    private String restaurant;

    @JsonProperty("votes")
    private Long votes;

    public VoteResultsTo() {
    }

    public VoteResultsTo(String restaurant, Long votes) {
        this.restaurant = restaurant;
        this.votes = votes;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "VoteTo:{"
                + "restaurant:" + restaurant
                + ", votes:" + votes
                + "}";
    }
}
