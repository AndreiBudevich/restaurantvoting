package by.restaurantvoting.testdata;

import by.restaurantvoting.MatcherFactory;
import by.restaurantvoting.model.Menu;

import static by.restaurantvoting.util.DateTimeUtil.getToday;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dishes");

    public static final int RESTAURANT0_MENU_ID_0 = 1;
    public static final int RESTAURANT0_MENU_ID_1 = RESTAURANT0_MENU_ID_0 + 1;
    public static final int RESTAURANT0_MENU_ID_2 = RESTAURANT0_MENU_ID_1 + 1;
    public static final int RESTAURANT0_MENU_ID_3 = RESTAURANT0_MENU_ID_2 + 1;
    public static final int RESTAURANT0_MENU_ID_4 = RESTAURANT0_MENU_ID_3 + 1;
    public static final int RESTAURANT0_MENU_ID_5 = RESTAURANT0_MENU_ID_4 + 1;

    public static final Menu restaurant0Menu0 = new Menu(RESTAURANT0_MENU_ID_0, getToday().minusDays(1));
    public static final Menu restaurant0Menu1 = new Menu(RESTAURANT0_MENU_ID_1, getToday());
    public static final Menu restaurant0Menu2 = new Menu(RESTAURANT0_MENU_ID_2, getToday().plusDays(1));
    public static final Menu restaurant0Menu3 = new Menu(RESTAURANT0_MENU_ID_3, getToday().plusDays(2));
    public static final Menu restaurant0Menu4 = new Menu(RESTAURANT0_MENU_ID_4, getToday().plusDays(3));
    public static final Menu restaurant0Menu5 = new Menu(RESTAURANT0_MENU_ID_5, getToday().plusDays(4));

    public static Menu getNew() {
        return new Menu(null, getToday().plusDays(5));
    }
}
