package com.easydinein.easy_dinein.events;

import com.easydinein.easy_dinein.domain.Employee;
import com.easydinein.easy_dinein.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Shahzeb on 22/04/2018.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private EmployeeService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {

        Employee employee = event.getEmployee();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(employee, token);

//        System.out.println("testing");
        String recipientAddress = employee.getEmail();
        String subject = "DineIn Registration Confirmation";
        String comfirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + "http://localhost:8080" + comfirmationUrl);
        email.setFrom(env.getProperty("support.email"));

        mailSender.send(email);
    }
}
