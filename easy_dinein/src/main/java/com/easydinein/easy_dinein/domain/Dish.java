package com.easydinein.easy_dinein.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"orders", "restaurant"})
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private Category category;

    private String name;

    @Lob
    private String description;

    private String price;
    private String estimatedTimeToPrepare;
    private String image;
}
