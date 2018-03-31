package com.sivitsky.controller;

import com.sivitsky.domain.ListRole;
import com.sivitsky.domain.User;
import com.sivitsky.domain.UserDto;
import com.sivitsky.service.MailService;
import com.sivitsky.service.RoleService;
import com.sivitsky.service.UserService;
import com.sparkpost.exception.SparkPostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
    }

    @RequestMapping(value = "/restore", method = RequestMethod.GET)
    public String getRestorePage(Model model) {
        return "restore";
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public String setRestorePage(String email) throws SparkPostException {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            String userMail = user.getEmail();
            String message = "Hi, " + userMail + ",\n your login is: " + userMail + " \n and your password is: " + user.getPassword();
            this.mailService.sendMailWithSparkPost("lastinegor.tk", email, "registration info on http://lastinegor.tk",
                    message, message);
        }
        return "redirect:/index";
    }

   @RequestMapping(value = "/index?logout", method = RequestMethod.GET)
    public ModelAndView getLogoutPage(@RequestParam Optional<String> logout) {
        return new ModelAndView("index", "logout", logout);
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String AddNewMessage(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/index";
        } else {
            model.addAttribute("user", new UserDto());
            return "registration";
        }
    }

    /*
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String saveRegistration(User user) {
        user.setRole(ListRole.ROLE_USER.toString());
        userService.createUpdate(user);
        return "redirect:/index";
    }*/

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid UserDto accountDto,
             BindingResult result, WebRequest request, Errors errors) {
        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        } else {
            return new ModelAndView("successRegister", "user", accountDto);
        }
    }

    private User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered = null;
        if (userService.getUserByEmail(accountDto.getEmail()) == null) {
            registered = userService.registerNewUserAccount(accountDto);
            return registered;
        }
        return registered;
    }
}
