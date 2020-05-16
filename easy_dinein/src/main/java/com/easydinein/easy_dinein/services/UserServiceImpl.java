package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.commands.UserCommand;
import com.easydinein.easy_dinein.converters.UserCommandToUser;
import com.easydinein.easy_dinein.converters.UserToUserCommand;
import com.easydinein.easy_dinein.domain.User;
import com.easydinein.easy_dinein.exceptions.UserAuthenticationFailedException;
import com.easydinein.easy_dinein.repositories.UserRepository;
import com.easydinein.easy_dinein.supplementarydtos.UserLoginRequestDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLoginResponseDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLogoutRequestDto;
import com.easydinein.easy_dinein.supplementarydtos.UserLogoutResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.easydinein.easy_dinein.util.Utilities.getAuthKey;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCommandToUser userConverter;

    @Autowired
    private UserToUserCommand userConverter2;

    @Override
    public User findByNumber(String number) {
        return userRepository.findByNumber(number);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserCommand findByAuthKey(String authkey) {
        return userConverter2.convert(userRepository.findByAuthkey(authkey));
    }


    @Transactional
    @Override
    public UserLoginResponseDto registerNewUserAccount(UserCommand userCommand){

        UserLoginResponseDto  userLoginResponseDto = new UserLoginResponseDto();

        if (numberExist(userCommand.getNumber())) {
            userLoginResponseDto.setStatus("Failure");
            userLoginResponseDto.setResponsecode("201");
            userLoginResponseDto.setMessage("PhoneNumber already Exists");
            userLoginResponseDto.setAuthkey("NA");
            return userLoginResponseDto;
        }


        if (emailExist(userCommand.getEmail())) {
            userLoginResponseDto.setStatus("Failure");
            userLoginResponseDto.setResponsecode("201");
            userLoginResponseDto.setMessage("Email already Exists");
            userLoginResponseDto.setAuthkey("NA");
            return userLoginResponseDto;
        }

        if (nameExist(userCommand.getUsername())) {
            userLoginResponseDto.setStatus("Failure");
            userLoginResponseDto.setResponsecode("201");
            userLoginResponseDto.setMessage("UserName already Exists");
            userLoginResponseDto.setAuthkey("NA");
            return userLoginResponseDto;
        }

        User user = userConverter.convert(userCommand);
        user.setSession(true);
        userRepository.save(user);

        userLoginResponseDto.setStatus("Success");
        userLoginResponseDto.setResponsecode("00");
        userLoginResponseDto.setMessage("Registered Sucessfully");
        userLoginResponseDto.setAuthkey(user.getAuthkey());
        return userLoginResponseDto;
    }

    //method for user login
    public UserLoginResponseDto userLogin(UserLoginRequestDto loginRequestDto){

        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        User user = userRepository.findByUsername(loginRequestDto.getUsername());

        if(user!=null){
            if(loginRequestDto.getPassword().equals(user.getPassword())){
                user.setAuthkey(getAuthKey(8));
                user.setSession(true);
                userRepository.save(user);
                userLoginResponseDto.setAuthkey(user.getAuthkey());
                userLoginResponseDto.setStatus("Success");
                userLoginResponseDto.setMessage("NA");
                userLoginResponseDto.setResponsecode("00");
                return userLoginResponseDto;
            } else{
                userLoginResponseDto.setAuthkey("NA");
                userLoginResponseDto.setStatus("Failure");
                userLoginResponseDto.setMessage("Incorrect Password");
                userLoginResponseDto.setResponsecode("1001");
                return userLoginResponseDto;
            }
        } else{
            userLoginResponseDto.setAuthkey("NA");
            userLoginResponseDto.setStatus("Failure");
            userLoginResponseDto.setMessage("Username doesn't exist");
            userLoginResponseDto.setResponsecode("1002");
            return userLoginResponseDto;
        }
    }

    public UserLogoutResponseDto userLogout(UserLogoutRequestDto logoutRequestDto){

        UserLogoutResponseDto logoutResponseDto = new UserLogoutResponseDto();
        User user = userRepository.findByUsername(logoutRequestDto.getUsername());

        if(user != null) {
            user.setSession(false);
            userRepository.save(user);
            logoutResponseDto.setResponsecode("00");
            logoutResponseDto.setStatus("Success");
            logoutResponseDto.setMessage("NA");
            return logoutResponseDto;
        } else {
            logoutResponseDto.setResponsecode("202");
            logoutResponseDto.setStatus("Failure");
            logoutResponseDto.setMessage("UserException");
            return logoutResponseDto;
        }
    }

    @Override
    public UserCommand verifyUserByAuthKey(String authkey, String username) throws  UserAuthenticationFailedException {
        UserCommand userCommand = findByAuthKey(authkey);
        if(userCommand == null || !userCommand.getUsername().equals(username)) {
            throw new UserAuthenticationFailedException();
        }
        return  userCommand;
    }


    private boolean numberExist(String number) {
        User user = userRepository.findByNumber(number);
        if(user != null) {
            return true;
        }
        return false;
    }


    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            return true;
        }
        return false;
    }

    private boolean nameExist(String name) {
        User user = userRepository.findByUsername(name);
        if(user != null) {
            return true;
        }
        return false;
    }


}
