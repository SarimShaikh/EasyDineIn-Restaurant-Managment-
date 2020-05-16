package com.easydinein.easy_dinein.repositories;


import com.easydinein.easy_dinein.domain.Dish;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish , Long> {

    Dish findByName(String name);
}
