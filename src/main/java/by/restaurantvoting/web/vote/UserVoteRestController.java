package by.restaurantvoting.web.vote;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static by.restaurantvoting.util.DateTimeUtil.*;
import static by.restaurantvoting.web.GlobalExceptionHandler.EXCEPTION_ALLOWED_MODIFICATION_TIME_HAS_EXPIRED;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = UserVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Rest user controller by vote", description = "Allows the user to manage the votes by user id")
public class UserVoteRestController {

    protected static final String REST_URL = "/api/user/votes";

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping
    @Operation(summary = "get all votes by user id", description = "Allows you get all votes by user id"
    )
    public List<Vote> getAllByUserId(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("getAll votes for user {}", userId);
        return voteRepository.getAllByUserId(userId);
    }

    @GetMapping("/today")
    @Operation(summary = "get today vote by user id", description = "Allows you get today vote by user id"
    )
    public Vote getTodayByUserId(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get today vote for user {}", userId);
        return voteRepository.getByDateUserId(userId, getToday());
    }

    @PostMapping
    @Transactional
    @Operation(summary = "create vote", description = "Allows you to create a vote")
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        int userId = authUser.id();
        Vote vote = new Vote(getToday(), userRepository.getById(userId));
        vote.setRestaurant(restaurantRepository.getById(restaurantId));
        Vote created = voteRepository.save(vote);
        log.info("create {} by user {} for the restaurant {}", created, userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}")
    @Transactional
    @Operation(summary = "update vote", description = "Allows you to update the vote by its id")
    public ResponseEntity<String> update(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id, @RequestParam int restaurantId) {
        if (getCurrentTime().isBefore(DEADLINE_CHANGES_VOTE)) {
            int userId = authUser.id();
            Vote vote = voteRepository.getById(id);
            Restaurant restaurant = restaurantRepository.getById(restaurantId);
            vote.setRestaurant(restaurant);
            log.info("update {} by user {} for the restaurant {}", vote.getId(), userId, restaurantId);
            return new ResponseEntity<>("vote changed", HttpStatus.OK);
        }
        return new ResponseEntity<>(EXCEPTION_ALLOWED_MODIFICATION_TIME_HAS_EXPIRED, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
