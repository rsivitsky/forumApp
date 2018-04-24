package com.sivitsky.controller;

import com.sivitsky.domain.Section;
import com.sivitsky.domain.Topic;
import com.sivitsky.domain.User;
import com.sivitsky.repository.SectionRepository;
import com.sivitsky.repository.TopicRepository;
import com.sivitsky.repository.UserRepository;
import com.sivitsky.service.TopicService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@PropertySource("classpath:forum.properties")
@Controller
public class TopicController {

    @Value("${model.items-per-page}")
    private int itemsPerPage;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserRepository userRepository;

    public static Pageable updatePageable(final Pageable source, final int size) {
        return new PageRequest(source.getPageNumber(), size, source.getSort());
    }

    @RequestMapping("/topic/add")
    public String addTopic(Model model) {
        model.addAttribute("topic", new Topic());
        model.addAttribute("sections", sectionRepository.findAll());
        return "newtopic";
    }

    @RequestMapping("/topics/edit/{id}")
    public String editSection(@PathVariable Long id, Model model, Pageable pageable) {
        Page<Topic> topicPage = topicService.findAll(updatePageable(pageable, itemsPerPage));
        PageWrapper<Topic> page = new PageWrapper<Topic>(topicPage, "/topics");
        model.addAttribute("topic", topicService.findById(id));
        model.addAttribute("topics", page.getContent());
        model.addAttribute("page", page);
        return "topics";
    }

    @RequestMapping("/topics")
    public String listTopics(Model model, Pageable pageable) {
        Page<Topic> topicPage = topicService.findAll(updatePageable(pageable, itemsPerPage));
        PageWrapper<Topic> page = new PageWrapper<Topic>(topicPage, "/topics");
        model.addAttribute("topics", page.getContent());
        model.addAttribute("topic", new Topic());
        model.addAttribute("page", page);
        return "topics";
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

    @RequestMapping(value = "/editTopic", method = RequestMethod.POST)
    public String saveProduct(Topic topic) {
        topicService.createUpdate(topic);
        return "redirect:topics";
    }
}