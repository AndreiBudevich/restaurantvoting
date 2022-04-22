package by.restaurantvoting.testdata;

import by.restaurantvoting.model.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static by.restaurantvoting.testdata.DishTestData.*;
import static by.restaurantvoting.testdata.RestaurantTestData.restaurant0;
import static by.restaurantvoting.testdata.RestaurantTestData.restaurant1;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;

public class MenuTestData {

    private static final LocalDate TODAY = now();
    private static final LocalDate TEST_DATE_0 = of(2022, Month.MARCH, 1);

    public static final int RESTAURANT0_MENU_ID_0 = 1;
    public static final int RESTAURANT0_MENU_ID_1 = RESTAURANT0_MENU_ID_0 + 1;

    public static final int RESTAURANT1_MENU_ID_0 = RESTAURANT0_MENU_ID_1 + 1;
    public static final int RESTAURANT1_MENU_ID_1 = RESTAURANT1_MENU_ID_0 + 1;

    public static final int RESTAURANT2_MENU_ID_0 = RESTAURANT1_MENU_ID_1 + 1;

    public static final Menu restaurant0Menu0 = new Menu(RESTAURANT0_MENU_ID_0, TEST_DATE_0, restaurant0);
    public static final Menu restaurant0Menu1 = new Menu(RESTAURANT0_MENU_ID_1, TODAY, restaurant0);
    public static final Menu restaurant1Menu0 = new Menu(RESTAURANT1_MENU_ID_0, TEST_DATE_0, restaurant1);
    public static final Menu restaurant1Menu1 = new Menu(RESTAURANT1_MENU_ID_1, TODAY, restaurant1);
    public static final Menu restaurant2Menu0 = new Menu(RESTAURANT2_MENU_ID_0, TODAY, restaurant1);

    static {
        restaurant0Menu0.setDishes(Set.of(dish0, dish1, dish3));
        restaurant0Menu1.setDishes(Set.of(dish0, dish2, dish4));
        restaurant1Menu0.setDishes(Set.of(dish5, dish6));
        restaurant1Menu1.setDishes(Set.of(dish5, dish7, dish8, dish9));
        restaurant2Menu0.setDishes(Set.of(dish10, dish11, dish12));
    }
}
