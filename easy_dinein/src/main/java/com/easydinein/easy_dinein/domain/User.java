package com.easydinein.easy_dinein.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    Set<FoodOrder> orders = new HashSet<>();

    private String firstName;
    private String lastName;
    private String number;
    private String email;
    private String username;
    private String password;
    private String authkey;
    private boolean session;

    public User addFoodOrder(FoodOrder order) {
        order.setUser(this);
        this.orders.add(order);
        return this;
    }
}
