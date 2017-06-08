package com.sivitsky.controller;

import com.sivitsky.domain.Topic;
import com.sivitsky.domain.User;
import com.sivitsky.repository.SectionRepository;
import com.sivitsky.repository.UserRepository;
import com.sivitsky.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Date;

@PropertySource("classpath:forum.properties")
@Controller
public class TopicController {


    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/topic/add")
    public String addTopic(Model model) {
        model.addAttribute("topic", new Topic());
        model.addAttribute("sections", sectionRepository.findAll());
        return "newtopic";
    }

    @RequestMapping(value = "/topic/save", method = RequestMethod.POST)
    public String saveTopic(Topic topic, Principal principal) {
        if (principal != null) {
            User user;
            user = userRepository.findOneByEmail(principal.getName());
            topic.setUser(user);
            topic.setCreated(new Date());
            topicService.createUpdate(topic);
        }
        return "redirect:/message/add";
    }
}
