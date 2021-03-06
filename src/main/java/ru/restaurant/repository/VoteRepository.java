package ru.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.model.User;
import ru.restaurant.model.Vote;
import ru.restaurant.to.VoteResultsTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Override
    <S extends Vote> S save(S entity);

    Vote getById(int id);

    List<Vote> findAllByDate(LocalDate date);
    List<Vote> findAllByUserId(int userId);

    Vote findByIdAndUserId(int id, int userId);
    @Query("SELECT v FROM Vote v WHERE v.date=:date AND v.user.id=:userId")
    Vote findByDateAndUserId(@Param("date") LocalDate date, @Param("userId")Integer userId);

    @Query("SELECT new ru.restaurant.to.VoteResultsTo(v.restaurant.name, COUNT(v)) FROM Vote v WHERE v.date=:date GROUP BY v.restaurant.name")
    List<VoteResultsTo> getResultByDate(@Param("date") LocalDate date);

}
