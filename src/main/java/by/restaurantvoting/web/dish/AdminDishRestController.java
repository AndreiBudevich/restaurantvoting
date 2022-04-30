package by.restaurantvoting.web.dish;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.repository.DishRepository;
import by.restaurantvoting.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static by.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static by.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {

    static final String REST_URL = "/api/admin/{restaurantId}/dishes";

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        dishRepository.deleteExisted(id);
        log.info("delete dish {} for restaurant {}", id, restaurantId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(dishRepository.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int restaurantId, @Valid @RequestBody Dish dish, @PathVariable int id) {
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        assureIdConsistent(dish, id);
        dishRepository.checkBelong(id, restaurantId);
        log.info("update dish {} for restaurant {}", id, restaurantId);
        dishRepository.save(dish);
    }

    @GetMapping
    public List<Dish> getAllDishesByRestaurantId(@PathVariable int restaurantId) {
        log.info("getAll dishes for menu for restaurant {}", restaurantId);
        return dishRepository.getAllDishesByRestaurantId(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Dish> createWithLocation(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNew(dish);
        Dish created = dishRepository.save(dish);
        log.info("create {} for restaurant {}", created, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

