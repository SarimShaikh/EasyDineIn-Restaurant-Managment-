package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.ImagesCommand;
import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.domain.Images;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ImagesCommandToImages implements Converter<ImagesCommand , Images> {

    @Synchronized
    @Nullable
    @Override
    public Images convert(ImagesCommand source) {
        if (source == null) {
            return null;
        }

        final Images images = new Images();
        images.setId(source.getId());
        images.setPath(source.getPath());

        if (source.getDishId() != null){
            Dish dish = new Dish();
            dish.setId(source.getId());
            images.setDish(dish);
//            dish.addimages(images);
        }

         return images;
    }
    }
