package by.restaurantvoting.web.menu;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.model.Menu;
import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.repository.DishRepository;
import by.restaurantvoting.repository.MenuRepository;
import by.restaurantvoting.repository.RestaurantRepository;
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

import static by.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rest admin controller by menu", description = "Allows the administrator to manage the menus by restaurant id")
public class AdminMenuRestController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menus";

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;

    @GetMapping("/{id}")
    @Operation(summary = "get menu", description = "Allows you to get a restaurant menu by its id in the form of a list of dishes")
    public ResponseEntity<Menu> get(@PathVariable int restaurantId, @PathVariable int id) {
        Menu menu = menuRepository.getWithDishes(id).orElse(null);
        if (menu != null && menu.getRestaurant().getId() == restaurantId) {
            log.info("get menu {} for restaurant {}", id, restaurantId);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @Operation(summary = "get all menus", description = "Allows you to get all a restaurant menus")
    public List<Menu> getAllMenusByRestaurantsId(@PathVariable int restaurantId) {
        log.info("getAll menus for restaurant {}", restaurantId);
        return menuRepository.getAllMenusByRestaurantsId(restaurantId);
    }

    @PatchMapping(value = "/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "add/delete dish in menu", description = "Allows you to add a dish to the menu or remove it " +
            "if this dish is in the current menu")
    public void setOrDeleteDish(
            @PathVariable int restaurantId, @PathVariable int id, @RequestParam int menuId) {
        Menu menu = menuRepository.getWithDishes(menuId).orElse(null);
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
    @Operation(summary = "create menu", description = "Allows you to create a restaurant menu without dishes")
    public ResponseEntity<Menu> createWithLocation(@PathVariable int restaurantId, @Valid @RequestBody Menu menu) {
        checkNew(menu);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        menu.setRestaurant(restaurant);
        Menu created = menuRepository.save(menu);
        log.info("create {} for restaurant {}", created, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

