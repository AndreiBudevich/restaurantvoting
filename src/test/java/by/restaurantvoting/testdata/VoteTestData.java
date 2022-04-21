package by.restaurantvoting.testdata;

import by.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.time.Month;

import static by.restaurantvoting.testdata.RestaurantTestData.START_SEQ;
import static by.restaurantvoting.testdata.UserTestDate.*;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;

public class VoteTestData {

    private static final LocalDate TODAY = now();
    private static final LocalDate TEST_DATE_0 = of(2022, Month.MARCH, 1);

    public static final int VOTE0_ID = START_SEQ + 37;
    public static final int VOTE1_ID = VOTE0_ID + 1;
    public static final int VOTE2_ID = VOTE1_ID + 1;
    public static final int VOTE3_ID = VOTE2_ID + 1;
    public static final int VOTE4_ID = VOTE3_ID + 1;
    public static final int VOTE5_ID = VOTE4_ID + 1;
    public static final int VOTE6_ID = VOTE5_ID + 1;
    public static final int VOTE7_ID = VOTE6_ID + 1;
    public static final int VOTE8_ID = VOTE7_ID + 1;
    public static final int VOTE9_ID = VOTE8_ID + 1;
    public static final int VOTE10_ID = VOTE9_ID + 1;
    public static final int VOTE11_ID = VOTE10_ID + 1;
    public static final int VOTE12_ID = VOTE11_ID + 1;
    public static final int VOTE13_ID = VOTE12_ID + 1;
    public static final int VOTE14_ID = VOTE13_ID + 1;
    public static final int VOTE15_ID = VOTE14_ID + 1;
    public static final int VOTE16_ID = VOTE15_ID + 1;
    public static final int VOTE17_ID = VOTE16_ID + 1;
    public static final int VOTE18_ID = VOTE17_ID + 1;
    public static final int VOTE19_ID = VOTE18_ID + 1;
    public static final int VOTE20_ID = VOTE19_ID + 1;
    public static final int VOTE21_ID = VOTE20_ID + 1;

    public static final Vote vote0 = new Vote(TEST_DATE_0, user0);
    public static final Vote vote1 = new Vote(TEST_DATE_0, user1);
    public static final Vote vote2 = new Vote(TEST_DATE_0, user2);
    public static final Vote vote3 = new Vote(TEST_DATE_0, user3);
    public static final Vote vote4 = new Vote(TEST_DATE_0, user4);
    public static final Vote vote5 = new Vote(TEST_DATE_0, user5);
    public static final Vote vote6 = new Vote(TEST_DATE_0, user6);
    public static final Vote vote7 = new Vote(TEST_DATE_0, user7);
    public static final Vote vote8 = new Vote(TEST_DATE_0, user8);
    public static final Vote vote9 = new Vote(TEST_DATE_0, user9);
    public static final Vote vote10 = new Vote(TEST_DATE_0, user10);

    public static final Vote vote11 = new Vote(TODAY, user0);
    public static final Vote vote12 = new Vote(TODAY, user1);
    public static final Vote vote13 = new Vote(TODAY, user2);
    public static final Vote vote14 = new Vote(TODAY, user3);
    public static final Vote vote15 = new Vote(TODAY, user4);
    public static final Vote vote16 = new Vote(TODAY, user5);
    public static final Vote vote17 = new Vote(TODAY, user6);
    public static final Vote vote18 = new Vote(TODAY, user7);
    public static final Vote vote19 = new Vote(TODAY, user8);
    public static final Vote vote20 = new Vote(TODAY, user9);
    public static final Vote vote21 = new Vote(TODAY, user10);
}
