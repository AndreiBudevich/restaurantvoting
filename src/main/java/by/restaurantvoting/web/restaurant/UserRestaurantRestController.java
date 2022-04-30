package by.restaurantvoting.web.restaurant;

import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.model.Vote;
import by.restaurantvoting.repository.RestaurantRepository;
import by.restaurantvoting.repository.UserRepository;
import by.restaurantvoting.repository.VoteRepository;
import by.restaurantvoting.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.restaurantvoting.util.DateTimeUtil.*;

@RestController
@AllArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = UserRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantRestController {

    protected static final String REST_URL = "/api/user/restaurants";

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        return restaurantRepository.getAllWithMenu(getToday());
    }

    @PutMapping("/{restaurantId}")
    @Transactional
    public ResponseEntity<String> setOrDeleteTodayUserVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        String message = "DID NOT SET";
        int userId = authUser.id();
        if (getCurrentTime().isBefore(DEADLINE_CHANGES_VOTE)) {
            Vote vote = voteRepository.getByDateUserId(userId, getToday());
            Restaurant restaurant = restaurantRepository.getOne(restaurantId);
            if (vote == null) {
                vote = new Vote(getToday(), userRepository.getOne(userId));
            }
            if (vote.getRestaurant() == null) {
                vote.setRestaurant(restaurant);
                voteRepository.save(vote);
                message = "VOTE SET";
            } else {
                if (vote.getRestaurant().getId() == restaurantId) {
                    voteRepository.delete(vote);
                    message = "VOTE CANCELED";
                } else {
                    vote.setRestaurant(restaurant);
                    message = "VOTE CHANGED";
                }
            }
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

