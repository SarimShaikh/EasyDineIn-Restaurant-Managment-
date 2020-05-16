package com.easydinein.easy_dinein.customvalidators;

import com.easydinein.easy_dinein.commands.EmployeeCommand;
import com.easydinein.easy_dinein.customannotations.PasswordMatches;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Adminpc on 4/8/2018.
 */

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        EmployeeCommand employee = (EmployeeCommand) obj;
        return employee.getPassword().equals(employee.getConfirmPassword());
    }
}
