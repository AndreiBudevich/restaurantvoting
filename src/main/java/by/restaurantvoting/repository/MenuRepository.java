package by.restaurantvoting.repository;

import by.restaurantvoting.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=?1 ORDER BY m.menuDate DESC")
    List<Menu> getAllMenusByRestaurantsId(int restaurantId);

    @EntityGraph(attributePaths = {"dishes", "restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.id=?1")
    Optional<Menu> getWithDishes(int id);
}

