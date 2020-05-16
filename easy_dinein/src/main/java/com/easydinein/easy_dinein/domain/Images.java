package com.easydinein.easy_dinein.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
@Entity
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MultipartFile[] path;

    @ManyToOne
    private Dish dish;
}
