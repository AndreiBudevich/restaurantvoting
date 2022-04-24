package by.restaurantvoting.util;

import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.to.RestaurantTo;

import java.util.List;

public final class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static List<RestaurantTo> getTos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantUtil::createTo)
                .toList();
    }

    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getContacts(), restaurant.getVotes().size());
    }
}
