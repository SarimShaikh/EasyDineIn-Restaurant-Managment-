package com.easydinein.easy_dinein.supplementarydtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeLoginRequestDTO {

    @NotNull
    @NotEmpty
    private String email ;

    @NotNull
    @NotEmpty
    private String password;

}
