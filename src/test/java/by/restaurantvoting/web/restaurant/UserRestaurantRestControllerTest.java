package by.restaurantvoting.web.restaurant;

import by.restaurantvoting.AbstractControllerTest;
import by.restaurantvoting.repository.RestaurantRepository;
import by.restaurantvoting.repository.VoteRepository;
import by.restaurantvoting.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static by.restaurantvoting.testdata.RestaurantTestData.*;
import static by.restaurantvoting.testdata.UserTestDate.USER0_ID;
import static by.restaurantvoting.testdata.UserTestDate.USER0_MAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserRestaurantRestController.REST_URL + '/';

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = USER0_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void setVote() throws Exception {
        try (MockedStatic<DateTimeUtil> mockedStatic = mockStatic(DateTimeUtil.class)) {
            LocalDate fixedDate = LocalDate.now().plusDays(1);
            LocalTime fixedTime = LocalTime.of(8, 10);
            mockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(fixedTime);
            mockedStatic.when(DateTimeUtil::getToday).thenReturn(fixedDate);
            perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_1))
                    .andDo(print())
                    .andExpect(status().isOk());
            assertEquals(restaurant1, voteRepository.getAllByUserId(USER0_ID).stream()
                    .filter(vote -> vote.getVoteDate().isEqual(fixedDate))
                    .findFirst().orElse(null).getRestaurant());
        }
    }

    @Test
    @WithUserDetails(value = USER0_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void changeVote() throws Exception {
        assertEquals(restaurant1, voteRepository.getAllByUserId(USER0_ID).stream()
                .filter(vote -> vote.getVoteDate().isEqual(LocalDate.now()))
                .findFirst().orElse(null).getRestaurant());
        try (MockedStatic<DateTimeUtil> mockedStatic = mockStatic(DateTimeUtil.class)) {
            LocalDate fixedDate = LocalDate.now();
            LocalTime fixedTime = LocalTime.of(8, 10);
            mockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(fixedTime);
            mockedStatic.when(DateTimeUtil::getToday).thenReturn(fixedDate);
            perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_0))
                    .andDo(print())
                    .andExpect(status().isOk());
            assertEquals(restaurant0, voteRepository.getAllByUserId(USER0_ID).stream()
                    .filter(vote -> vote.getVoteDate().isEqual(fixedDate))
                    .findFirst().orElse(null).getRestaurant());
        }
    }

    @Test
    @WithUserDetails(value = USER0_MAIL)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Transactional(propagation = Propagation.NEVER)
    void changeVoteAfterDeadLineTime() throws Exception {
        assertEquals(restaurant1, voteRepository.getAllByUserId(USER0_ID).stream()
                .filter(vote -> vote.getVoteDate().isEqual(LocalDate.now()))
                .findFirst().orElse(null).getRestaurant());
        try (MockedStatic<DateTimeUtil> mockedStatic = mockStatic(DateTimeUtil.class)) {
            LocalDate fixedDate = LocalDate.now();
            LocalTime fixedTime = LocalTime.of(11, 0);
            mockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(fixedTime);
            mockedStatic.when(DateTimeUtil::getToday).thenReturn(fixedDate);
            perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_0))
                    .andDo(print())
                    .andExpect(status().isOk());
            assertEquals(restaurant1, voteRepository.getAllByUserId(USER0_ID).stream()
                    .filter(vote -> vote.getVoteDate().isEqual(fixedDate))
                    .findFirst().orElse(null).getRestaurant());
        }
    }

    @Test
    @WithUserDetails(value = USER0_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void deleteVote() throws Exception {
        assertEquals(restaurant1, voteRepository.getAllByUserId(USER0_ID).stream()
                .filter(vote -> vote.getVoteDate().isEqual(LocalDate.now()))
                .findFirst().orElse(null).getRestaurant());
        try (MockedStatic<DateTimeUtil> mockedStatic = mockStatic(DateTimeUtil.class)) {
            LocalDate fixedDate = LocalDate.now();
            LocalTime fixedTime = LocalTime.of(8, 10);
            mockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(fixedTime);
            mockedStatic.when(DateTimeUtil::getToday).thenReturn(fixedDate);
            perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_1))
                    .andDo(print())
                    .andExpect(status().isOk());
            assertNull(voteRepository.getAllByUserId(USER0_ID).stream()
                    .filter(vote -> vote.getVoteDate().isEqual(fixedDate))
                    .findFirst().orElse(null));
        }
    }
}
