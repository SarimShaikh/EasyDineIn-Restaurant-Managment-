package com.easydinein.easy_dinein.repositories;

import com.easydinein.easy_dinein.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Shahzeb on 18/07/2018.
 */
public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByDesignation(String designation);
}
