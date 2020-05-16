package com.easydinein.easy_dinein.controller;

import com.easydinein.easy_dinein.commands.UserCommand;
import com.easydinein.easy_dinein.exceptions.UserAuthenticationFailedException;
import com.easydinein.easy_dinein.services.UserService;
import com.easydinein.easy_dinein.supplementarydtos.UserLoginRequestDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLoginResponseDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLogoutRequestDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLogoutResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import static com.easydinein.easy_dinein.util.Utilities.getAuthKey;

@RestController
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService1) {
        this.userService = userService1;
    }

    @RequestMapping(value = "/user-registration", method = RequestMethod.POST)
    public UserLoginResponseDto registration(@RequestBody @Valid UserCommand userCommand, BindingResult result, WebRequest request, Errors errors){
        userCommand.setAuthkey(getAuthKey(8));
        UserLoginResponseDto responseDto = userService.registerNewUserAccount(userCommand);

        return responseDto;
    }


    @RequestMapping(value = "/user-login", method = RequestMethod.POST)
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto loginRequestDto , BindingResult result, WebRequest request, Errors errors){

        UserLoginResponseDto loginresponse = userService.userLogin(loginRequestDto);

        return  loginresponse;
    }


    @RequestMapping(value = "/user-logout", method = RequestMethod.POST)
    public UserLogoutResponseDto logout(@RequestBody UserLogoutRequestDto logoutRequestDto , BindingResult result, WebRequest request, Errors errors){
        UserLogoutResponseDto logoutresponse;
        try {
            UserCommand userCommand = userService.verifyUserByAuthKey(logoutRequestDto.getAuthkey(), logoutRequestDto.getUsername());
            logoutresponse = userService.userLogout(logoutRequestDto);
        } catch (UserAuthenticationFailedException e) {
            logoutresponse = new UserLogoutResponseDto();
            logoutresponse.setResponsecode("401");
            logoutresponse.setMessage("Failed to authenticate user.");
            logoutresponse.setStatus("Failure");
            e.printStackTrace();
        }

        return  logoutresponse;
    }

}
