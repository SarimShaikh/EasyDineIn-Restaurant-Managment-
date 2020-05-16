package com.easydinein.easy_dinein.restService;

import com.easydinein.easy_dinein.commands.RestaurantCommand;
import com.easydinein.easy_dinein.converters.RestaurantToRestaurantCommand;
import com.easydinein.easy_dinein.domain.Restaurant;
import com.easydinein.easy_dinein.services.ImageService;
import com.easydinein.easy_dinein.services.RestaurantService;
import com.easydinein.easy_dinein.supplementarydtos.GenericRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RestController
public class RestRestaurantController {

    private RestaurantService restaurantService;
    private RestaurantToRestaurantCommand restaurantConvertor;
    private Environment env;
    private ImageService imageService;

    @Autowired
    public RestRestaurantController(RestaurantService restaurantService, RestaurantToRestaurantCommand restaurantConvertor,Environment env, ImageService imageService) {
        this.restaurantService = restaurantService;
        this.restaurantConvertor = restaurantConvertor;
        this.env = env;
        this.imageService =imageService;
    }

    @RequestMapping(value = "/show-restaurants", method = RequestMethod.POST)
    public Set<RestaurantCommand> getRestaurants(@RequestBody GenericRequestDto genericRequestDto, BindingResult result, WebRequest request, Errors errors) {
        Set<Restaurant> restaurantSet = restaurantService.getRestaurants();
        Set<RestaurantCommand> restaurantCommands = new HashSet<>();
        Iterator<Restaurant> it = restaurantSet.iterator();
        while(it.hasNext()) {
            restaurantCommands.add(restaurantConvertor.convert(it.next()));
        }
        for(RestaurantCommand restaurant:restaurantCommands){
            if(restaurant.getImage()!=null){
                String imagePath = env.getProperty("dish.image.path");
                restaurant.setImage(imageService.getImageBase64(imagePath, restaurant.getImage()));
            }
        }
        return restaurantCommands;
    }
}