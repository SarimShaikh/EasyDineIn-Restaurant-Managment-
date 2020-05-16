package com.easydinein.easy_dinein.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Shahzeb on 05/08/2018.
 */
@Getter
@Setter
@NoArgsConstructor
public class CartItemCommand {
    private Long id;
    private DishCommand dish;
    private Integer quantity;
    private Long foodOrderCommandId;
}
