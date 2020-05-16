package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.FoodOrderCommand;
import com.easydinein.easy_dinein.commands.RestaurantCommand;
import com.easydinein.easy_dinein.domain.Employee;
import com.easydinein.easy_dinein.domain.FoodOrder;
import com.easydinein.easy_dinein.domain.Restaurant;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RestaurantToRestaurantCommand implements Converter<Restaurant, RestaurantCommand> {

    private final DishToDishCommand dishConverter;
    private final EmployeeToEmployeeCommand employeeConverter;

    public RestaurantToRestaurantCommand(DishToDishCommand dishConverter, EmployeeToEmployeeCommand employeeConverter) {
        this.dishConverter = dishConverter;
        this.employeeConverter = employeeConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RestaurantCommand convert(Restaurant source) {
        if(source == null) {
            return null;
        }

        final RestaurantCommand command = new RestaurantCommand();

        command.setId(source.getId());
        command.setAddress(source.getAddress());
        command.setContactNumber(source.getContactNumber());
        command.setName(source.getName());
        if(source.getEmployee() != null) {
            command.setEmployeeId(source.getEmployee().getId());
        }
        command.setImage(source.getImage());
        if (source.getDishes() != null && source.getDishes().size() > 0){
            source.getDishes()
                    .forEach( dish -> command.getDishes().add(dishConverter.convert(dish)));
        }

        return command;
    }

}
