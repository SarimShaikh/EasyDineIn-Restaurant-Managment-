package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.EmployeeCommand;
import com.easydinein.easy_dinein.domain.Employee;
import com.easydinein.easy_dinein.domain.Restaurant;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCommandToEmployee implements Converter<EmployeeCommand, Employee> {

    private final RoleCommandToRole roleConverter;

    public EmployeeCommandToEmployee(RoleCommandToRole roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Employee convert(EmployeeCommand source){
        if(source==null){
            return null;
        }

        final Employee employee=new Employee();
        employee.setId(source.getId());
        employee.setName(source.getName());
        employee.setNumber(source.getNumber());
        employee.setEmail(source.getEmail());
        employee.setPassword(source.getPassword());

        if(employee.getRole() != null) {
            employee.setRole(roleConverter.convert(source.getRole()));
        }

        if(source.getRestaurantId() != null) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(source.getRestaurantId());
            employee.setRestaurant(restaurant);
            restaurant.setEmployee(employee);
        }

        return employee;

    }

}
