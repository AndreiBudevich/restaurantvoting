package by.restaurantvoting.web.vote;

import by.restaurantvoting.model.Vote;
import by.restaurantvoting.repository.VoteRepository;
import by.restaurantvoting.web.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = UserVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteRestController {

    @Autowired
    VoteRepository voteRepository;

    protected static final String REST_URL = "/api/rest/user/votes";

    @GetMapping
    public List<Vote> getAllByUserId() {
        int userId = SecurityUtil.authId();
        log.info("getAll votes for user {}", userId);
        return voteRepository.getAllByUserId(userId);
    }
}
