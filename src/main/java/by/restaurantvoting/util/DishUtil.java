package by.restaurantvoting.util;

import by.restaurantvoting.model.Dish;
import by.restaurantvoting.model.Menu;
import by.restaurantvoting.to.DishTo;
import by.restaurantvoting.to.NamedTo;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;

@UtilityClass
public final class DishUtil {

    public static List<DishTo> getTos(List<Dish> dishes, Menu menu) {
        return dishes.stream()
                .map(dish -> createTo(dish, menu))
                .sorted(Comparator.comparing(NamedTo::getName))
                .toList();
    }

    public static DishTo createTo(Dish dish, Menu menu) {
        return new DishTo(dish.getId(), dish.getName(), dish.getDescription(), dish.getWeight(), dish.getPrice(),
                menu.getDishes().contains(dish));
    }
}

