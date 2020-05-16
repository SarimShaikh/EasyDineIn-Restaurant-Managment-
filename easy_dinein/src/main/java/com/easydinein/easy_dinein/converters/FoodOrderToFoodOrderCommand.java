package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.FoodOrderCommand;
import com.easydinein.easy_dinein.commands.UserCommand;
import com.easydinein.easy_dinein.domain.FoodOrder;
import com.easydinein.easy_dinein.domain.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class FoodOrderToFoodOrderCommand implements Converter<FoodOrder, FoodOrderCommand> {

    private final CartItemToCartItemCommand cartItemConverter;

    public FoodOrderToFoodOrderCommand(CartItemToCartItemCommand cartItemConverter) {
        this.cartItemConverter = cartItemConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public FoodOrderCommand convert(FoodOrder source) {
        if(source == null) {
            return null;
        }

        final FoodOrderCommand command = new FoodOrderCommand();

        command.setId(source.getId());
        command.setStatus(source.getStatus());
        command.setComment(source.getComment());

        if(source.getUser() != null) {
            command.setUserId(source.getUser().getId());
        }

        if (source.getCartItems() != null && source.getCartItems().size() > 0){
            source.getCartItems()
                    .forEach( cartItem -> command.getCartItemCommands().add(cartItemConverter.convert(cartItem)));
        }


        return command;
    }

}
