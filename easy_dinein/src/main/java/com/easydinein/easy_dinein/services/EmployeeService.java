package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.commands.EmployeeCommand;
import com.easydinein.easy_dinein.domain.Employee;
import com.easydinein.easy_dinein.domain.VerificationToken;
import com.easydinein.easy_dinein.exceptions.EmailExistsException;

public interface EmployeeService {

    void Save(Employee employee);

    Employee findByName(String name);

    Employee findById(Long id);

    Employee registerNewEmployeeAccount(EmployeeCommand employeeCommand) throws EmailExistsException;

    void createVerificationToken(Employee employee, String token);

    VerificationToken getVerificationToken(String token);

    void saveRegisteredEmployee(Employee employee);

    Employee getEmployee(String token);

    VerificationToken generateNewVerificationToken(String existingToken);
}
