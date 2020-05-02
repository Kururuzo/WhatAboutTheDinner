package ru.restaurant;

import ru.restaurant.model.Vote;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.restaurant.RestaurantTestData.*;
import static ru.restaurant.UserTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.*;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingEquals(Vote.class);

    public static final int VOTE_1_ID = START_SEQ + 25;

    public static final Vote VOTE_1 = new Vote(START_SEQ + 25, LocalDate.of(2020, Month.APRIL,1), REST_1, USER);
    public static final Vote VOTE_2 = new Vote(START_SEQ + 26, LocalDate.of(2020, Month.APRIL,1), REST_1, ADMIN);

    public static final List<Vote> VOTES = List.of(VOTE_2, VOTE_1);

    public static Vote getNew(){
        return new Vote(null, LocalDate.of(2020, Month.APRIL,2), REST_2, ADMIN);
    }

    public static Vote getUpdated() {
        Vote vote = new Vote(VOTE_1);
        vote.setDate(LocalDate.of(2020, Month.MAY,9));
        vote.setRestaurant(REST_3);
        vote.setUser(ADMIN);
        return vote;
    }
}
