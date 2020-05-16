package com.easydinein.easy_dinein.restService;

import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.converters.DishToDishCommand;
import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.services.DishService;
import com.easydinein.easy_dinein.services.ImageService;
import com.easydinein.easy_dinein.services.UserServiceImpl;
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
public class RestDishController {

    private DishService dishService;
    private DishToDishCommand dishConverter;
    private Environment env;
    private ImageService imageService;
    @Autowired
    public RestDishController(DishService dishService, DishToDishCommand dishConvertor,Environment env, ImageService imageService) {
        this.dishService = dishService;
        this.dishConverter = dishConvertor;
        this.env = env;
        this.imageService=imageService;
    }

    @RequestMapping(value = "/show-dishes", method = RequestMethod.POST)
    public Set<DishCommand> getDishes(@RequestBody GenericRequestDto genericRequestDto, BindingResult result, WebRequest request, Errors errors) {

        UserServiceImpl userService = new UserServiceImpl();

            Set<Dish> dishSet = dishService.getDishes();
            Set<DishCommand> dishCommands = new HashSet<>();
            Iterator<Dish> itr = dishSet.iterator();
            while (itr.hasNext()) {
                dishCommands.add(dishConverter.convert(itr.next()));
            }
            for (DishCommand d : dishCommands) {
                if (d.getImage() != null) {
                    String imagePath = env.getProperty("dish.image.path");
                    d.setImage(imageService.getImageBase64(imagePath, d.getImage()));
                }
            }

            return dishCommands;
    }
}
