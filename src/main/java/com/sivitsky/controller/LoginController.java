package com.sivitsky.controller;

import com.sivitsky.domain.User;
import com.sivitsky.service.MailService;
import com.sivitsky.service.UserService;
import com.sparkpost.exception.SparkPostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

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
}
