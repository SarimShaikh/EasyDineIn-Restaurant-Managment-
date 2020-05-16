package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.commands.EmployeeCommand;
import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.domain.Employee;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEmployeeCommand implements Converter<Employee, EmployeeCommand> {

    private final RoleToRoleCommand roleConverter;

    public EmployeeToEmployeeCommand(RoleToRoleCommand roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public EmployeeCommand convert(Employee source){
        if(source==null){
            return null;
        }

        final EmployeeCommand command=new EmployeeCommand();
        command.setId(source.getId());
        command.setName(source.getName());
        command.setNumber(source.getNumber());
        command.setEmail(source.getEmail());
        command.setPassword(source.getPassword());

        if(command.getRole() != null) {
            command.setRole(roleConverter.convert(source.getRole()));
        }

        if(source.getRestaurant() != null) {
            command.setRestaurantId(source.getRestaurant().getId());
        }

        return command;

    }

}
