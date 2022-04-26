package by.restaurantvoting.testdata;

import by.restaurantvoting.MatcherFactory;
import by.restaurantvoting.model.Dish;

public class DishTestData {

    public static final int DISH_ID_0 = 1;
    public static final int DISH_ID_1 = DISH_ID_0 + 1;
    public static final int DISH_ID_2 = DISH_ID_1 + 1;
    public static final int DISH_ID_3 = DISH_ID_2 + 1;
    public static final int DISH_ID_4 = DISH_ID_3 + 1;
    public static final int DISH_ID_5 = DISH_ID_4 + 1;
    public static final int DISH_ID_6 = DISH_ID_5 + 1;
    public static final int DISH_ID_7 = DISH_ID_6 + 1;
    public static final int DISH_ID_8 = DISH_ID_7 + 1;
    public static final int DISH_ID_9 = DISH_ID_8 + 1;
    public static final int DISH_ID_10 = DISH_ID_9 + 1;
    public static final int DISH_ID_11 = DISH_ID_10 + 1;
    public static final int DISH_ID_12 = DISH_ID_11 + 1;
    public static final int DISH_ID_13 = DISH_ID_12 + 1;

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "menus");

    public static final Dish dish0 = new Dish(DISH_ID_0, "Мачанка с драниками",
            "драники, куриное филе, ветчина, морковь, лук, шампиньоны, сливочный соус с укропом",
            290, 67);
    public static final Dish dish1 = new Dish(DISH_ID_1, "Салат с курицей и помидорами",
            "листья салата, морковь, куриное филе, помидоры, огурцы, заправка для салата",
            150, 32);
    public static final Dish dish2 = new Dish(DISH_ID_2, "Куриный суп с рисом",
            "картофель, куриное филе, лук, морковь, рис, зелень",
            250, 31);
    public static final Dish dish3 = new Dish(DISH_ID_3, "Салат с ветчиной и кукурузой",
            "листья салата, морковь, огурцы, ветчина, консервированная кукуруза, подаются с заправкой для салата",
            145, 34);
    public static final Dish dish4 = new Dish(DISH_ID_4, "Биточки с грибным соусом",
            "биточки из свинины, подаются со сливочным соусом с шампиньонами, помидорами и зеленью, картофельное пюре",
            335, 75);

    public static final Dish dish5 = new Dish(DISH_ID_5, "Пицца Народная",
            "соус из протертых томатов, Моцарелла, ветчина, шампиньоны, маринованные огурцы, приправа к пицце, масло чесночное",
            290, 67);
    public static final Dish dish6 = new Dish(DISH_ID_6, "Пицца Пикантная",
            "соус из протертых томатов, Моцарелла, курица, ананасы, приправа к пицце, масло чесночное",
            290, 67);
    public static final Dish dish7 = new Dish(DISH_ID_7, "Пицца Повседневная",
            "соус из протертых томатов, Моцарелла, салями, помидоры, приправа к пицце, масло чесночное",
            290, 67);
    public static final Dish dish8 = new Dish(DISH_ID_8, "Драники с жареной грудинкой и беконом",
            "подаются с жареной грудинкой, беконом и луком, сметаной, маринованными огурцами и зеленью",
            290, 103);
    public static final Dish dish9 = new Dish(DISH_ID_9, "Салат с курицей и помидорами",
            "листья салата, морковь, куриное филе, помидоры, огурцы, заправка для салата",
            290, 105);

    public static final Dish dish10 = new Dish(DISH_ID_10, "Тальята из говядины с вялеными томатами",
            "овядина на подушке из зелени",
            340, 133);
    public static final Dish dish11 = new Dish(DISH_ID_11, "Тартар из говядины",
            "кубики говядины, сырой перепелиный желток, красный лук, каперсы, маринованный огурец",
            250, 150);
    public static final Dish dish12 = new Dish(DISH_ID_12, "Суп кюфта-бозбаш",
            "ароматное сытное блюдо с тефтелей из баранины, нутом, рисом, картофелем и пряными травами",
            150, 100);

    public static final Dish dish13 = new Dish(DISH_ID_13, "Еда не в меню", "без описания",
            290, 105);

    public static Dish getNew() {
        return new Dish(null, "New", "New description", 200, 100);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID_0, "Updated name", "Updated description", 300, 800);
    }

}
