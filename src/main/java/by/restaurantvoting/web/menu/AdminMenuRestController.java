package by.restaurantvoting.web.menu;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.model.Menu;
import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.repository.MenuRepository;
import by.restaurantvoting.repository.RestaurantRepository;
import by.restaurantvoting.to.DishTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static by.restaurantvoting.util.DishUtil.getTos;
import static by.restaurantvoting.util.MenuUtil.checkForPossibilityOfChange;

@RestController
@Slf4j
@RequestMapping(value = AdminMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuRestController extends AbstractMenuRestController {

    static final String REST_URL = "api/rest/admin/{restaurantId}/menus";

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuRepository menuRepository;

    @Override
    @GetMapping("/{id}")
    public List<Dish> get(@PathVariable int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }

    @GetMapping
    public List<Menu> getAllMenusByRestaurantsId(@PathVariable int restaurantId) {
        log.info("getAll menus for restaurant {}", restaurantId);
        return menuRepository.getAllMenusByRestaurantsId(restaurantId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int restaurantId, @PathVariable int id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (checkForPossibilityOfChange(menu)) {
            menuRepository.deleteExisted(id);
            log.info("delete menu {} for restaurant {}", id, restaurantId);
            return new ResponseEntity<>("DELETE", HttpStatus.OK);
        }
        return new ResponseEntity<>("NOT DELETE", HttpStatus.OK);
    }

    @GetMapping("/{menuId}/edit")
    public List<DishTo> getMenuToEditDishes(@PathVariable int restaurantId, @PathVariable int menuId) {
        log.info("get menu {} for restaurant {} to edit dishes", menuId, restaurantId);
        return getTos(dishRepository.getAllDishesByRestaurantId(restaurantId), menuRepository.getWithDishes(menuId));
    }

    @PutMapping(value = "/{menuId}/edit/{id}")
    @Transactional
    public ResponseEntity<String> setOrDeleteDishInCurrentMenu(
            @PathVariable int restaurantId, @PathVariable int menuId, @PathVariable int id) {
        Menu menu = menuRepository.getWithDishes(menuId);
        if (checkForPossibilityOfChange(menu)) {
            Dish dish = dishRepository.getOne(id);
            if (menu.getDishes().contains(dish)) {
                menu.getDishes().remove(dish);
                log.info("remove {} dish from menu {} for restaurant {}", id, menuId, restaurantId);
                return new ResponseEntity<>("DISH DELETE", HttpStatus.OK);
            } else {
                menu.setDish(dish);
                log.info("add {} dish to menu {} for restaurant {}", id, menuId, restaurantId);
                return new ResponseEntity<>("DISH ADD", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("NOT CHANGED", HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@PathVariable int restaurantId, @RequestBody Menu menu) {
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        menu.setRestaurant(restaurant);
        Menu created = menuRepository.save(menu);
        log.info("create {} for restaurant {}", created, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

