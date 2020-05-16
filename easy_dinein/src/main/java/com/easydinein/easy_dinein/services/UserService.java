package com.easydinein.easy_dinein.services;


import com.easydinein.easy_dinein.commands.UserCommand;
import com.easydinein.easy_dinein.domain.User;
import com.easydinein.easy_dinein.exceptions.UserAuthenticationFailedException;
import com.easydinein.easy_dinein.supplementarydtos.UserLoginRequestDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLoginResponseDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLogoutRequestDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLogoutResponseDto;

public interface UserService {

    User findByNumber(String number);

    User findByUsername(String username);

    User findByEmail(String email);

    UserCommand findByAuthKey(String authkey);

    UserLoginResponseDto registerNewUserAccount(UserCommand userCommand);

    UserLoginResponseDto userLogin(UserLoginRequestDto loginRequestDto);

    UserLogoutResponseDto userLogout(UserLogoutRequestDto logoutRequestDto);

    UserCommand verifyUserByAuthKey(String authkey, String username) throws UserAuthenticationFailedException;
}
