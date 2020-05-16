package com.easydinein.easy_dinein.repositories;

import com.easydinein.easy_dinein.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {

  Employee findByName(String name);

  Employee findByEmail(String email);
}
