package com.easydinein.easy_dinein.commands;

import com.easydinein.easy_dinein.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminCommand {
    private Long id;
    private RoleCommand role;
    private String name;
    private String email;
    private String password;
}
