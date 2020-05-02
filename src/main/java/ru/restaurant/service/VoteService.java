package ru.restaurant.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.restaurant.model.Vote;
import ru.restaurant.repository.VoteRepository;

import java.util.List;

import static ru.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service("voteService")
public class VoteService {
    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    //    @Cacheable("votes")
    public List<Vote> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Vote create (Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    //    @CacheEvict(value = "votes", allEntries = true)
    @Transactional
    public void update (Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        repository.save(vote);
    }

    public void delete (int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }
}
