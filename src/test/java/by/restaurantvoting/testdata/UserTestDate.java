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
    public static final int USER4_ID = USER3_ID + 1;
    public static final int USER5_ID = USER4_ID + 1;
    public static final int USER6_ID = USER5_ID + 1;
    public static final int USER7_ID = USER6_ID + 1;
    public static final int USER8_ID = USER7_ID + 1;
    public static final int USER9_ID = USER8_ID + 1;
    public static final int USER10_ID = USER9_ID + 1;
    public static final int USER11_ID = USER10_ID + 1;
    public static final int ADMIN_ID = USER11_ID + 1;
    public static final int GUEST_ID = ADMIN_ID + 1;

    public static final String USER0_MAIL = "user0@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user0 = new User(USER0_ID, "User0", USER0_MAIL, "password0", Role.USER);
    public static final User user1 = new User(USER1_ID, "User1", "user1@yandex.ru", "password1", Role.USER);
    public static final User user2 = new User(USER2_ID, "User2", "user2@yandex.ru", "password2", Role.USER);
    public static final User user3 = new User(USER3_ID, "User3", "user3@yandex.ru", "password3", Role.USER);
    public static final User user4 = new User(USER4_ID, "User4", "user4@yandex.ru", "password4", Role.USER);
    public static final User user5 = new User(USER5_ID, "User5", "user5@yandex.ru", "password5", Role.USER);
    public static final User user6 = new User(USER6_ID, "User6", "user6@yandex.ru", "password6", Role.USER);
    public static final User user7 = new User(USER7_ID, "User7", "user7@yandex.ru", "password7", Role.USER);
    public static final User user8 = new User(USER8_ID, "User8", "user8@yandex.ru", "password8", Role.USER);
    public static final User user9 = new User(USER9_ID, "User9", "user9@yandex.ru", "password9", Role.USER);
    public static final User user10 = new User(USER10_ID, "User10", "user10@yandex.ru", "password10", Role.USER);
    public static final User user11 = new User(USER11_ID, "User11 без голосования", "user11@yandex.ru", "password11", Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest", Role.USER);
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


