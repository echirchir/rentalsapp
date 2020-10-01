package com.chirchir.rm.controllers;

import com.chirchir.rm.models.User;
import com.chirchir.rm.services.SecurityService;
import com.chirchir.rm.services.UserService;
import com.chirchir.rm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/")
    public String home(){

        return "index";
    }

    @GetMapping("/register")
    public String registration(Model model){

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid User user, BindingResult bindingResult, Model model) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.save(user);

        securityService.autologin(user.getUsername(), user.getPasswordConfirm());

        return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout){

        if (error != null)
            model.addAttribute("error", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("message", "You have logged out successfully.");

        return "login";
    }

    @GetMapping("/logout")
    public String logout(){

        return "index";
    }
}
