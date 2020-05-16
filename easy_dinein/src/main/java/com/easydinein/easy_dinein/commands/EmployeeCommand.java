package com.easydinein.easy_dinein.commands;

import com.easydinein.easy_dinein.customannotations.PasswordMatches;
import com.easydinein.easy_dinein.customannotations.ValidEmail;
import com.easydinein.easy_dinein.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
@Getter
@Setter
@NoArgsConstructor
public class EmployeeCommand {
    private Long id;
    private Long restaurantId;
    private RoleCommand role;

    @NotNull
    @NotEmpty
    private String name;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @NotNull
    @NotEmpty
    private String number;
}
