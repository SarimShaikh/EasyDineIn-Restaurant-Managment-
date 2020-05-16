package com.easydinein.easy_dinein.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_order_id")
    Set<CartItem> cartItems = new HashSet<>();

    @ManyToOne
    private User user;

    private String status;

    @Lob
    private String comment;

    public FoodOrder addCartItem(CartItem cartItem) {
        cartItem.setFoodOrder(this);
        this.cartItems.add(cartItem);
        return this;
    }
}
