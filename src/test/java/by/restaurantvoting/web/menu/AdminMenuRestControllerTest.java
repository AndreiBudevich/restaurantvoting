package by.restaurantvoting.web.menu;

import by.restaurantvoting.AbstractControllerTest;
import by.restaurantvoting.model.Menu;
import by.restaurantvoting.repository.MenuRepository;
import by.restaurantvoting.testdata.MenuTestData;
import by.restaurantvoting.util.DateTimeUtil;
import by.restaurantvoting.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static by.restaurantvoting.testdata.DishTestData.*;
import static by.restaurantvoting.testdata.MenuTestData.*;
import static by.restaurantvoting.testdata.UserTestDate.ADMIN_MAIL;
import static by.restaurantvoting.testdata.UserTestDate.USER0_MAIL;
import static by.restaurantvoting.util.DishUtil.getTos;
import static by.restaurantvoting.util.JsonUtil.writeValue;
import static by.restaurantvoting.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_DATE_MENU;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuRestController.REST_URL.replace("{restaurantId}", "1") + '/';

    @Autowired
    MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(restaurant0Menu5, restaurant0Menu4, restaurant0Menu3,
                        restaurant0Menu2, restaurant0Menu1, restaurant0Menu0));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT0_MENU_ID_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish4, dish2, dish3));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getToForEdit() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT0_MENU_ID_1 + "/edit"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(getTos(dishesByRestaurant0, menuRepository.getById(RESTAURANT0_MENU_ID_1))));
    }

    @Test
    @WithUserDetails(value = USER0_MAIL)
    void getAllForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT0_MENU_ID_1))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT0_MENU_ID_1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void deleteMenuYesterday() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT0_MENU_ID_0))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(RESTAURANT0_MENU_ID_0).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void deleteMenuTodayAfterDeadlineDeleteTime() throws Exception {
        try (MockedStatic<DateTimeUtil> mockedStatic = mockStatic(DateTimeUtil.class)) {
            LocalTime fixedTime = LocalTime.of(8, 1);
            mockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(fixedTime);
            mockedStatic.when(DateTimeUtil::getToday).thenReturn(LocalDate.now());
            perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT0_MENU_ID_1))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            assertTrue(menuRepository.findById(RESTAURANT0_MENU_ID_1).isPresent());
        }
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void deleteMenuTomorrow() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT0_MENU_ID_2))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(RESTAURANT0_MENU_ID_2).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void deleteMenuTodayBeforeDeadlineDeleteTime() throws Exception {
        try (MockedStatic<DateTimeUtil> mockedStatic = mockStatic(DateTimeUtil.class)) {
            LocalTime fixedTime = LocalTime.of(6, 0);
            mockedStatic.when(DateTimeUtil::getCurrentTime).thenReturn(fixedTime);
            mockedStatic.when(DateTimeUtil::getToday).thenReturn(LocalDate.now().plusDays(2));
            perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT0_MENU_ID_3))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            assertFalse(menuRepository.findById(RESTAURANT0_MENU_ID_3).isPresent());
        }
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void addDishInMenu() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT0_MENU_ID_4 + "/edit/" + DISH_ID_0))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertTrue(menuRepository.getWithDishes(RESTAURANT0_MENU_ID_4).getDishes().contains(dish0));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void deleteDishInMenu() throws Exception {
        assertTrue(menuRepository.getWithDishes(RESTAURANT0_MENU_ID_5).getDishes().contains(dish0));
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT0_MENU_ID_5 + "/edit/" + DISH_ID_0))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertFalse(menuRepository.getWithDishes(RESTAURANT0_MENU_ID_5).getDishes().contains(dish0));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
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
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Menu expected = new Menu(null, LocalDate.now());
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_DATE_MENU)));
    }
}

