package by.restaurantvoting.testdata;

import by.restaurantvoting.model.Vote;

import java.time.LocalDate;

import static by.restaurantvoting.util.DateTimeUtil.getToday;

public class VoteTestData {

    private static final LocalDate TEST_DATE_0 = getToday().minusDays(1);

    public static final Vote vote0 = new Vote(TEST_DATE_0, UserTestDate.user0);
    public static final Vote vote1 = new Vote(TEST_DATE_0, UserTestDate.user1);
    public static final Vote vote2 = new Vote(TEST_DATE_0, UserTestDate.user2);
    public static final Vote vote3 = new Vote(TEST_DATE_0, UserTestDate.user3);
    public static final Vote vote4 = new Vote(TEST_DATE_0, UserTestDate.user4);
    public static final Vote vote5 = new Vote(TEST_DATE_0, UserTestDate.user5);
    public static final Vote vote6 = new Vote(TEST_DATE_0, UserTestDate.user6);
    public static final Vote vote7 = new Vote(TEST_DATE_0, UserTestDate.user7);
    public static final Vote vote8 = new Vote(TEST_DATE_0, UserTestDate.user8);
    public static final Vote vote9 = new Vote(TEST_DATE_0, UserTestDate.user9);
    public static final Vote vote10 = new Vote(TEST_DATE_0, UserTestDate.user10);
}
