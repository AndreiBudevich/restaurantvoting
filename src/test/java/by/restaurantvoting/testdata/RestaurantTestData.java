package by.restaurantvoting.testdata;

import by.restaurantvoting.model.Restaurant;

import java.util.List;
import java.util.Set;

import static by.restaurantvoting.testdata.DishTestData.*;
import static by.restaurantvoting.testdata.MenuTestData.*;
import static by.restaurantvoting.testdata.VoteTestData.*;

public class RestaurantTestData {

    public static final int RESTAURANT_ID_0 = 1;
    public static final int RESTAURANT_ID_1 = RESTAURANT_ID_0 + 1;
    public static final int RESTAURANT_ID_2 = RESTAURANT_ID_1 + 1;
    public static final int RESTAURANT_ID_3 = RESTAURANT_ID_2 + 1;

    public static final Restaurant restaurant0 = new Restaurant(
            RESTAURANT_ID_0, "Ваcильки", "г. Минск ул. тимирязева 67", "8-029-7634349");
    public static final Restaurant restaurant1 = new Restaurant(
            RESTAURANT_ID_1, "Пицца Темпо", "г. Минск ул. багратиона 81", "8-029-5882922");
    public static final Restaurant restaurant2 = new Restaurant(
            RESTAURANT_ID_2, "Новый свет", "г. Минск ул. Варшавская 81", "8-044-7324144");
    public static final Restaurant restaurant3 = new Restaurant(
            RESTAURANT_ID_3, "Ресторан без меню", "без адреса", "без контактов");

    static {
        restaurant0.setDishes(List.of(dish0, dish1, dish2, dish4));
        restaurant0.setDishes(List.of(dish5, dish6, dish7, dish8, dish9));
        restaurant2.setDishes(List.of(dish10, dish11, dish12));
        restaurant0.setMenus(Set.of(restaurant0Menu0, restaurant0Menu1));
        restaurant1.setMenus(Set.of(restaurant1Menu0, restaurant1Menu1));
        restaurant2.setMenus(Set.of(restaurant2Menu0));
        restaurant0.setVotes(Set.of(vote0, vote1, vote2, vote14, vote15, vote16, vote17, vote18, vote19, vote20, vote21));
        restaurant1.setVotes(Set.of(vote3, vote4, vote5, vote6, vote7, vote8, vote9, vote10, vote11, vote12, vote13));
    }
}