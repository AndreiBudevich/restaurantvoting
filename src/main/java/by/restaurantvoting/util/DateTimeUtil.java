package by.restaurantvoting.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    public static final LocalDate TODAY = LocalDate.now();

    public static final LocalTime NOW = LocalTime.now();

    public static final LocalTime DEADLINE_CHANGES_MENU = LocalTime.of(7, 0);

    public static final LocalTime DEADLINE_CHANGES_VOTE = LocalTime.of(11, 0);
}
