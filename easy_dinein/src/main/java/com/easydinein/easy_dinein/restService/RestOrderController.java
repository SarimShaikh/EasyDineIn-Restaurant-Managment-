package com.easydinein.easy_dinein.restService;

import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.commands.FoodOrderCommand;
import com.easydinein.easy_dinein.commands.UserCommand;
import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.exceptions.UserAuthenticationFailedException;
import com.easydinein.easy_dinein.services.FoodOrderService;
import com.easydinein.easy_dinein.services.UserService;
import com.easydinein.easy_dinein.supplementarydtos.AddOrderRequestDto;
import com.easydinein.easy_dinein.supplementarydtos.AddOrderResponseDto;
import com.easydinein.easy_dinein.supplementarydtos.GenericRequestDto;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Shahzeb on 02/08/2018.
 */

@RestController
public class RestOrderController {

    FoodOrderService foodOrderService;
    UserService userService;

    public RestOrderController(FoodOrderService foodOrderService, UserService userService) {
        this.foodOrderService = foodOrderService;
        this.userService = userService;
    }

    @RequestMapping(value = "/add-order", method = RequestMethod.POST)
    public AddOrderResponseDto getDishes(@RequestBody AddOrderRequestDto addOrderRequestDto, BindingResult result, WebRequest request, Errors errors) {
        AddOrderResponseDto responseDto;
        try {
            UserCommand userCommand = userService.verifyUserByAuthKey(addOrderRequestDto.getAuthkey(), addOrderRequestDto.getUsername());
            addOrderRequestDto.getFoodOrderCommand().setUserId(userCommand.getId());
            responseDto = foodOrderService.addFoodOrder(addOrderRequestDto.getFoodOrderCommand());
        } catch (UserAuthenticationFailedException e) {
            responseDto = new AddOrderResponseDto();
            responseDto.setResponseCode("401");
            responseDto.setMessage("Failed to authenticate user.");
            responseDto.setStatus("Failure");
            e.printStackTrace();
        }
        return responseDto;
    }
}
