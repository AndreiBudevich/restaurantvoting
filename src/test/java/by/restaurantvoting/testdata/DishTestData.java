package by.restaurantvoting.testdata;

import by.restaurantvoting.MatcherFactory;
import by.restaurantvoting.model.Dish;
import by.restaurantvoting.to.DishTo;

import java.util.List;

public class DishTestData {

    public static final int DISH_ID_0 = 1;
    public static final int DISH_ID_1 = DISH_ID_0 + 1;
    public static final int DISH_ID_2 = DISH_ID_1 + 1;
    public static final int DISH_ID_3 = DISH_ID_2 + 1;
    public static final int DISH_ID_4 = DISH_ID_3 + 1;
    public static final int DISH_ID_5 = DISH_ID_4 + 1;
    public static final int DISH_ID_6 = DISH_ID_5 + 1;

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "menus");
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

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
    public static final Dish dish5 = new Dish(DISH_ID_5, "Еда не в меню", "без описания",
            290, 105);
    public static final Dish dish6 = new Dish(DISH_ID_6, "Пицца Пикантная",
            "соус из протертых томатов, Моцарелла, курица, ананасы, приправа к пицце, масло чесночное",
            290, 67);

    public static List<Dish> dishesByRestaurant0 = List.of(dish0, dish1, dish2, dish3, dish4, dish5);

    public static Dish getNew() {
        return new Dish(null, "New", "New description", 200, 100);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID_0, "Updated name", "Updated description", 300, 800);
    }
}
