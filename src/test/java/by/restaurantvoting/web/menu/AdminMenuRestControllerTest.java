package by.restaurantvoting.web.menu;

import by.restaurantvoting.AbstractControllerTest;
import by.restaurantvoting.model.Dish;
import by.restaurantvoting.model.Menu;
import by.restaurantvoting.repository.DishRepository;
import by.restaurantvoting.repository.MenuRepository;
import by.restaurantvoting.testdata.MenuTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Set;

import static by.restaurantvoting.testdata.DishTestData.*;
import static by.restaurantvoting.testdata.MenuTestData.*;
import static by.restaurantvoting.testdata.UserTestDate.ADMIN_MAIL;
import static by.restaurantvoting.testdata.UserTestDate.USER0_MAIL;
import static by.restaurantvoting.util.JsonUtil.writeValue;
import static by.restaurantvoting.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_DATE_MENU;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = ADMIN_MAIL)
class AdminMenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuRestController.REST_URL.replace("{restaurantId}", "1") + '/';

    @Autowired
    MenuRepository menuRepository;
    DishRepository dishRepository;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(restaurant0Menu5, restaurant0Menu4, restaurant0Menu3,
                        restaurant0Menu2, restaurant0Menu1, restaurant0Menu0));
    }

    @Test
    void get() throws Exception {
        restaurant0Menu0.setDishes(Set.of(dish2, dish0, dish3, dish1));
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT0_MENU_ID_0))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(restaurant0Menu0));
    }

    @Test
    void getNotOwn() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL.replace("1", "2") + RESTAURANT0_MENU_ID_0))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER0_MAIL)
    void getAllForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT0_MENU_ID_1))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT0_MENU_ID_1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createWithLocation() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andExpect(status().isCreated());
        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuRepository.getById(newId), newMenu);
    }

    @Test
    void createDuplicate() throws Exception {
        Menu expected = new Menu(null, LocalDate.now());
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_DATE_MENU)));
    }

    @Test
    void addDishInMenu() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + DISH_ID_0)
                .param("menuId", "5"))
                .andExpect(status().isNoContent())
                .andDo(print());
        Dish expected = dishRepository.findById(DISH_ID_0).orElseThrow();
        Dish actual = menuRepository.getWithDishes(RESTAURANT0_MENU_ID_4).orElseThrow().getDishes().stream().findFirst().orElseThrow();
        DISH_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void deleteDishInMenu() throws Exception {
        assertTrue(menuRepository.getWithDishes(RESTAURANT0_MENU_ID_0).orElseThrow().getDishes().contains(dish0));
        perform(MockMvcRequestBuilders.patch(REST_URL + DISH_ID_0)
                .param("menuId", "1"))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertFalse(menuRepository.getWithDishes(RESTAURANT0_MENU_ID_0).orElseThrow().getDishes().contains(dish0));
    }
}

