package by.restaurantvoting.util;

import by.restaurantvoting.model.Menu;

import static by.restaurantvoting.util.DateTimeUtil.*;

public class MenuUtil {

    private MenuUtil() {
    }

    public static boolean checkForPossibilityOfChange(Menu menu) {
        if (menu == null || menu.getDate().isBefore(TODAY)) {
            return false;
        }
        if (menu.getDate().isAfter(TODAY)) {
            return true;
        }
        return NOW.isBefore(DEADLINE_CHANGES_MENU);
    }
}
