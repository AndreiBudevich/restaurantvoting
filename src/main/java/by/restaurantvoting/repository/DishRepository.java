package by.restaurantvoting.repository;

import by.restaurantvoting.error.DataConflictException;
import by.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d JOIN d.menus m WHERE m.id=?1 ORDER BY d.name ASC")
    List<Dish> getAllDishesByMenuId(int menuId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=?1 ORDER BY d.name ASC")
    List<Dish> getAllDishesByRestaurantId(int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.id = :id and d.restaurant.id = :restaurantId")
    Optional<Dish> get(int id, int restaurantId);

    default Dish checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Dish id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }
}
