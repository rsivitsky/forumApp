package com.sivitsky.controller;

import com.sivitsky.service.SectionService;
import com.sivitsky.service.UserService;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
    }

    @RequestMapping(value = "/restore", method = RequestMethod.GET)
    public String getRestorePage(Model model) {
        return "restore";
    }

    @RequestMapping(value = "/restore", method = RequestMethod.POST)
    public String setRestorePage(String email) {
        if (userService.getUserByEmail(email) != null) {
            //sparkpost service here
            System.out.println("user exist. we have send email into this adress");
        } else {
            System.out.print("user with this email does not exist");
        }

        return "redirect:/index";
    }
   @RequestMapping(value = "/index?logout", method = RequestMethod.GET)
    public ModelAndView getLogoutPage(@RequestParam Optional<String> logout) {
        return new ModelAndView("index", "logout", logout);
    }
}
