package by.restaurantvoting.model;

import by.restaurantvoting.View;
import by.restaurantvoting.util.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant", "menus"})
@Table(name = "dish", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"restaurant_id", "name", "description", "weight"},
                name = "restaurant_id_dish_name_description_weight_idx")})
public class Dish extends NamedEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @NoHtml
    private String description;

    @Column(name = "weight", nullable = false)
    @NotNull
    @Range(min = 5, max = 1500)
    private int weight;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 1000000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    @JsonBackReference
    @Schema(hidden = true)
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_dish",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    @Schema(hidden = true)
    private Set<Menu> menus;

    public Dish(Integer id, String name, String description, int weight, int price) {
        super(id, name);
        this.description = description;
        this.weight = weight;
        this.price = price;
    }
}
