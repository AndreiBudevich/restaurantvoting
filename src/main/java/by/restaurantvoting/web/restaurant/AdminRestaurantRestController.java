package by.restaurantvoting.web.restaurant;

import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.to.RestaurantTo;
import by.restaurantvoting.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController extends AbstractRestaurantRestController {

    static final String REST_URL = "/api/rest/admin/restaurants";

    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantRepository.findAll(SORT_NAME);
    }

    @GetMapping("/with-voting-on-date")
    public List<RestaurantTo> getRestaurantWithVoting(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll restaurants with voting on date {}", date);
        return RestaurantUtil.getTos(restaurantRepository.getWithVote(date));
    }

    @GetMapping("/{id}")
    public ResponseEntity <Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant {}", id);
        restaurantRepository.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantRepository.save(restaurant);
        log.info("create  restaurant {}", created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}



