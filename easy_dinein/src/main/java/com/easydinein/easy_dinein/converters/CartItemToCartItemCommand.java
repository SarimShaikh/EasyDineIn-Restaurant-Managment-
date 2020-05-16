package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.CartItemCommand;
import com.easydinein.easy_dinein.domain.CartItem;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Shahzeb on 05/08/2018.
 */
@Component
public class CartItemToCartItemCommand implements Converter<CartItem, CartItemCommand> {

    private final DishToDishCommand dishConverter;

    public CartItemToCartItemCommand(DishToDishCommand dishConverter) {
        this.dishConverter = dishConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public CartItemCommand convert(CartItem source) {
        if(source == null) {
            return null;
        }
        final CartItemCommand cartItemCommand = new CartItemCommand();
        cartItemCommand.setId(source.getId());
        cartItemCommand.setDish(dishConverter.convert(source.getDish()));
        cartItemCommand.setQuantity(source.getQuantity());

        if(source.getFoodOrder() != null) {
            cartItemCommand.setFoodOrderCommandId(source.getFoodOrder().getId());
        }

        return  cartItemCommand;
    }
}
