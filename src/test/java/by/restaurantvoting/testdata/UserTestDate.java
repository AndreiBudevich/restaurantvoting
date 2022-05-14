package by.restaurantvoting.testdata;

import by.restaurantvoting.MatcherFactory;
import by.restaurantvoting.model.Role;
import by.restaurantvoting.model.User;
import by.restaurantvoting.util.JsonUtil;

import java.util.Collections;
import java.util.Date;

public class UserTestDate {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER0_ID = 1;
    public static final int USER1_ID = USER0_ID + 1;
    public static final int USER2_ID = USER1_ID + 1;
    public static final int USER3_ID = USER2_ID + 1;
    public static final int ADMIN_ID = USER3_ID + 1;

    public static final String USER0_MAIL = "user0@yandex.ru";
    public static final String USER3_MAIL = "user3@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user0 = new User(USER0_ID, "User0", USER0_MAIL, "password0", Role.USER);
    public static final User user1 = new User(USER1_ID, "User1", "user1@yandex.ru", "password1", Role.USER);
    public static final User user2 = new User(USER2_ID, "User2", "user2@yandex.ru", "password2", Role.USER);
    public static final User user3 = new User(USER3_ID, "User3", "user3@yandex.ru", "password3", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER0_ID, "UpdatedName", USER0_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}


