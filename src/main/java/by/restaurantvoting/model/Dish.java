package by.restaurantvoting.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name"}, name = "dish_restaurant_id_name_idx")})
public class Dish extends NamedEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "weight", nullable = false)
    @NotNull
    @Range(min = 5, max = 1500)
    private int weight;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 5000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_dish",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<Menu> menus;

    public Dish() {
    }

    public Dish(Integer id, String name, String description, int weight, int price, Restaurant restaurant) {
        super(id, name);
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.restaurant = restaurant;
    }
}
