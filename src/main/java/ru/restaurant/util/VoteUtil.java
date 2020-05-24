package ru.restaurant.util;

import ru.restaurant.model.Vote;
import ru.restaurant.to.VoteTo;
import ru.restaurant.util.exception.DateTimeExpiredException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    public static final LocalTime CHECK_TIME = LocalTime.of(11, 0);

    private VoteUtil() {
    }

    public static void checkIsDateAndTimeExpired(LocalDate date, LocalDateTime now,  int restaurantId) {
        boolean expired = now.toLocalTime().isAfter(CHECK_TIME);
        checkIsDateExpired(date, now, restaurantId);
        if (expired) {
            throw new DateTimeExpiredException("Voting time for restaurantId=" + restaurantId + " is expired");
        }
    }

    public static void checkIsDateExpired(LocalDate date, LocalDateTime now, int restaurantId) {
        if (!date.equals(now.toLocalDate())) {
            throw new DateTimeExpiredException("Voting date for restaurantId=" + restaurantId + " is in the past");
        }
    }

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream().map(VoteTo::new).collect(Collectors.toList());
    }
}
