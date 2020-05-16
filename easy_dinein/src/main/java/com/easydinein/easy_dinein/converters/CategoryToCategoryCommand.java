package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.CategoryCommand;
import com.easydinein.easy_dinein.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category , CategoryCommand>{

    private final DishToDishCommand dishConverter;
    public CategoryToCategoryCommand(DishToDishCommand dishConverter) {
        this.dishConverter = dishConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source){
        if(source == null){
            return null;
        }

        final CategoryCommand command = new CategoryCommand();

        command.setCategoryId(source.getCategoryId());
        command.setName(source.getName());

        if (source.getDishes() != null && source.getDishes().size() > 0){
            source.getDishes()
                    .forEach( dish -> command.getDishes().add(dishConverter.convert(dish)));
        }

        return command;
    }

}
