package ru.restaurant.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.*;
import ru.restaurant.repository.MenuRepository;
import ru.restaurant.repository.RestaurantRepository;
import ru.restaurant.repository.UserRepository;
import ru.restaurant.repository.VoteRepository;
import ru.restaurant.to.VoteResultsTo;
import ru.restaurant.util.VoteUtil;
import ru.restaurant.util.exception.IllegalRequestDataException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.restaurant.util.ValidationUtil.checkNotFound;
import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository, UserRepository userRepository, MenuRepository menuRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.findByIdAndUserId(id, userId), id);
    }

    public Vote getByDateAndUserId(LocalDate date, int userId) {
        return checkNotFound(repository.findByDateAndUserId(date, userId),
                "Vote for this date not found");
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
    public Vote doVote(LocalDate date, int restaurantId, int userId) {
        Assert.notNull(date, "date must not be null");
        VoteUtil.checkIsDateExpired(date, restaurantId);
        checkNotFound(menuRepository.findByDateAndRestaurantId(date, restaurantId).orElse(null),
                "Menu for this restaurant and date was not found");

        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
        User user = checkNotFoundWithId(userRepository.findById(userId).orElse(null), userId);
        Vote vote = findByDateAndUserId(date, userId);

        if (vote != null) {
            throw new IllegalRequestDataException("You already voted! If your decision changed, please, use PUT method");
        } else {
            vote = new Vote(date, restaurant, user);
            return repository.save(vote);
        }
    }

    @Transactional
    public void updateVote(Vote vote, int restaurantId, int userId, LocalDate date) {
        Assert.notNull(vote, "vote must not be null");
        VoteUtil.checkIsDateExpired(date, restaurantId);
        VoteUtil.checkIsTimeExpired(date, restaurantId);
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
        vote.setRestaurant(restaurant);
        checkNotFoundWithId(repository.save(vote), vote.getId());
    }

    public Vote findByDateAndUserId(LocalDate date, int userId) {
        return repository.findByDateAndUserId(date, userId);
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
    public Vote create(Vote vote) {
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
}
