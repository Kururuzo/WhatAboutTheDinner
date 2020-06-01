package ru.restaurant.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.AuthorizedUser;
import ru.restaurant.model.Vote;
import ru.restaurant.to.UserTo;
import ru.restaurant.to.VoteResultsTo;
import ru.restaurant.to.VoteTo;
import ru.restaurant.util.ToUtil;
import ru.restaurant.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController extends AbstractVoteController{
    public static final LocalTime CHECK_TIME = LocalTime.of(11, 0);
//    public static final LocalTime CHECK_TIME = LocalTime.of(23, 0);

    @Autowired
    Clock clock;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/votes";

    @GetMapping(path = "/{id}")
    public VoteTo get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get vote with id={} fo userId={}", id, authUser.getId());
        return new VoteTo(service.get(id, authUser.getId()));
    }

    @GetMapping(params = "date")
    public VoteTo getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                            @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get vote by date {} for user with id={}", date, authUser.getId());
        return new VoteTo(service.getByDateAndUserId(date, authUser.getId()));
    }

    @GetMapping
    public List<VoteTo> getUsersAllVotes(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get all votes for user with id={}", authUser.getId());
        return ToUtil.tosFromModel(service.getAllByUserId(authUser.getId()), VoteTo.class);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> doVote(@RequestBody int restaurantId,
                                         @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("user id:{}, vote for restaurant id:{}", authUser.getId(), restaurantId);
        LocalDateTime now = LocalDateTime.now(clock);
        Vote vote = getVoteFromTo(new VoteTo(now.toLocalDate(), restaurantId, authUser.getId()));

        Vote created = service.create(vote);

        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(new VoteTo(created));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id,
                       @Valid @RequestBody VoteTo voteTo,
                       @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("update vote {}", voteTo);
        LocalDateTime now = LocalDateTime.now(clock);
        ValidationUtil.checkIsDateExpired(now.toLocalDate(), voteTo.getDate());
        ValidationUtil.checkIsTimeExpired(now.toLocalTime(), CHECK_TIME);

        ValidationUtil.assureIdConsistent(voteTo, id); //test
        Vote vote = getVoteFromTo(voteTo);
        ValidationUtil.assureIdConsistentBriefly(new UserTo(vote.getUser()), authUser.getId());

        service.update(vote);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete vote {} for user with id={}", id, authUser.getId());
        service.delete(id, authUser.getId());
    }

    //todo VoteResultsTo - not good in service, REDO
    @GetMapping(path = "/results", params = "date")
    public List<VoteResultsTo> getVoteResults(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Get result of voting");
        List<VoteResultsTo> resultByDate = service.getResultByDate(date);
        ValidationUtil.checkIsEmpty(resultByDate, "No one voted on that date");
        return resultByDate.stream().sorted(Comparator.comparing(VoteResultsTo::getVotes)).collect(Collectors.toList());
    }
}
