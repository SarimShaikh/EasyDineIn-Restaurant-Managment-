package com.easydinein.easy_dinein.services;


import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.exceptions.DishExistsException;
import java.util.Set;

public interface DishService {

    Set<Dish> getDishes();
    Dish findByName(String name);
    DishCommand findById(Long id);
    Dish addOrUpdateDish(DishCommand dishCommand)throws DishExistsException;
    void deleteById(Long idToDelete);
}
