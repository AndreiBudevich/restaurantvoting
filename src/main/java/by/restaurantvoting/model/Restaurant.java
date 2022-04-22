package by.restaurantvoting.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "restaurant")
public class Restaurant extends NamedEntity {

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String address;

    @Column(name = "contacts", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String contacts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Menu> menus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String address, String contacts) {
        super(id, name);
        this.address = address;
        this.contacts = contacts;
    }
}
