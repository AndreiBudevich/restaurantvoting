package by.restaurantvoting.web.restaurant;

import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.repository.RestaurantRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static by.restaurantvoting.util.DateTimeUtil.getToday;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = UserRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
@Tag(name = "Rest user restaurant controller", description = "Allows the user to get the available operations on the restaurant")
public class UserRestaurantRestController {

    protected static final String REST_URL = "/api/user/restaurants";

    private final RestaurantRepository restaurantRepository;

    @GetMapping
    @Cacheable
    @Operation(summary = "get all restaurants", description = "Allows you to get all a restaurants")
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantRepository.getAllWithMenu(getToday());
    }
}

