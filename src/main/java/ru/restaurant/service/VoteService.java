package ru.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.*;
import ru.restaurant.repository.VoteRepository;
import ru.restaurant.to.VoteResultsTo;
import ru.restaurant.util.exception.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    public static final LocalTime CHECK_TIME = LocalTime.of(11, 0);
    public static final LocalDate CHECK_DATE = LocalDate.now();

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.findByIdAndUserId(id, userId), id);
    }

    public Vote getByDateAndUserId(LocalDate date, int userId) {
        return repository.findByDateAndUserId(date, userId);
    }

    public List<Vote> getAllByUserId(int userId) {
        List<Vote> all = repository.findAllByUserId(userId);
        return all.stream().sorted(Comparator.comparing(AbstractBaseEntity::getId).reversed())
                .collect(Collectors.toList());
    }

    public List<Vote> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Vote> getAllByDate(LocalDate date) {
        List<Vote> allByDate = repository.findAllByDate(date);
        allByDate.sort(Comparator.comparing(Vote::getId).reversed());
        return allByDate;
    }

    @Transactional
    public Vote create(Vote vote) {
        Assert.notNull(vote, "Vote must not be null");
        if (getByDateAndUserId(vote.getDate(), vote.getUser().getId()) != null) {
            throw new IllegalRequestDataException("You already voted! If your decision changed, please, use PUT method");
        } else {
            return repository.save(vote);
        }
    }

    @Transactional
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<VoteResultsTo> getResultByDate(LocalDate date) {
        return repository.getResultByDate(date);
    }


    //----------------------------- ADMIN --------------------------------
    public Vote get(int id) {
        return checkNotFoundWithId(repository.getById(id), id);
    }

    @Transactional
    public Vote create1(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    @Transactional
    public void update(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        repository.save(vote);
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }


    //----------------------------- Service methods -----------------------------
//    private Vote createVote(int voteId, LocalDate date, int restaurantId, int userId) {
//        Assert.notNull(date, "date must not be null");
//        Restaurant restaurant = restaurantService.get(restaurantId);
//        User user = userService.get(userId);
//        return new Vote(voteId, date, restaurant, user);
//    }
}
