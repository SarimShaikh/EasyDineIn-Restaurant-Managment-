package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.commands.CartItemCommand;
import com.easydinein.easy_dinein.commands.FoodOrderCommand;
import com.easydinein.easy_dinein.converters.FoodOrderCommandToFoodOrder;
import com.easydinein.easy_dinein.domain.FoodOrder;
import com.easydinein.easy_dinein.repositories.FoodOrderRepository;
import com.easydinein.easy_dinein.supplementarydtos.AddOrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Shahzeb on 05/08/2018.
 */
@Service
public class FoodOrderServiceImpl implements FoodOrderService {

    FoodOrderCommandToFoodOrder foodConverter;
    FoodOrderRepository foodOrderRepository;

    public FoodOrderServiceImpl(FoodOrderCommandToFoodOrder foodConverter, FoodOrderRepository foodOrderRepository) {
        this.foodConverter = foodConverter;
        this.foodOrderRepository = foodOrderRepository;
    }

    @Override
    public AddOrderResponseDto addFoodOrder(FoodOrderCommand foodOrderCommand) {
        AddOrderResponseDto responseDto = new AddOrderResponseDto();
        FoodOrder foodOrder = foodConverter.convert(foodOrderCommand);
        foodOrder.setStatus("Pending");
        foodOrder = foodOrderRepository.save(foodOrder);
        int minsToPrepare = calculateMinsToPrepare(foodOrderCommand.getCartItemCommands());
        responseDto.setMinsToPrepare(minsToPrepare+""); //hard-coded for now
        responseDto.setOrderStatus("Pending");
        responseDto.setFoodOrderId(foodOrder.getId());
        responseDto.setStatus("Success");
        responseDto.setResponseCode("00");
        responseDto.setMessage("Order placed successfully. Waiting for approval.");
        return responseDto;
    }

    private int calculateMinsToPrepare(Set<CartItemCommand> cartItemCommands) {
        int minsToPrepare = 0;
        int estimatedTime = 0;
        for(CartItemCommand item : cartItemCommands) {
            estimatedTime = Integer.parseInt(item.getDish().getEstimatedTimeToPrepare());
            if(estimatedTime > minsToPrepare) {
                minsToPrepare = estimatedTime;
            }
        }
        minsToPrepare += 5;
        return minsToPrepare;
    }
}
