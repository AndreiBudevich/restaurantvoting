package by.restaurantvoting.testdata;

import by.restaurantvoting.model.Restaurant;

public class RestaurantTestData {
    public static final int START_SEQ = 0;

    public static final int RESTAURANT_ID_0 = START_SEQ;
    public static final int RESTAURANT_ID_1 = RESTAURANT_ID_0 + 1;
    public static final int RESTAURANT_ID_2 = RESTAURANT_ID_1 + 1;
    public static final int RESTAURANT_ID_3 = RESTAURANT_ID_2 + 1;
    public static final int NOT_FOUND = 4;

    public static final Restaurant restaurant0 = new Restaurant(RESTAURANT_ID_0, "Ваcильки", "г. Минск ул. тимирязева 67", "8-029-7634349");
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID_1, "Пицца Темпо", "г. Минск ул. багратиона 81", "8-029-5882922");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID_2, "Новый свет", "г. Минск ул. Варшавская 81", "8-044-7324144");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID_3, "Ресторан без меню", "без адреса", "без контактов");
}
