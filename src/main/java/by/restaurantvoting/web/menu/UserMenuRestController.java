package by.restaurantvoting.web.menu;

import by.restaurantvoting.model.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = UserMenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMenuRestController extends AbstractMenuRestController {

    protected static final String REST_URL = "/api/rest/user/restaurants/{restaurantId}/menu";

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<Dish>> get(@PathVariable int restaurantId, @PathVariable int id) {
        return super.get(restaurantId, id);
    }
}
