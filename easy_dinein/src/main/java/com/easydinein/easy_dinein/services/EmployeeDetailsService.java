package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.domain.Employee;
import com.easydinein.easy_dinein.domain.Role;
import com.easydinein.easy_dinein.repositories.EmployeeRepository;
import com.easydinein.easy_dinein.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Adminpc on 4/8/2018.
 */

@Service
@Transactional
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        Employee employee = employeeRepository.findByEmail(email);
        if(employee == null) {
            throw new UsernameNotFoundException("No username found with username: "+ email);
        }
        boolean enabled = true;
        boolean accountNotExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new CustomUser(employee.getEmail(), employee.getPassword(), enabled,
                accountNotExpired, credentialsNonExpired, accountNonLocked, getAuthorities(employee.getRole()), employee.getId());
    }

    private static List<GrantedAuthority> getAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getDesignation()));
        return authorities;
    }
}
