package com.easydinein.easy_dinein.controller;

import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.commands.RestaurantCommand;
import com.easydinein.easy_dinein.domain.Dish;
import com.easydinein.easy_dinein.domain.Employee;
import com.easydinein.easy_dinein.domain.Restaurant;
import com.easydinein.easy_dinein.exceptions.DishExistsException;
import com.easydinein.easy_dinein.security.CustomUser;
import com.easydinein.easy_dinein.services.DishService;
import com.easydinein.easy_dinein.services.EmployeeDetailsService;
import com.easydinein.easy_dinein.services.ImageService;
import com.easydinein.easy_dinein.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class DishController {

    private DishService dishService;
    private RestaurantService restaurantService;
    private Environment env;
    private ImageService imageService;

    @Autowired
    public DishController(DishService dishService, RestaurantService restaurantService, Environment env, ImageService imageService) {
        this.dishService = dishService;
        this.restaurantService = restaurantService;
        this.env = env;
        this.imageService = imageService;
    }

    @RequestMapping(value ={"/panel/dishes"}, method = RequestMethod.GET)
    public String getDishes(Model model, @ModelAttribute("dishObj") final DishCommand dishCommandObj) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RestaurantCommand restaurantCommand = restaurantService.getRestaurantByEmployeeId(customUser.getId());
        if (restaurantCommand != null) {
            DishCommand dishCommand = new DishCommand();
            if (dishCommandObj != null) {
                model.addAttribute("dish", dishCommandObj);
            }
            else {
                model.addAttribute("dish", dishCommand);
            }
            model.addAttribute("dishes", restaurantCommand.getDishes());
        }
        return "pannel/dishes";
    }

    @RequestMapping(value = "/panel/dishes", method = RequestMethod.POST)
    public ModelAndView addOrUpdateDish(@ModelAttribute("dish") @Valid DishCommand dishCommand, @RequestParam MultipartFile imageFile, BindingResult result, WebRequest request, Errors errors) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RestaurantCommand restaurantCommand = restaurantService.getRestaurantByEmployeeId(customUser.getId());
        if(!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            imageName = customUser.getId() + "-" + imageName.replaceAll("\\s+","-");
            dishCommand.setImage(imageName);
            String imagePath = env.getProperty("dish.image.path");
            imageService.uploadImage(imageFile, imagePath, customUser.getId().toString());
        }
        if(result.hasErrors()) {
            return new ModelAndView("pannel/dishes", "dish", dishCommand);
        }
        dishCommand.setRestaurantId(restaurantCommand.getId());
        Dish registered = addOrUpdateDish(dishCommand, result);

        if(registered == null) {
            result.rejectValue("name","message.regError");
        }

        dishCommand = new DishCommand();
        return new ModelAndView("redirect:/panel/dishes", "dish", dishCommand);
    }

    //for delete
    @GetMapping("/panel/dish/{id}/delete")
    public String deleteById(@PathVariable String id){
        dishService.deleteById(Long.valueOf(id));
        return  "redirect:/panel/dishes";
    }

    //for update
    @GetMapping("/panel/dish/{id}/update")
    public String updateById(@PathVariable String id, Model model, final RedirectAttributes redirectAttributes){
        DishCommand dishCommand = dishService.findById(Long.valueOf(id));
        if(dishCommand.getImage() != null) {
            String imagePath = env.getProperty("dish.image.path");
            dishCommand.setImage(imageService.getImageBase64(imagePath, dishCommand.getImage()));
        }
        redirectAttributes.addFlashAttribute("dishObj", dishCommand);

        return "redirect:/panel/dishes";
    }

    private Dish addOrUpdateDish(DishCommand dishCommand, BindingResult result) {
        Dish registered = null;

        try{
            registered = dishService.addOrUpdateDish(dishCommand);

        } catch (DishExistsException ex){
            ex.printStackTrace();
            return null;
        }

        return registered;
    }


}
