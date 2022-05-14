package by.restaurantvoting.testdata;

import by.restaurantvoting.MatcherFactory;
import by.restaurantvoting.model.Vote;

import java.time.LocalDate;

import static by.restaurantvoting.testdata.RestaurantTestData.restaurant0;
import static by.restaurantvoting.testdata.RestaurantTestData.restaurant1;
import static by.restaurantvoting.testdata.UserTestDate.*;
import static by.restaurantvoting.util.DateTimeUtil.getToday;

public class VoteTestData {

    public static final int VOTE_ID_0 = 1;
    public static final int VOTE_ID_1 = VOTE_ID_0 + 1;
    public static final int VOTE_ID_2 = VOTE_ID_1 + 1;
    public static final int VOTE_ID_3 = VOTE_ID_2 + 1;
    public static final int VOTE_ID_4 = VOTE_ID_3 + 1;

    private static final LocalDate TEST_DATE_0 = getToday().minusDays(1);
    private static final LocalDate TEST_DATE_1 = getToday();

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user.registered", "user.password", "restaurant.votes", "restaurant");

    public static final Vote vote0 = new Vote(VOTE_ID_0, TEST_DATE_0, user0);
    public static final Vote vote1 = new Vote(VOTE_ID_1, TEST_DATE_0, user1);
    public static final Vote vote2 = new Vote(VOTE_ID_2, TEST_DATE_0, user2);
    public static final Vote vote3 = new Vote(VOTE_ID_3, TEST_DATE_0, user3);
    public static final Vote vote4 = new Vote(VOTE_ID_4, TEST_DATE_1, user0);

    public static Vote getNew() {
        Vote vote = new Vote(null, TEST_DATE_1, user3);
        vote.setRestaurant(restaurant1);
        return vote;
    }

    static {
        vote0.setRestaurant(restaurant0);
        vote1.setRestaurant(restaurant0);
        vote2.setRestaurant(restaurant0);
        vote3.setRestaurant(restaurant1);
        vote4.setRestaurant(restaurant1);
    }
}
