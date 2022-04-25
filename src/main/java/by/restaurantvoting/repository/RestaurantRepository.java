package by.restaurantvoting.repository;

import by.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph(attributePaths = {"votes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN r.votes v WHERE v.date=?1 or v.restaurant IS NULL ORDER BY r.name ASC")
    List<Restaurant> getWithVote(LocalDate date);

    @Query("SELECT r FROM Restaurant r JOIN r.menus m WHERE m.date=?1 ORDER BY r.name ASC")
    List<Restaurant> getAllWithMenu(LocalDate date);
}

