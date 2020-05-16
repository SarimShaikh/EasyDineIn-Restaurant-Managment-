package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.RoleCommand;
import com.easydinein.easy_dinein.domain.Role;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RoleToRoleCommand implements Converter<Role, RoleCommand> {

    @Synchronized
    @Nullable
    @Override
    public RoleCommand convert(Role source) {
        if(source == null) {
            return null;
        }

        final RoleCommand command = new RoleCommand();
        command.setId(source.getId());
        command.setDesignation(source.getDesignation());

        return command;
    }

}
