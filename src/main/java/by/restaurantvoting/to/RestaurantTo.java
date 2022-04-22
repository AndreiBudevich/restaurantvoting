package by.restaurantvoting.to;

import lombok.*;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo {

    int numberVoices;

    public RestaurantTo(Integer id, String name, int numberVoices) {
        super(id, name);
        this.numberVoices = numberVoices;
    }
}

