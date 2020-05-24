package ru.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.model.MenuItem;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<MenuItem, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM MenuItem m WHERE m.id=:id")
    int delete(@Param("id") int id);

    MenuItem getById(int id);

    @Transactional
    @Override
    <S extends MenuItem> S save(S entity);

    List<MenuItem> findAllByDateOrderByIdDesc(LocalDate date);

    @Query("SELECT m FROM MenuItem m LEFT JOIN FETCH m.restaurant LEFT JOIN FETCH m.dish WHERE m.date=:date")
    List<MenuItem> findByDateWithRestaurants(@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    MenuItem findByDateAndRestaurantId(@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                       @Param("restaurantId") int restaurantId);
}
