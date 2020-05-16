package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.commands.EmployeeCommand;
import com.easydinein.easy_dinein.converters.EmployeeCommandToEmployee;
import com.easydinein.easy_dinein.domain.Employee;
import com.easydinein.easy_dinein.domain.VerificationToken;
import com.easydinein.easy_dinein.exceptions.EmailExistsException;
import com.easydinein.easy_dinein.repositories.EmployeeRepository;
import com.easydinein.easy_dinein.repositories.RoleRepository;
import com.easydinein.easy_dinein.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private EmployeeCommandToEmployee employeeConverter;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void Save(Employee employee) {
        employee.setPassword((employee.getPassword()));
        employeeRepository.save(employee);
    }

    @Override
    public Employee findByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Employee registerNewEmployeeAccount(EmployeeCommand employeeCommand) throws EmailExistsException {
        if (emailExist(employeeCommand.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: "
                    +  employeeCommand.getEmail());
        }
        Employee employee = employeeConverter.convert(employeeCommand);
        employee.setRole(roleRepository.findByDesignation("ROLE_EMP"));
        return employeeRepository.save(employee);
    }

    @Override
    public void createVerificationToken(Employee employee, String token) {
        final VerificationToken empToken = new VerificationToken(token, employee);
        tokenRepository.save(empToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void saveRegisteredEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken != null) {
            return verificationToken.getEmployee();
        }
        return null;
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        return tokenRepository.findByToken(existingToken);
    }

    private boolean emailExist(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        if(employee != null) {
            return true;
        }
        return false;
    }
}
