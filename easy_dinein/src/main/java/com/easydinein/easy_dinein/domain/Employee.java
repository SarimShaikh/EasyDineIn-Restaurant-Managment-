package com.easydinein.easy_dinein.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Employee {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private Restaurant restaurant;

    @OneToOne
    private Role role;

    private String name;
    private String email;
    private String password;
    private String number;
    private boolean enabled;

    public Employee() {
        super();
        this.enabled=false;
    }



}
