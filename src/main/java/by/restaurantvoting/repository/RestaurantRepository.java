package by.restaurantvoting.repository;

import by.restaurantvoting.model.Restaurant;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@CacheConfig(cacheNames = "restaurants")
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Cacheable
    @Query("SELECT r FROM Restaurant r JOIN r.menus m WHERE m.menuDate=?1 ORDER BY r.name ASC")
    List<Restaurant> getAllWithMenu(LocalDate date);
}
