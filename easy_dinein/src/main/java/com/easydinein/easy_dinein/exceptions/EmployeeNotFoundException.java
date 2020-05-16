package com.easydinein.easy_dinein.exceptions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Shahzeb on 22/04/2018.
 */
public class EmployeeNotFoundException extends  Exception {
    public EmployeeNotFoundException(@NotNull @NotEmpty String s) {
    }
}
