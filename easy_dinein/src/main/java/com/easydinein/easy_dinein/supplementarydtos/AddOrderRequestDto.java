package com.easydinein.easy_dinein.supplementarydtos;

import com.easydinein.easy_dinein.commands.FoodOrderCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Shahzeb on 02/08/2018.
 */
@Getter
@Setter
@NoArgsConstructor
public class AddOrderRequestDto {
    private String authkey;
    private String username;
    private FoodOrderCommand foodOrderCommand;
}
