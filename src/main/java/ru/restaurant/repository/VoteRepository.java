package ru.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.model.Vote;
import ru.restaurant.to.VoteTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    Optional<Vote> findById(int id);

    @Transactional
    @Override
    <S extends Vote> S save(S entity);

    List<Vote> findAllByDate(LocalDate date);

    @Query("SELECT new ru.restaurant.to.VoteTo(v.restaurant.name, COUNT(v)) FROM Vote v WHERE v.date=:date GROUP BY v.restaurant.name")
    List<VoteTo> getResultByDate(@Param("date") LocalDate date);

}
