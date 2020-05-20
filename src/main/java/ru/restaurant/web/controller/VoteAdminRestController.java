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
import ru.restaurant.service.VoteService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.restaurant.util.ValidationUtil.assureIdConsistent;
import static ru.restaurant.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteAdminRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/admin/votes";

    @Autowired
    VoteService service;

    @GetMapping(value = "/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote with id={}", id);
        return service.get(id);
    }

    @GetMapping(params = "userId")
    public List<Vote> getAllByUserId(@RequestParam int userId) {
        log.info("get all votes");
        return service.getAllByUserId(userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody Vote vote) {
        log.info("create {}", vote);
        checkNew(vote);
        Vote created = service.create(vote);
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Vote vote, @PathVariable Integer id) {
        log.info("update {} with id={}", vote, id);
        assureIdConsistent(vote, id);
        service.update(vote);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu {}", id);
        service.delete(id);
    }

}
