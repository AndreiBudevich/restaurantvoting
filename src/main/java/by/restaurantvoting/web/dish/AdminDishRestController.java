package by.restaurantvoting.web.dish;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.repository.DishRepository;
import by.restaurantvoting.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {

    static final String REST_URL = "api/rest/admin/{restaurantId}/dishes";

    @Autowired
    DishRepository dishRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int restaurantId, @PathVariable int id) {
        Dish dish = dishRepository.getWithMenu(id);
        if (dish != null && dish.getMenus().isEmpty()) {
            dishRepository.deleteExisted(id);
            log.info("delete dish {} for restaurant {}", id, restaurantId);
            return new ResponseEntity<>("DELETE", HttpStatus.OK);
        }
        return new ResponseEntity<>("NOT DELETE", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return dishRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Dish> getAllDishesByRestaurantId(@PathVariable int restaurantId) {
        log.info("getAll dishes for menu for restaurant {}", restaurantId);
        return dishRepository.getAllDishesByRestaurantId(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        Dish created = dishRepository.save(dish);
        log.info("create {} for restaurant {}", created, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

