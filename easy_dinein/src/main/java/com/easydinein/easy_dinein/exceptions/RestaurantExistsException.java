package com.easydinein.easy_dinein.exceptions;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RestaurantExistsException extends Exception{
    public RestaurantExistsException(@NotNull @NotEmpty String s) {
    }


}
