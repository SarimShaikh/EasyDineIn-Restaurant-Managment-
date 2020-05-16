package com.easydinein.easy_dinein.exceptions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DishExistsException extends Exception{

    public DishExistsException(@NotNull @NotEmpty String s) {
    }
}
