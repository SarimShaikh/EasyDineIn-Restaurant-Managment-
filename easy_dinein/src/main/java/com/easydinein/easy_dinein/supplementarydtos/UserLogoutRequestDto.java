package com.easydinein.easy_dinein.supplementarydtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserLogoutRequestDto {

    @NotNull
    @NotEmpty
    private String username ;

    @NotNull
    @NotEmpty
    private String authkey;

}
