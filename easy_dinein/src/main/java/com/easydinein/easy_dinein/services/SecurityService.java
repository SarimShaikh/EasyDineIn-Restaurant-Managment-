package com.easydinein.easy_dinein.services;


public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);
}
