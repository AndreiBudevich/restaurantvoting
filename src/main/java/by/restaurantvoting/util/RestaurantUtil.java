package by.restaurantvoting.util;

import by.restaurantvoting.model.Restaurant;
import by.restaurantvoting.to.NamedTo;
import by.restaurantvoting.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;

@UtilityClass
public class RestaurantUtil {

    public static List<RestaurantTo> getTos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantUtil::createTo)
                .sorted(Comparator.comparing(NamedTo::getName))
                .toList();
    }

    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getContacts(), restaurant.getVotes().size());
    }
}
