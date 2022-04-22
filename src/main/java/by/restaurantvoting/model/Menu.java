package by.restaurantvoting.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id"}, name = "restaurant_id_date_idx")})
public class Menu extends BaseEntity {

    @Column(name = "date", nullable = false, updatable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_dish",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Set<Dish> dishes;

    public void setDish(Dish dish) {
        if (CollectionUtils.isEmpty(dishes)) {
            this.dishes = new HashSet<>();
        } else {
            this.dishes.add(dish);
        }
    }

    public Menu() {
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
    }
}
