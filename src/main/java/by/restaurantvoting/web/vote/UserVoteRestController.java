package by.restaurantvoting.web.vote;

import by.restaurantvoting.model.Vote;
import by.restaurantvoting.repository.VoteRepository;
import by.restaurantvoting.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = UserVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteRestController {

    protected static final String REST_URL = "/api/user/votes";

    private final VoteRepository voteRepository;

    @GetMapping
    public List<Vote> getAllByUserId(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("getAll votes for user {}", userId);
        return voteRepository.getAllByUserId(userId);
    }
}
