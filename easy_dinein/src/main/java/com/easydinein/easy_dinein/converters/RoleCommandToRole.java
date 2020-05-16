package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.RoleCommand;
import com.easydinein.easy_dinein.domain.Role;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RoleCommandToRole implements Converter<RoleCommand, Role> {

    @Synchronized
    @Nullable
    @Override
    public Role convert(RoleCommand source) {
        if(source == null) {
            return null;
        }

        final Role role = new Role();
        role.setId(source.getId());
        role.setDesignation(source.getDesignation());

        return role;
    }

}
