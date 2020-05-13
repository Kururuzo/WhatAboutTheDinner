package ru.restaurant.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    Optional<Menu> findById(int id);

    @Transactional
    @Override
    <S extends Menu> S save(S entity);

    List<Menu> findAllByDate(LocalDate date);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.restaurant LEFT JOIN FETCH m.dish WHERE m.date=:date")
    List<Menu> findByDateWithRestaurants(@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

//    @Query("SELECT m FROM Menu m WHERE m.date = :date AND m.restaurant=:restaurantId")
    Optional<Menu> findByDateAndRestaurantId(@Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                             @Param("restaurantId") int restaurantId);
}
