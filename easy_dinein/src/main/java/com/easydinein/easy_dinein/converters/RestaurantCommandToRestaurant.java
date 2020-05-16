package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.RestaurantCommand;
import com.easydinein.easy_dinein.domain.Restaurant;
import com.easydinein.easy_dinein.services.EmployeeService;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RestaurantCommandToRestaurant implements Converter<RestaurantCommand, Restaurant> {

    private final DishCommandToDish dishConverter;
    private final EmployeeCommandToEmployee employeeConverter;
    private final EmployeeService employeeService;

    public RestaurantCommandToRestaurant(DishCommandToDish dishConverter, EmployeeCommandToEmployee employeeConverter, EmployeeService employeeService) {
        this.dishConverter = dishConverter;
        this.employeeConverter = employeeConverter;
        this.employeeService = employeeService;
    }

    @Synchronized
    @Nullable
    @Override
    public Restaurant convert(RestaurantCommand source) {
        if(source == null) {
            return null;
        }

        final Restaurant restaurant = new Restaurant();

        restaurant.setId(source.getId());
        restaurant.setAddress(source.getAddress());
        restaurant.setContactNumber(source.getContactNumber());
        restaurant.setName(source.getName());
        restaurant.setEmployee(employeeService.findById(source.getEmployeeId()));
        restaurant.setImage(source.getImage());
        if (source.getDishes() != null && source.getDishes().size() > 0){
            source.getDishes()
                    .forEach( dish -> restaurant.getDishes().add(dishConverter.convert(dish)));
        }

        return restaurant;
    }

}
