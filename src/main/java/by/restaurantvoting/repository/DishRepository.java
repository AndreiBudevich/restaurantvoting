package by.restaurantvoting.repository;

import by.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT d FROM Dish d WHERE d.id=?1")
    Dish getWithMenu(int id);

    @Query("SELECT d FROM Dish d JOIN d.menus m WHERE m.id=?1 ORDER BY d.name ASC")
    List<Dish> getAllDishesByMenuId(int menuId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=?1 ORDER BY d.name ASC")
    List<Dish> getAllDishesByRestaurantId(int restaurantId);
}
