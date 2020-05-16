package com.easydinein.easy_dinein.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DishCommand {
    private Long id;
    private Long restaurantId;
    private Long categoryId;

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private String price;
    @NotNull
    @NotEmpty
    private String estimatedTimeToPrepare;

    private String image;

}
