package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.domain.Dish;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class DishToDishCommand implements Converter<Dish,DishCommand> {

    private final ImagesToImagesCommand imageConverter;
    public DishToDishCommand(ImagesToImagesCommand imageConverter) {
        this.imageConverter = imageConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public DishCommand convert(Dish source){
        if(source==null){
            return null;
        }

        final DishCommand command=new DishCommand();
        command.setId(source.getId());
        command.setName(source.getName());
        command.setDescription(source.getDescription());
        command.setPrice(source.getPrice());
        command.setEstimatedTimeToPrepare(source.getEstimatedTimeToPrepare());
        command.setImage(source.getImage());

        if(source.getRestaurant() != null) {
            command.setRestaurantId(source.getRestaurant().getId());
        }
        return command;

    }

}
