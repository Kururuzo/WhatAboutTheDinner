package ru.restaurant.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.model.Vote;
import ru.restaurant.repository.VoteRepository;
import ru.restaurant.service.VoteService;
import ru.restaurant.to.VoteTo;
import ru.restaurant.util.ToUtil;
import ru.restaurant.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.restaurant.util.ValidationUtil.assureIdConsistent;
import static ru.restaurant.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteAdminRestController extends AbstractVoteController{

    public static final String REST_URL = "/rest/admin/votes";

    @GetMapping(value = "/{id}")
    public VoteTo get(@PathVariable int id) {
        log.info("get vote with id={}", id);
        return new VoteTo(service.get(id));
    }

    @GetMapping(params = "userId")
    public List<VoteTo> getAllByUserId(@RequestParam int userId) {
        log.info("get all votes");
        return ToUtil.tosFromModel(service.getAllByUserId(userId), VoteTo.class);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createWithLocation(@Valid @RequestBody VoteTo voteTo) {
        log.info("create {}", voteTo);
        checkNew(voteTo);
        Vote created = service.create(getVoteFromTo(voteTo));

        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(new VoteTo(created));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteTo voteTo, @PathVariable Integer id) {
        log.info("update vote {}", voteTo);
        ValidationUtil.assureIdConsistent(voteTo, id);
        Vote vote = getVoteFromTo(voteTo);
        service.update(vote);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu {}", id);
        service.delete(id);
    }

}
