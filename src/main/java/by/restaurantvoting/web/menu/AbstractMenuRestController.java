package by.restaurantvoting.web.menu;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.repository.DishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class AbstractMenuRestController {

    @Autowired
    DishRepository dishRepository;

    public List<Dish> get(int restaurantId, int id) {
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return dishRepository.getAllDishesByMenuId(id);
    }
}
