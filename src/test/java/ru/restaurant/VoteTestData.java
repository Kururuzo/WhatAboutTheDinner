package ru.restaurant;

import ru.restaurant.model.Vote;
import ru.restaurant.to.VoteResultsTo;
import ru.restaurant.to.VoteTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.restaurant.RestaurantTestData.*;
import static ru.restaurant.UserTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.*;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingEquals(Vote.class);
    public static TestMatcher<VoteResultsTo> VOTERESULTS_TO_MATCHER = TestMatcher.usingFieldsComparator(VoteResultsTo.class);
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingFieldsComparator(VoteTo.class, "user");

    public static final int VOTE_1_ID = START_SEQ + 29;

    public static final Vote VOTE_1 = new Vote(START_SEQ + 29, LocalDate.of(2020, Month.APRIL,1), REST_1, USER);
    public static final Vote VOTE_2 = new Vote(START_SEQ + 30, LocalDate.of(2020, Month.APRIL,1), REST_1, ADMIN);

    public static final Vote VOTE_UPDATABLE_1 = new Vote(START_SEQ + 31, LocalDate.now(), REST_1, USER);

    public static final List<Vote> VOTES = List.of(VOTE_2, VOTE_1);

    public static final VoteTo VOTE_1_TO = new VoteTo(VOTE_1);

    public static final VoteResultsTo VOTE_RESULTS_TO = new VoteResultsTo("Lucky Pizza", 2L);

    public static Vote getNew(){
        return new Vote(null, LocalDate.of(2020, Month.APRIL,2), REST_2, ADMIN);
    }

    public static Vote getUpdated() {
        Vote vote = new Vote(VOTE_1);
        vote.setDate(LocalDate.now());
        vote.setRestaurant(REST_3);
        vote.setUser(USER);
        return vote;
    }
}
