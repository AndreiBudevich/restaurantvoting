package by.restaurantvoting.web.restaurant;

import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.model.Vote;
import by.restaurantvoting.repository.RestaurantRepository;
import by.restaurantvoting.repository.UserRepository;
import by.restaurantvoting.repository.VoteRepository;
import by.restaurantvoting.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = UserRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rest user controller by restaurants", description = "Allows the user to get the available operations on the restaurant")
public class UserRestaurantRestController {

    protected static final String REST_URL = "/api/user/restaurants";

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.getAllWithMenu(getToday());
    }

    @PutMapping("/{restaurantId}")
    @Transactional
    @Operation(summary = "vote for the restaurant", description = "Allows you to vote for a restaurant until 11.00, " +
            "if you vote again for the same restaurant, this will cancel the vote for it, if you vote again, " +
            "but for a different restaurant, the vote will change"
    )
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

