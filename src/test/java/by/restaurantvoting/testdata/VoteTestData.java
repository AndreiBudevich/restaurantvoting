package by.restaurantvoting.testdata;

import by.restaurantvoting.model.Vote;

import java.time.LocalDate;

import static by.restaurantvoting.testdata.UserTestDate.*;
import static by.restaurantvoting.util.DateTimeUtil.getToday;

public class VoteTestData {

    private static final LocalDate TEST_DATE_0 = getToday().minusDays(1);

    public static final Vote vote0 = new Vote(TEST_DATE_0, user0);
    public static final Vote vote1 = new Vote(TEST_DATE_0, user1);
    public static final Vote vote2 = new Vote(TEST_DATE_0, user2);
    public static final Vote vote3 = new Vote(TEST_DATE_0, user3);
}
