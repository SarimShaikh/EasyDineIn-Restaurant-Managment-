package com.easydinein.easy_dinein.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {

    @RequestMapping(value ={"/panel/order"}, method = RequestMethod.GET)
    public String DashboardView(Model model) {
        return "pannel/orders";
    }
}
