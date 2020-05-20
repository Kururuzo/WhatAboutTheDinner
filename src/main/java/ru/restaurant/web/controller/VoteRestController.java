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
import ru.restaurant.service.VoteService;
import ru.restaurant.to.VoteResultsTo;
import ru.restaurant.to.VoteTo;
import ru.restaurant.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.restaurant.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/votes";

    @Autowired
    VoteService service;

    @GetMapping(path = "/{id}")
    public Vote get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get vote with id={} fo userId={}", id, authUser.getId());
        return service.get(id, authUser.getId());
    }

    @GetMapping(params = "date")
    public Vote getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                            @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get vote by date {} for user with id={}", date, authUser.getId());
        return service.getByDateAndUserId(date, authUser.getId());
    }

    @GetMapping
    public List<Vote> getUsersAllVotes(@AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get all votes for user with id={}", authUser.getId());
        return service.getAllByUserId(authUser.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> doVote(@RequestBody int restaurantId,
                                         @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("user id:{}, vote for restaurant id:{}", authUser.getId(), restaurantId);
        LocalDate today = LocalDate.now();
        Vote created = service.doVote(today, restaurantId, authUser.getId());

        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(new VoteTo(created));
    }

    @PutMapping(value = "/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer restaurantId,
                       @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("user id:{}, update vote for restaurant id:{}", authUser.getId(), restaurantId);
        LocalDate today = LocalDate.now();
        Vote vote = service.getByDateAndUserId(today, authUser.getId());
        service.updateVote(vote, restaurantId, authUser.getId(), today);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete vote {} for user with id={}", id, authUser.getId());
        service.delete(id, authUser.getId());
    }

    @GetMapping(path = "/results", params = "date")
    public List<VoteResultsTo> getVoteResults(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Get result of voting");
        List<VoteResultsTo> resultByDate = service.getResultByDate(date);
        ValidationUtil.checkIsEmpty(resultByDate, "No one voted on that date");
        return resultByDate.stream().sorted(Comparator.comparing(VoteResultsTo::getVotes)).collect(Collectors.toList());
    }
}
