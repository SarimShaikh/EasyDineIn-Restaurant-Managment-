package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.CartItemCommand;
import com.easydinein.easy_dinein.domain.CartItem;
import com.easydinein.easy_dinein.domain.FoodOrder;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Shahzeb on 05/08/2018.
 */
@Component
public class CartItemCommandToCartItem implements Converter<CartItemCommand, CartItem> {

    private final DishCommandToDish dishConverter;

    public CartItemCommandToCartItem(DishCommandToDish dishConverter) {
        this.dishConverter = dishConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public CartItem convert(CartItemCommand source) {
        if(source == null) {
            return null;
        }
        CartItem cartItem = new CartItem();
        cartItem.setId(source.getId());
        cartItem.setDish(dishConverter.convert(source.getDish()));
        cartItem.setQuantity(source.getQuantity());

        if(source.getFoodOrderCommandId() != null) {
            FoodOrder foodOrder = new FoodOrder();
            foodOrder.setId(source.getFoodOrderCommandId());
            cartItem.setFoodOrder(foodOrder);
            foodOrder.addCartItem(cartItem);
        }

        return cartItem;
    }
}
