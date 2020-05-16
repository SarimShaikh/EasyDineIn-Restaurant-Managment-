package com.easydinein.easy_dinein.events;

import com.easydinein.easy_dinein.domain.Employee;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by Shahzeb on 22/04/2018.
 */
@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private Employee employee;

    public OnRegistrationCompleteEvent(Employee employee, Locale locale, String appUrl) {
        super(employee);

        this.employee = employee;
        this.appUrl = appUrl;
        this.locale = locale;
    }
}
