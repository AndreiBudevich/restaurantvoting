package by.restaurantvoting.to;

import lombok.*;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends NamedTo {

    String description;

    int weight;

    int price;

    boolean currentMenu;

    public DishTo(Integer id, String name, String description, int weight, int price, boolean currentMenu) {
        super(id, name);
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.currentMenu = currentMenu;
    }
}
