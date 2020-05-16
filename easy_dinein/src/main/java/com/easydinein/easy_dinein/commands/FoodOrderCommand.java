package com.easydinein.easy_dinein.commands;

import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FoodOrderCommand {

    private Long id;
    private Set<CartItemCommand> cartItemCommands = new HashSet<>();
    private Long userId;
    private String status;
    private String comment;
}
