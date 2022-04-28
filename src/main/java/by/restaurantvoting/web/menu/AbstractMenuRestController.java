package by.restaurantvoting.web.menu;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.repository.DishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j
public class AbstractMenuRestController {

    @Autowired
    DishRepository dishRepository;

    public ResponseEntity<List<Dish>> get(int restaurantId, int id) {
        List<Dish> allDishesByMenuId = dishRepository.getAllDishesByMenuId(id);
        if (allDishesByMenuId.isEmpty()) {
            log.info("menu {} not found for restaurant {}", id, restaurantId);
            return new ResponseEntity<>(allDishesByMenuId, HttpStatus.NOT_FOUND);
        }
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return new ResponseEntity<>(allDishesByMenuId, HttpStatus.OK);
    }
}
