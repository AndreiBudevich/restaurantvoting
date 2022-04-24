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
    @Query("SELECT r FROM Restaurant r JOIN r.votes v WHERE v.date=?1")
    List<Restaurant> getWithVote(LocalDate date);

    @Query("SELECT r FROM Restaurant r JOIN r.menus m WHERE m.date=?1")
    List<Restaurant> getAll(LocalDate date);
}
