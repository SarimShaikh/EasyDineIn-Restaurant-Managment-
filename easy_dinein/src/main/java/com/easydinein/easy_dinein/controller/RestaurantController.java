package com.easydinein.easy_dinein.controller;


import com.easydinein.easy_dinein.commands.RestaurantCommand;
import com.easydinein.easy_dinein.converters.RestaurantToRestaurantCommand;
import com.easydinein.easy_dinein.domain.Restaurant;
import com.easydinein.easy_dinein.exceptions.NotFoundException;
import com.easydinein.easy_dinein.exceptions.RestaurantExistsException;
import com.easydinein.easy_dinein.security.CustomUser;
import com.easydinein.easy_dinein.services.EmployeeService;
import com.easydinein.easy_dinein.services.ImageService;
import com.easydinein.easy_dinein.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RestaurantController {

    private static final String Restaurant_URL = "pannel/restaurants";

    private RestaurantService restaurantService;
    private RestaurantToRestaurantCommand restaurantConvertor;
    private Environment env;
    private ImageService imageService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, RestaurantToRestaurantCommand restaurantConvertor, Environment env, ImageService imageService) {
        this.restaurantService = restaurantService;
        this.restaurantConvertor = restaurantConvertor;
        this.env = env;
        this.imageService = imageService;
    }

    @RequestMapping(value ={"/panel/restaurant"}, method = RequestMethod.GET)
    public String registration(Model model) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RestaurantCommand restaurantCommand = restaurantService.getRestaurantByEmployeeId(customUser.getId());
        if(restaurantCommand == null) {
            restaurantCommand = new RestaurantCommand();
        }
        if(restaurantCommand.getImage() != null) {
            String imagePath = env.getProperty("restaurant.image.path");
            restaurantCommand.setImage(imageService.getImageBase64(imagePath, restaurantCommand.getImage()));
        }
        model.addAttribute("restaurant", restaurantCommand);
        return "pannel/restaurants";
    }

    //for delete
    @GetMapping("/panel/restaurant/{id}/delete")
    public String deleteById(@PathVariable String id){
        restaurantService.deleteById(Long.valueOf(id));
        return  "redirect:/panel/restaurant";
    }


    @RequestMapping(value = "/panel/restaurant", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("restaurant") @Valid RestaurantCommand restaurantCommand, @RequestParam MultipartFile imageFile, BindingResult result, WebRequest request, Errors errors) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        restaurantCommand.setEmployeeId(customUser.getId());
        if(!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            imageName = customUser.getId() + "-" + imageName.replaceAll("\\s+","-");
            restaurantCommand.setImage(imageName);
            String imagePath = env.getProperty("restaurant.image.path");
            imageService.uploadImage(imageFile, imagePath, customUser.getId().toString());
        }
        if(result.hasErrors()) {
            return new ModelAndView("pannel/restaurants", "restaurant", restaurantCommand);
        }

        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {
                System.out.println(objectError.toString());
            });
            return new ModelAndView(Restaurant_URL, "restaurant", restaurantCommand);
        }

        Restaurant registered = addRestaurant(restaurantCommand, result);

        if(registered == null) {
            result.rejectValue("name","message.regError");
        }
        return new ModelAndView("redirect:/panel/restaurant", "restaurant", registered);
    }

    private Restaurant addRestaurant(RestaurantCommand restaurantCommand, BindingResult result) {
     Restaurant registered =null;

     try{
         registered = restaurantService.addOrUpdateRestaurant(restaurantCommand);
     }catch (RestaurantExistsException ex){
         ex.printStackTrace();
         return null;
     }

     return registered;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        System.out.println(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }


}
