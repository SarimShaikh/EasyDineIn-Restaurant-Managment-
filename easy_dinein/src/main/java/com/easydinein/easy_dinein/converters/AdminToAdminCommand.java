package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.AdminCommand;
import com.easydinein.easy_dinein.domain.Admin;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AdminToAdminCommand implements Converter<Admin, AdminCommand> {


    private final RoleToRoleCommand roleConverter;

    public AdminToAdminCommand(RoleToRoleCommand roleConverter) {
        this.roleConverter = roleConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public AdminCommand convert(Admin source) {
        if(source == null) {
            return null;
        }

        final AdminCommand command = new AdminCommand();
        command.setId(source.getId());
        command.setRole(roleConverter.convert(source.getRole()));
        command.setName(source.getName());
        command.setEmail(source.getEmail());
        command.setPassword(source.getPassword());

        return command;
    }
}
