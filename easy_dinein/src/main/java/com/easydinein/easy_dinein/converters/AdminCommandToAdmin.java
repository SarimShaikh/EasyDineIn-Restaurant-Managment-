package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.AdminCommand;
import com.easydinein.easy_dinein.domain.Admin;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AdminCommandToAdmin implements Converter<AdminCommand, Admin> {


    private final RoleCommandToRole roleConverter;

    public AdminCommandToAdmin(RoleCommandToRole roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Admin convert(AdminCommand source) {
        if(source == null) {
            return null;
        }

        final Admin admin = new Admin();
        admin.setId(source.getId());
        admin.setRole(roleConverter.convert(source.getRole()));
        admin.setName(source.getName());
        admin.setEmail(source.getEmail());
        admin.setPassword(source.getPassword());

        return admin;
    }
}
