package com.easydinein.easy_dinein.converters;


import com.easydinein.easy_dinein.commands.CategoryCommand;
import com.easydinein.easy_dinein.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand , Category>{

    private final DishCommandToDish dishConverter;

    public CategoryCommandToCategory(DishCommandToDish dishConverter) {
        this.dishConverter = dishConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if(source == null) {
            return null;
        }

    final Category category = new Category();

        category.setCategoryId(source.getCategoryId());
        category.setName(source.getName());

        if(source.getDishes() != null && source.getDishes().size() > 0){

            source.getDishes().forEach(dish->category.getDishes().add(dishConverter.convert(dish)));
        }

        return category;
    }


}
