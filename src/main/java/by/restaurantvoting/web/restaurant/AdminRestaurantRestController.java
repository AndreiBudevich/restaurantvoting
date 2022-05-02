package by.restaurantvoting.web.restaurant;

import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.repository.RestaurantRepository;
import by.restaurantvoting.to.RestaurantTo;
import by.restaurantvoting.util.RestaurantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static by.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static by.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rest admin controller by restaurants", description = "Allows the administrator to manage restaurants")
public class AdminRestaurantRestController {

    static final String REST_URL = "/api/admin/restaurants";

    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final RestaurantRepository restaurantRepository;


    @GetMapping
    @Operation(summary = "get all restaurants", description = "Allows you to get all a restaurants")
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantRepository.findAll(SORT_NAME);
    }

    @GetMapping("/with-voting-on-date")
    @Operation(summary = "get all restaurants", description = "Allows you to get all restaurants with votes for a specific date")
    public List<RestaurantTo> getAllRestaurantWithVoting(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll restaurants with voting on date {}", date);
        return RestaurantUtil.getTos(restaurantRepository.getAllWithVote(date));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get restaurant", description = "Allows you to get a restaurant by its id")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete restaurant", description = "Allows you to delete a restaurant by its id")
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update restaurant", description = "Allows you to update a restaurant by its id")
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        log.info("update restaurant {}", id);
        restaurantRepository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create restaurant", description = "Allows you to create a restaurant")
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        log.info("create  restaurant {}", created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}



