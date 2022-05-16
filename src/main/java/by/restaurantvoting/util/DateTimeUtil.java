package by.restaurantvoting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class DateTimeUtil {

    public static final LocalTime DEADLINE_CHANGES_VOTE = LocalTime.of(11, 0);

    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    public static LocalDate getToday() {
        return LocalDate.now();
    }
}
