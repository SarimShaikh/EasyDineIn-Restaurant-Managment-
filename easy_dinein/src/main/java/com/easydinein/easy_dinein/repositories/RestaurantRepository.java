package com.easydinein.easy_dinein.repositories;


import com.easydinein.easy_dinein.domain.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant , Long>{

    Restaurant findByName(String name);
}
