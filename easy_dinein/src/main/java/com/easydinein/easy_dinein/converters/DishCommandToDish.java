package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.domain.Category;
import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.domain.Restaurant;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class DishCommandToDish implements Converter<DishCommand,Dish> {

    private final ImagesCommandToImages imageConverter;

    public DishCommandToDish(ImagesCommandToImages imageConverter) {
        this.imageConverter = imageConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Dish convert(DishCommand source){
        if(source==null){
            return null;
        }

        final Dish dish=new Dish();
        dish.setId(source.getId());
        dish.setName(source.getName());
        dish.setDescription(source.getDescription());
        dish.setPrice(source.getPrice());
        dish.setEstimatedTimeToPrepare(source.getEstimatedTimeToPrepare());
        dish.setImage(source.getImage());

        if(source.getRestaurantId() != null) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(source.getRestaurantId());
            dish.setRestaurant(restaurant);
            restaurant.addDish(dish);
        }

        if(source.getCategoryId()!=null){
            Category category=new Category();
            category.setCategoryId(source.getCategoryId());
            dish.setCategory(category);
            category.addDish(dish);
        }

        return dish;
    }

}
