package com.easydinein.easy_dinein.services;


import com.easydinein.easy_dinein.commands.RestaurantCommand;
import com.easydinein.easy_dinein.domain.Restaurant;
import com.easydinein.easy_dinein.exceptions.RestaurantExistsException;

import java.util.Set;

public interface RestaurantService {

    //void Save(Restaurant restaurant);
    Set<Restaurant> getRestaurants();
    Restaurant findRestaurantById(Long l);
    RestaurantCommand findCommandById(Long l);
    Restaurant findByName(String name);
    Restaurant addOrUpdateRestaurant(RestaurantCommand restaurantCommand) throws RestaurantExistsException;

    void deleteById(Long idToDelete);

    RestaurantCommand getRestaurantByEmployeeId(Long id);
}
