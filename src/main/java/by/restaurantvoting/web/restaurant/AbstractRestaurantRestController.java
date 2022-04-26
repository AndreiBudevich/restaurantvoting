package by.restaurantvoting.web.restaurant;

import by.restaurantvoting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractRestaurantRestController {

    @Autowired
    RestaurantRepository restaurantRepository;
}
