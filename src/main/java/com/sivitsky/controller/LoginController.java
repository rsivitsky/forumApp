package com.sivitsky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class LoginController {
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
        System.out.print(email);
        return "redirect:/index";
    }
   @RequestMapping(value = "/index?logout", method = RequestMethod.GET)
    public ModelAndView getLogoutPage(@RequestParam Optional<String> logout) {
        return new ModelAndView("index", "logout", logout);
    }
}
