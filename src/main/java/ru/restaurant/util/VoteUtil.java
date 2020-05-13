package ru.restaurant.util;

import ru.restaurant.model.Vote;
import ru.restaurant.to.VoteTo;
import ru.restaurant.util.exception.DateTimeExpiredException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
//    public static final LocalTime CHECK_TIME = LocalTime.of(11, 0);
    public static final LocalTime CHECK_TIME = LocalTime.of(23, 0);

    private VoteUtil() {
    }

    public static void checkIsTimeExpired(LocalDate date, int restaurantId) {
        boolean expired = LocalTime.now().isAfter(CHECK_TIME);
//        checkIsDateExpired(date, restaurantId);
        if (expired) {
            throw new DateTimeExpiredException("Voting time for restaurantId=" + restaurantId + " is expired");
        }
    }

    public static void checkIsDateExpired(LocalDate date, int restaurantId) {
        LocalDate today = LocalDate.now();
        if (!date.equals(today)) {
            throw new DateTimeExpiredException("Voting date for restaurantId=" + restaurantId + " is in the past");
        }
    }

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream().map(VoteTo::new).collect(Collectors.toList());
    }
}
