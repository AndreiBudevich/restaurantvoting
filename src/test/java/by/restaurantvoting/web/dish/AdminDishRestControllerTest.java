package by.restaurantvoting.web.dish;

import by.restaurantvoting.AbstractControllerTest;
import by.restaurantvoting.model.Dish;
import by.restaurantvoting.repository.DishRepository;
import by.restaurantvoting.repository.MenuRepository;
import by.restaurantvoting.testdata.DishTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static by.restaurantvoting.testdata.DishTestData.*;
import static by.restaurantvoting.testdata.UserTestDate.ADMIN_MAIL;
import static by.restaurantvoting.testdata.UserTestDate.USER0_MAIL;
import static by.restaurantvoting.util.JsonUtil.writeValue;
import static by.restaurantvoting.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_NAME_DISH;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = ADMIN_MAIL)
class AdminDishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishRestController.REST_URL.replace("{restaurantId}", "1") + '/';

    @Autowired
    DishRepository dishRepository;

    @Autowired
    MenuRepository menuRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_ID_0))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish0));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithAnonymousUser
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_ID_0))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER0_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_ID_0))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish4, dish5, dish2, dish0, dish3, dish1));
    }

    @Test
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID_0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(dishRepository.getById(DISH_ID_0), getUpdated());
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newDish)))
                .andExpect(status().isCreated());
        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getById(newId), newDish);
    }

    @Test
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, "", "d", 3, 5005);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(1, null, null, 3, 5005);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID_0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    void updateDuplicate() throws Exception {
        Dish updated = new Dish(DISH_ID_1, "?????????????? ?? ??????????????????", "??????????????, ?????????????? ????????, ??????????????, " +
                "??????????????, ??????, ????????????????????, ?????????????????? ???????? ?? ??????????????", 290, 800);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_NAME_DISH)));
    }

    @Test
    void createDuplicate() throws Exception {
        Dish expected = new Dish(null, "?????????????? ?? ??????????????????", "??????????????, ?????????????? ????????, ??????????????, " +
                "??????????????, ??????, ????????????????????, ?????????????????? ???????? ?? ??????????????", 290, 2000);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_NAME_DISH)));
    }

    @Test
    void admissibleCreateDuplicate() throws Exception {
        Dish expected = new Dish(null, "?????????????? ?? ??????????????????", "??????????????, ?????????????? ????????, ??????????????, " +
                "??????????????, ??????, ????????????????????, ?????????????????? ???????? ?? ??????????????", 290, 2000);
        perform(MockMvcRequestBuilders.post(AdminDishRestController.REST_URL.replace("{restaurantId}", "2") + '/')
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void admissibleUpdateDuplicate() throws Exception {
        Dish updated = new Dish(DISH_ID_6, "?????????????? ?? ??????????????????", "??????????????, ?????????????? ????????, ??????????????, " +
                "??????????????, ??????, ????????????????????, ?????????????????? ???????? ?? ??????????????", 290, 2000);
        perform(MockMvcRequestBuilders.put(AdminDishRestController.REST_URL.replace("{restaurantId}", "2") + '/' + DISH_ID_6)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Dish invalid = new Dish(1, "<script>alert(123)</script>", "<script>alert(123)</script>", 3, 5005);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_ID_0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}

