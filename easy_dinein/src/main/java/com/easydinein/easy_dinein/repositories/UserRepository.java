package com.easydinein.easy_dinein.repositories;

import com.easydinein.easy_dinein.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByNumber(String number);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByAuthkey(String authkey);
}
