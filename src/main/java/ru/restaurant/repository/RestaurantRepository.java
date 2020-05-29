package ru.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    Restaurant getById(int id);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE CONCAT('%',:name,'%') ORDER BY r.id")
    Restaurant findByName(@Param("name") String name);

    @Transactional
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE r.id=:id AND d.date=:date ORDER BY r.id")
    Restaurant findByIdWithDishesByDate(@Param("id")int id, @Param("date")LocalDate date);

    @Transactional
    @Override
    <S extends Restaurant> S save(S entity);
}
