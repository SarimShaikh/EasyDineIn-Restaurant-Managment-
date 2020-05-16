package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.UserCommand;
import com.easydinein.easy_dinein.domain.User;
import lombok.Synchronized;
import org.springframework.lang.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    //private final FoodOrderToFoodOrderCommand foodOrderConverter;



    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand source) {
        if(source == null) {
            return null;
        }

        final User user = new User();
        user.setId(source.getId());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setNumber(source.getNumber());
        user.setEmail(source.getEmail());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setAuthkey(source.getAuthkey());
        user.setSession(source.isSession());
        return user;
    }

}
