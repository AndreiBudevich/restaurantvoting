package by.restaurantvoting.testdata;

import by.restaurantvoting.MatcherFactory;
import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.to.RestaurantTo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus", "dishes", "votes");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final int RESTAURANT_ID_0 = 1;
    public static final int RESTAURANT_ID_1 = RESTAURANT_ID_0 + 1;
    public static final int RESTAURANT_ID_2 = RESTAURANT_ID_1 + 1;
    public static final int RESTAURANT_ID_3 = RESTAURANT_ID_2 + 1;

    public static final Restaurant restaurant0 = new Restaurant(
            RESTAURANT_ID_0, "Ваcильки", "г. Минск ул. Тимирязева 67", "8-029-7634349");
    public static final Restaurant restaurant1 = new Restaurant(
            RESTAURANT_ID_1, "Пицца Темпо", "г. Минск ул. Багратиона 81", "8-029-5882922");
    public static final Restaurant restaurant2 = new Restaurant(
            RESTAURANT_ID_2, "Новый свет", "г. Минск ул. Варшавская 81", "8-044-7324144");
    public static final Restaurant restaurant3 = new Restaurant(
            RESTAURANT_ID_3, "Ресторан без меню", "без адреса", "без контактов");

    public static List<Restaurant> all = List.of(restaurant0, restaurant1, restaurant2, restaurant3);

    public static Restaurant getNew() {
        return new Restaurant(null, "New name", "New address", "New contacts");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID_0, "Updated name", "Updated address", "Updated contacts");
    }

    static {
        restaurant0.setVotes(Set.of(VoteTestData.vote0, VoteTestData.vote1, VoteTestData.vote2));
        restaurant1.setVotes(Set.of(VoteTestData.vote3, VoteTestData.vote4, VoteTestData.vote5, VoteTestData.vote6, VoteTestData.vote7, VoteTestData.vote8, VoteTestData.vote9, VoteTestData.vote10));
        restaurant2.setVotes(new HashSet<>());
        restaurant3.setVotes(new HashSet<>());
    }
}
