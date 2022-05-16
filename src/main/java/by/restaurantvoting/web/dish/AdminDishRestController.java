package by.restaurantvoting.web.dish;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.model.Menu;
import by.restaurantvoting.repository.DishRepository;
import by.restaurantvoting.repository.MenuRepository;
import by.restaurantvoting.repository.RestaurantRepository;
import by.restaurantvoting.to.DishTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static by.restaurantvoting.util.DishUtil.getTos;
import static by.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static by.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rest admin controller by dish", description = "Allows the administrator to manage the dishes by restaurant ID")
public class AdminDishRestController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @GetMapping({"/{id}"})
    @Operation(summary = "get dish", description = "Allows you to get a restaurant dish by its id")
    public ResponseEntity<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(dishRepository.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(summary = "update dish", description = "Allows you to update a restaurant dish by its id")
    public void update(@PathVariable int restaurantId, @Valid @RequestBody Dish dish, @PathVariable int id) {
        assureIdConsistent(dish, id);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        dishRepository.checkBelong(id, restaurantId);
        dishRepository.save(dish);
        log.info("update dish {} for restaurant {}", id, restaurantId);
    }

    @GetMapping
    @Operation(summary = "get all dishes", description = "Allows you to get all a restaurant dishes by its id")
    public List<Dish> getAllDishesByRestaurantId(@PathVariable int restaurantId) {
        log.info("getAll dishes for menu for restaurant {}", restaurantId);
        return dishRepository.getAllDishesByRestaurantId(restaurantId);
    }

    @GetMapping("/with-markers")
    @Operation(summary = "get all dishes with-markers", description = "Allows you to get a list of all the dishes of " +
            "the restaurant with a check for availability in the menu by its ID, for quickly adding or removing dishes from this menu")
    public ResponseEntity<List<DishTo>> getMenuWithMarkers(@PathVariable int restaurantId, @RequestParam int menuId) {
        Menu menu = menuRepository.getWithDishes(menuId).orElse(null);
        if (menu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("get all dishes with-markers for restaurant {} by menu {}", restaurantId, menuId);
        return new ResponseEntity<>(getTos(dishRepository.getAllDishesByRestaurantId(restaurantId),
                menuRepository.getWithDishes(menuId).orElseThrow()), HttpStatus.OK);
    }

    @PatchMapping(value = "/with-markers/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "add/delete dish in menu", description = "Allows you to add a dish to the menu or remove it " +
            "if this dish is in the current menu")
    public void setOrDeleteDishInCurrentMenu(
            @PathVariable int restaurantId, @PathVariable int id, @RequestParam int menuId) {
        Menu menu = menuRepository.getWithDishes(menuId).orElseThrow();
        Dish dish = dishRepository.getById(id);
        if (menu.getDishes().contains(dish)) {
            menu.getDishes().remove(dish);
            log.info("remove {} dish from menu {} for restaurant {}", id, menuId, restaurantId);
        } else {
            menu.setDish(dish);
            log.info("add {} dish to menu {} for restaurant {}", id, menuId, restaurantId);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @Operation(summary = "create dish", description = "Allows you to create a restaurant dish")
    public ResponseEntity<Dish> createWithLocation(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        Dish created = dishRepository.save(dish);
        log.info("create {} for restaurant {}", created, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

