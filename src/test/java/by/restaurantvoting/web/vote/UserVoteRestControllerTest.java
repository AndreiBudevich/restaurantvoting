package by.restaurantvoting.web.vote;

import by.restaurantvoting.AbstractControllerTest;
import by.restaurantvoting.model.Vote;
import by.restaurantvoting.repository.VoteRepository;
import by.restaurantvoting.testdata.VoteTestData;
import by.restaurantvoting.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static by.restaurantvoting.testdata.RestaurantTestData.*;
import static by.restaurantvoting.testdata.UserTestDate.*;
import static by.restaurantvoting.testdata.VoteTestData.*;
import static by.restaurantvoting.util.DateTimeUtil.getToday;
import static by.restaurantvoting.web.GlobalExceptionHandler.EXCEPTION_AGAIN_VOTE_DAY;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = USER0_MAIL)
public class UserVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserVoteRestController.REST_URL + '/';

    @Autowired
    VoteRepository voteRepository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote0, vote4));
    }

    @Test
    void getTodayByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote4));
        RESTAURANT_MATCHER.assertMatch(restaurant1, voteRepository.getByDateUserId(USER0_ID, getToday()).orElseThrow().getRestaurant());
    }

    @Test
    @WithUserDetails(value = USER3_MAIL)
    void createWithLocation() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2"))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getByDateUserId(USER3_ID, getToday()).orElseThrow(), newVote);
        RESTAURANT_MATCHER.assertMatch(restaurant1, voteRepository.getByDateUserId(USER3_ID, getToday()).orElseThrow().getRestaurant());
    }

    @Test
    void createAgain() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_AGAIN_VOTE_DAY)));
    }

    @Test
    void updateVote() throws Exception {
        RESTAURANT_MATCHER.assertMatch(restaurant1, voteRepository.getByDateUserId(USER0_ID, getToday()).orElseThrow().getRestaurant());
        try (MockedStatic<DateTimeUtil> mockedStatic = mockStatic(DateTimeUtil.class)) {
            LocalDate fixedDate = LocalDate.now();
            LocalTime fixedTime = LocalTime.of(8, 10);
            mockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(fixedTime);
            mockedStatic.when(DateTimeUtil::getToday).thenReturn(fixedDate);
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID_4)
                    .param("restaurantId", "1"))
                    .andDo(print())
                    .andExpect(status().isOk());
            RESTAURANT_MATCHER.assertMatch(restaurant0, voteRepository.getByDateUserId(USER0_ID, getToday()).orElseThrow().getRestaurant());
        }
    }

    @Test
    void updateVoteAfterDeadLineTime() throws Exception {
        RESTAURANT_MATCHER.assertMatch(restaurant1, voteRepository.getByDateUserId(USER0_ID, getToday()).orElseThrow().getRestaurant());
        try (MockedStatic<DateTimeUtil> mockedStatic = mockStatic(DateTimeUtil.class)) {
            LocalDate fixedDate = LocalDate.now();
            LocalTime fixedTime = LocalTime.of(11, 0);
            mockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(fixedTime);
            mockedStatic.when(DateTimeUtil::getToday).thenReturn(fixedDate);
            perform(MockMvcRequestBuilders.put(REST_URL + VOTE_ID_4)
                    .param("restaurantId", "1"))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(content().string(containsString("ALLOWED_MODIFICATION_TIME_HAS_EXPIRED")));
            RESTAURANT_MATCHER.assertMatch(restaurant1, voteRepository.getByDateUserId(USER0_ID, getToday()).orElseThrow().getRestaurant());
        }
    }
}
