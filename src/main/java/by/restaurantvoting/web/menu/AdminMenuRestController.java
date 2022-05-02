package by.restaurantvoting.web.menu;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.model.Menu;
import by.restaurantvoting.model.Restaurant;
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

import static by.restaurantvoting.util.DateTimeUtil.*;
import static by.restaurantvoting.util.DishUtil.getTos;
import static by.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rest admin controller by menu", description = "Allows the administrator to manage the menus by restaurant ID")
public class AdminMenuRestController extends AbstractMenuRestController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menus";

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "get menu", description = "Allows you to get a restaurant menu by its id in the form of a list of dishes")
    public ResponseEntity<List<Dish>> get(@PathVariable int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }

    @GetMapping
    @Operation(summary = "get all menus", description = "Allows you to get all a restaurant menus")
    public List<Menu> getAllMenusByRestaurantsId(@PathVariable int restaurantId) {
        log.info("getAll menus for restaurant {}", restaurantId);
        return menuRepository.getAllMenusByRestaurantsId(restaurantId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete menu", description = "Allows you to delete a restaurant menu by its id")
    public ResponseEntity<String> delete(@PathVariable int restaurantId, @PathVariable int id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu == null) {
            log.info("didn't delete menu {} for restaurant {} as menu not found", id, restaurantId);
            return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
        }
        if (menu.getMenuDate().isEqual(getToday()) && getCurrentTime().isAfter(DEADLINE_DELETE_TODAY_MENU)) {
            log.info("didn't delete menu {} for restaurant {}", id, restaurantId);
            return new ResponseEntity<>("NOT DELETE", HttpStatus.NO_CONTENT);
        }
        menuRepository.deleteExisted(id);
        log.info("delete menu {} for restaurant {}", id, restaurantId);
        return new ResponseEntity<>("DELETE", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{menuId}/edit")
    @Operation(summary = "edit menu", description = "Allows you to get a list of all dishes in a restaurant with a " +
            "check for availability in the current menu by its ID")
    public List<DishTo> getMenuToEditDishes(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("get menu {} for restaurant {} to edit dishes", menuId, restaurantId);
        return getTos(dishRepository.getAllDishesByRestaurantId(restaurantId), menuRepository.getWithDishes(menuId));
    }

    @PutMapping(value = "/{menuId}/edit/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "add/delete dish in menu", description = "Allows you to add a dish to the menu or remove it " +
            "if this dish is in the current menu")
    public void setOrDeleteDishInCurrentMenu(
            @PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int id) {
        Menu menu = menuRepository.getWithDishes(menuId);
        Dish dish = dishRepository.getOne(id);
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
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        menu.setRestaurant(restaurant);
        checkNew(menu);
        Menu created = menuRepository.save(menu);
        log.info("create {} for restaurant {}", created, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

