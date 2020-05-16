package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.commands.FoodOrderCommand;
import com.easydinein.easy_dinein.supplementarydtos.AddOrderResponseDto;

/**
 * Created by Shahzeb on 05/08/2018.
 */
public interface FoodOrderService {

    AddOrderResponseDto addFoodOrder(FoodOrderCommand foodOrderCommand);
}
