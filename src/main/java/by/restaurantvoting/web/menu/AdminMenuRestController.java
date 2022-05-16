package by.restaurantvoting.web.menu;

import by.restaurantvoting.model.Menu;
import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.repository.MenuRepository;
import by.restaurantvoting.repository.RestaurantRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    @Operation(summary = "get menu", description = "Allows you to get a restaurant menu by its id in the form of a list of dishes")
    public ResponseEntity<Menu> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(menuRepository.getWithDishes(id));
    }

    @GetMapping
    @Operation(summary = "get all menus", description = "Allows you to get all a restaurant menus")
    public List<Menu> getAllMenusByRestaurantsId(@PathVariable int restaurantId) {
        log.info("getAll menus for restaurant {}", restaurantId);
        return menuRepository.getAllMenusByRestaurantsId(restaurantId);
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

