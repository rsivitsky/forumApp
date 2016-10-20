package com.sivitsky.controller;

import com.sivitsky.domain.User;
import com.sivitsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PropertySource("classpath:forum.properties")
public class UserController {

    @Value("${model.items-per-page}")
    private int itemsPerPage;

    @Autowired
    private UserService userService;

    public static Pageable updatePageable(final Pageable source, final int size) {
        return new PageRequest(source.getPageNumber(), size, source.getSort());
    }

    @RequestMapping("users/new")
    public String newProduct(Model model) {
        model.addAttribute("user", new User());
        return "userform";
    }

    @RequestMapping("users/edit/{id}")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "userform";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveProduct(User user) {
        userService.createUpdate(user);
        return "redirect:/users/" + user.getId();
    }

    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public String showUsers(Model model, Pageable pageable) {
        Page<User> userPage = userService.findAll(updatePageable(pageable, itemsPerPage));
        PageWrapper<User> page = new PageWrapper<User>(userPage, "/users");
        model.addAttribute("users", page.getContent());
        model.addAttribute("page", page);
        return "users";
    }

    @RequestMapping("users/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "usershow";
    }

    @RequestMapping("users/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
