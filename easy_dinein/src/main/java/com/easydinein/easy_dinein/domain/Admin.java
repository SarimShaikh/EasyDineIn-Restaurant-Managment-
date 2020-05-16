package com.easydinein.easy_dinein.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Role role;
    private String name;
    private String email;
    private String password;
}
