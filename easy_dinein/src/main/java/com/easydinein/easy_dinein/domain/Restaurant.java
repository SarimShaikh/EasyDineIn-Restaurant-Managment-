package com.easydinein.easy_dinein.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude="employee")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private Set<Dish> dishes = new HashSet<>();

    private String name;

    @Lob
    private String address;

    private String contactNumber;

    private String image;

    public Restaurant addDish(Dish dish) {
        dish.setRestaurant(this);
        this.dishes.add(dish);
        return this;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
