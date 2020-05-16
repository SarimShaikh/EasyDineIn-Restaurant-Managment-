package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.UserCommand;
import com.easydinein.easy_dinein.domain.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    //private final FoodOrderToFoodOrderCommand foodOrderConverter;



    @Synchronized
    @Nullable
    @Override
    public UserCommand convert(User source) {
        if(source == null) {
            return null;
        }

        final UserCommand command = new UserCommand();
        command.setId(source.getId());
        command.setFirstName(source.getFirstName());
        command.setLastName(source.getLastName());
        command.setNumber(source.getNumber());
        command.setEmail(source.getEmail());
        command.setUsername(source.getUsername());
        command.setPassword(source.getPassword());
        command.setAuthkey(source.getAuthkey());
        command.setSession(source.isSession());
        return command;
    }

}
