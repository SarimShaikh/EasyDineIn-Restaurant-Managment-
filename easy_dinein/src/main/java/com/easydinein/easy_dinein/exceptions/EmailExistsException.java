package com.easydinein.easy_dinein.exceptions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Shahzeb on 22/04/2018.
 */
public class EmailExistsException extends  Exception {
    public EmailExistsException(@NotNull @NotEmpty String s) {
    }
}
