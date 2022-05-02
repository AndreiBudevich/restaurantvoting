package by.restaurantvoting.web.menu;

import by.restaurantvoting.model.Dish;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CacheConfig(cacheNames = "menus")
@RequestMapping(value = UserMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rest user controller by menu", description = "Allows the user to get the available menus operations by restaurant ID")
public class UserMenuRestController extends AbstractMenuRestController {

    protected static final String REST_URL = "/api/user/restaurants/{restaurantId}/menu";

    @Override
    @GetMapping("/{id}")
    @Cacheable
    @Operation(summary = "get menu", description = "Allows you to get a restaurant menu by its id in the form of a list of dishes")
    public ResponseEntity<List<Dish>> get(@PathVariable int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }
}
