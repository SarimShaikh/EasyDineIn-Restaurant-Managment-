package com.easydinein.easy_dinein.converters;

import com.easydinein.easy_dinein.commands.ImagesCommand;
import com.easydinein.easy_dinein.domain.Images;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ImagesToImagesCommand implements Converter<Images , ImagesCommand>{

    @Synchronized
    @Nullable
    @Override
    public ImagesCommand convert(Images source) {
        if (source == null) {
            return null;
        }

        final ImagesCommand command = new ImagesCommand();
        command.setId(source.getId());
        command.setPath(source.getPath());

        if (source.getDish() != null){

            command.setDishId(source.getDish().getId());
        }

        return command;
    }

}
