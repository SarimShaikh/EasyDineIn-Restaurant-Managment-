package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.FoodOrderCommand;
import com.easydinein.easy_dinein.domain.FoodOrder;
import com.easydinein.easy_dinein.domain.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class FoodOrderCommandToFoodOrder implements Converter<FoodOrderCommand, FoodOrder> {

    private final CartItemCommandToCartItem cartItemConverter;

    public FoodOrderCommandToFoodOrder(CartItemCommandToCartItem cartItemConverter) {
        this.cartItemConverter = cartItemConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public FoodOrder convert(FoodOrderCommand source) {
        if(source == null) {
            return null;
        }

        final FoodOrder foodOrder = new FoodOrder();

        foodOrder.setId(source.getId());
        foodOrder.setStatus(source.getStatus());
        foodOrder.setComment(source.getComment());

        if(source.getUserId() != null) {
            User user = new User();
            user.setId(source.getUserId());
            foodOrder.setUser(user);
            user.addFoodOrder(foodOrder);
        }

        if (source.getCartItemCommands() != null && source.getCartItemCommands().size() > 0){
            source.getCartItemCommands()
                    .forEach( cartItem -> foodOrder.getCartItems().add(cartItemConverter.convert(cartItem)));
        }

        return foodOrder;
    }

}
