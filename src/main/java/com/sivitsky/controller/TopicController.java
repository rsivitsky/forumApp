package com.sivitsky.controller;

import com.sivitsky.domain.Topic;
import com.sivitsky.domain.User;
import com.sivitsky.repository.SectionRepository;
import com.sivitsky.repository.TopicRepository;
import com.sivitsky.repository.UserRepository;
import com.sivitsky.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@PropertySource("classpath:forum.properties")
@Controller
public class TopicController {


    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TopicRepository topicRepository;

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
    public String saveTopic(Topic topic, Principal principal, HttpServletRequest httpServletRequest) {
        if (principal != null) {
            User user;
            user = userRepository.findOneByEmail(principal.getName());
            topic.setUser(user);
            topic.setCreated(new Date());
            List<Topic> existTopic = topicRepository.findByNameLike(topic.getName());
            if(existTopic.size()==0){
                topicService.createUpdate(topic);
            }
            else{
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute("similarRecs", existTopic);
                return "redirect:/topic/add";
            }
        }
        return "redirect:/message/add";
    }
}
