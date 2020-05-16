package com.easydinein.easy_dinein.controller;

import com.easydinein.easy_dinein.commands.EmployeeCommand;
import com.easydinein.easy_dinein.domain.Employee;
import com.easydinein.easy_dinein.domain.VerificationToken;
import com.easydinein.easy_dinein.events.OnRegistrationCompleteEvent;
import com.easydinein.easy_dinein.exceptions.EmailExistsException;
import com.easydinein.easy_dinein.services.EmployeeService;
import com.easydinein.easy_dinein.services.SecurityService;
import com.easydinein.easy_dinein.supplementarydtos.EmployeeLoginRequestDTO;
import com.easydinein.easy_dinein.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;



@Controller
public class EmployeeController {

    private EmployeeService employeeService;
    private SecurityService securityService;
    private ApplicationEventPublisher eventPublisher;
    private MessageSource messages;
    private JavaMailSender mailSender;
    private Environment env;

    @Autowired
    public EmployeeController(EmployeeService employeeService, SecurityService securityService, ApplicationEventPublisher eventPublisher, MessageSource messages, JavaMailSender mailSender, Environment env) {
        this.employeeService = employeeService;
        this.securityService = securityService;
        this.eventPublisher = eventPublisher;
        this.messages = messages;
        this.mailSender = mailSender;
        this.env = env;
    }

    @RequestMapping(value ={"/emp-registration"}, method = RequestMethod.GET)
    public String registration(Model model) {
        EmployeeCommand employee = new EmployeeCommand();
        model.addAttribute("employee", employee);
        return "employee/registration";
    }

    @RequestMapping(value = "/emp-registration", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("employee") @Valid EmployeeCommand employeeCommand, BindingResult result, WebRequest request, Errors errors) {

        if(result.hasErrors()) {
            return new ModelAndView("employee/registration", "employee", employeeCommand);
        }

        Employee registered = createEmployeeAccount(employeeCommand, result);

        if(registered == null) {
            result.rejectValue("email","message.regError");
        }
        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (Exception ex) {

            System.out.println(ex);
           return new ModelAndView("emailError", "employee", employeeCommand);

        }

        return new ModelAndView("employee/registrationSuccess", "employee", employeeCommand);
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        System.out.println(token);

        VerificationToken verificationToken = employeeService.getVerificationToken(token);
        if(verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + request.getLocale().getLanguage();
        }

        Employee employee = verificationToken.getEmployee();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            model.addAttribute("expired",true);
            model.addAttribute("token", token);
            return "redirect:/badUser.html?lang=" + request.getLocale().getLanguage();
        }

        employee.setEnabled(true);
        employeeService.saveRegisteredEmployee(employee);
        return "redirect:/emp-login.html?lang=" + request.getLocale().getLanguage();
    }

    @RequestMapping(value = "/employee/resendRegistrationToken", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
        final VerificationToken newToken = employeeService.generateNewVerificationToken(existingToken);

        Employee employee = employeeService.getEmployee(newToken.getToken());
        String appUrl = "http://" + request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();
        SimpleMailMessage email =
                constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, employee);
        mailSender.send(email);

        return new GenericResponse(
                messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    private SimpleMailMessage constructResendVerificationTokenEmail
            (String contextPath, Locale locale, VerificationToken newToken, Employee employee) {
        String confirmationUrl =
                contextPath + "/registrationConfirmation.html?TOKEN=" + newToken.getToken();
        String message = messages.getMessage("message.resendToken", null, locale);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        email.setTo(employee.getEmail());
        return email;
    }

    private Employee createEmployeeAccount(EmployeeCommand employeeCommand, BindingResult result) {
        Employee registered = null;
        try {
            registered = employeeService.registerNewEmployeeAccount(employeeCommand);
        } catch (EmailExistsException e) {
            e.printStackTrace();
            return null;
        }
        return registered;
    }

    @RequestMapping(value ={"/emp-login"}, method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("employeeLogin", new EmployeeLoginRequestDTO());
        return "employee/login";
    }

//    @RequestMapping(value ={"/emp-login"}, method = RequestMethod.POST)
//    public String login(@ModelAttribute @Valid EmployeeLoginRequestDTO employeeLoginRequestDTO, String error, String logout) {
//        return "employee/login";
//    }


    @RequestMapping(value = {"/", "/wellcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "wellcome";
    }


}
