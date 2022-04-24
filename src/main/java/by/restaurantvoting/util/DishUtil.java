package by.restaurantvoting.util;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.model.Menu;
import by.restaurantvoting.to.DishTo;

import java.util.List;

public final class DishUtil {
    private DishUtil() {
    }

    public static List<DishTo> getTos(List<Dish> dishes, Menu menu) {
        return dishes.stream()
                .map(dish -> createTo(dish, menu))
                .toList();
    }

    public static DishTo createTo(Dish dish, Menu menu) {
        return new DishTo(dish.getId(), dish.getName(), dish.getDescription(), dish.getWeight(), dish.getPrice(),
                menu.getDishes().contains(dish));
    }
}

