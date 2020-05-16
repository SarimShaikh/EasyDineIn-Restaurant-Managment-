package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.converters.DishCommandToDish;
import com.easydinein.easy_dinein.converters.DishToDishCommand;
import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.exceptions.DishExistsException;
import com.easydinein.easy_dinein.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class DishServiceImpl implements DishService{

        @Autowired
        private DishRepository dishRepository;
        @Autowired
        private DishCommandToDish dishCommandConverter;
        @Autowired
        private DishToDishCommand dishConverter;


    public Dish findByName(String name) {
        return dishRepository.findByName(name);
    }
    public DishCommand findById(Long id) { return  dishConverter.convert(dishRepository.findById(id).get());  }

     public Set<Dish> getDishes(){

         Set<Dish> dishSet = new HashSet<>();
         dishRepository.findAll().iterator().forEachRemaining(dishSet::add);
         return dishSet;
     }

    @Transactional
    public Dish addOrUpdateDish(DishCommand dishCommand) throws DishExistsException {
        if (DishExist(dishCommand.getId() ,dishCommand.getName())) {
            throw new DishExistsException("This dish name is already exists: "
                    +  dishCommand.getName());
        }

        DishCommand dishCom = findById(dishCommand.getId());
        if(dishCom != null) {
            if(dishCom.getImage() != null && dishCommand.getImage() == null) {
                dishCommand.setImage(dishCom.getImage());
            }
        }
        Dish dish = dishCommandConverter.convert(dishCommand);

        return dishRepository.save(dish);
    }

    @Override
    public void deleteById(Long idToDelete) {
        dishRepository.deleteById(idToDelete);
    }

    private boolean DishExist(Long id, String name) {
        Dish dish = dishRepository.findByName(name);
        if(dish != null && dish.getId() != id) {
            return true;
        }
        return false;
    }

}
