package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.commands.RestaurantCommand;
import com.easydinein.easy_dinein.converters.RestaurantCommandToRestaurant;
import com.easydinein.easy_dinein.converters.RestaurantToRestaurantCommand;
import com.easydinein.easy_dinein.domain.Restaurant;
import com.easydinein.easy_dinein.exceptions.NotFoundException;
import com.easydinein.easy_dinein.exceptions.RestaurantExistsException;
import com.easydinein.easy_dinein.repositories.EmployeeRepository;
import com.easydinein.easy_dinein.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RestaurantCommandToRestaurant restaurantConverter;
    @Autowired
    private RestaurantToRestaurantCommand restaurantConverter2;

    @Override
    public Restaurant findByName(String name) {
        return restaurantRepository.findByName(name);
    }

    @Override
    public Set<Restaurant> getRestaurants(){

        Set<Restaurant> restaurantSet = new HashSet<>();
        restaurantRepository.findAll().iterator().forEachRemaining(restaurantSet::add);
        return restaurantSet;
    }

    @Override
   public Restaurant findRestaurantById(Long l){

       Optional<Restaurant> restaurantOptional = restaurantRepository.findById(l);
       if(!restaurantOptional.isPresent()){
           throw new NotFoundException("Restaurant Not Found For ID value:"+l.toString());
       }
       return restaurantOptional.get();
   }


    public Restaurant findById(Long l) {

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(l);

        if (!restaurantOptional.isPresent()) {
            throw new NotFoundException("Restaurant Not Found. For ID value: " + l.toString() );
        }
        System.out.println(restaurantOptional);
        return restaurantOptional.get();
    }

    @Override
    @Transactional
    public RestaurantCommand findCommandById(Long l) {
        return restaurantConverter2.convert(findById(l));
    }


    @Transactional
    public Restaurant addOrUpdateRestaurant(RestaurantCommand restaurantCommand) throws RestaurantExistsException {
        if (ReataurantExist(restaurantCommand.getId() ,restaurantCommand.getName()) ) {
            throw new RestaurantExistsException("There is an restaurant with that name: "
                    +  restaurantCommand.getName());
        }

        RestaurantCommand restaurantCom = findCommandById(restaurantCommand.getId());
        if(restaurantCom != null) {
            if(restaurantCom.getImage() != null && restaurantCommand.getImage() != null) {
                restaurantCommand.setImage(restaurantCom.getImage());
            }
        }
        Restaurant restaurant = restaurantConverter.convert(restaurantCommand);

        return restaurantRepository.save(restaurant);
    }

    private boolean ReataurantExist(Long id, String name) {
        Restaurant restaurant = restaurantRepository.findByName(name);
        if(restaurant != null && restaurant.getId() != id) {
            return true;
        }
        return false;
    }


    @Override
    public void deleteById(Long idToDelete) {
        restaurantRepository.deleteById(idToDelete);
    }

    @Override
    public RestaurantCommand getRestaurantByEmployeeId(Long id) {
       return restaurantConverter2.convert(employeeRepository.findById(id).get().getRestaurant());
    }

}
