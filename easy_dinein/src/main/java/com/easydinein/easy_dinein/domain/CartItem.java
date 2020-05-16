package com.easydinein.easy_dinein.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Shahzeb on 05/08/2018.
 */
@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Integer Quantity;

    @ManyToOne
    private Dish dish;

    @ManyToOne
    private FoodOrder foodOrder;
}
