package by.restaurantvoting.model;

import by.restaurantvoting.View;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant", "dishes"})
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_date", "restaurant_id"}, name = "menu_date_restaurant_id_idx")})
public class Menu extends BaseEntity {

    @Column(name = "menu_date", nullable = false, updatable = false)
    @NotNull
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_dish",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Set<Dish> dishes;

    public void setDish(Dish dish) {
        if (CollectionUtils.isEmpty(dishes)) {
            this.dishes = new HashSet<>();
            this.dishes.add(dish);
        } else {
            this.dishes.add(dish);
        }
    }

    public Menu(Integer id, LocalDate menuDate) {
        super(id);
        this.menuDate = menuDate;
    }
}
