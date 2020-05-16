package com.easydinein.easy_dinein.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Dish> dishes = new HashSet<>();


    public Category addDish(Dish dish) {
        dish.setCategory(this);
        this.dishes.add(dish);
        return this;
    }
}
