package by.restaurantvoting.web.menu;

import by.restaurantvoting.AbstractControllerTest;
import by.restaurantvoting.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static by.restaurantvoting.testdata.DishTestData.*;
import static by.restaurantvoting.testdata.MenuTestData.RESTAURANT0_MENU_ID_0;
import static by.restaurantvoting.testdata.UserTestDate.USER0_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserMenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserMenuRestController.REST_URL.replace("{restaurantId}", "1") + '/';

    @Autowired
    MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = USER0_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT0_MENU_ID_0))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish2, dish0, dish3, dish1));
    }

    @Test
    @WithUserDetails(value = USER0_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
