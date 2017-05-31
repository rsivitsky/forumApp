package com.sivitsky.controller;

import com.sivitsky.domain.Message;
import com.sivitsky.domain.Topic;
import com.sivitsky.domain.User;
import com.sivitsky.repository.MessageRepository;
import com.sivitsky.repository.SectionRepository;
import com.sivitsky.repository.TopicRepository;
import com.sivitsky.repository.UserRepository;
import com.sivitsky.service.MessageService;
import com.sivitsky.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;

@Controller
@PropertySource("classpath:forum.properties")
public class HomeController {

    @Value("${model.messages-per-mainpage}")
    private int messagesPerPage;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    public static Pageable updatePageable(final Pageable source, final int size) {
        return new PageRequest(source.getPageNumber(), size, source.getSort());
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model, Pageable pageable) {
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("message", new Message());
        model.addAttribute("topics", topicRepository.findAll());
        Page<Message> messagePage = messageService.findByRating(updatePageable(pageable, messagesPerPage));
        PageWrapper<Message> page = new PageWrapper<Message>(messagePage, "/index");
        model.addAttribute("messages", page.getContent());
        model.addAttribute("page", page);
        return "index";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.POST)
    public String addNewPost(@Valid Message message, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        User user = new User();
        userRepository.save(user);
        Topic topic = new Topic();
        topic.setName("Name test");
        topic.setUser(user);
        topicRepository.save(topic);
        messageRepository.save(new Message(user, topic, message.getHeader(), message.getContent(), new Date()));
        model.addAttribute("messages", messageRepository.findAll());
        return "redirect:/index";
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String showAllPosts(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "result";
    }

    @RequestMapping("/sections/{id}")
    public String messagesBySectionFilter(@PathVariable Long id, Model model, Pageable pageable) {
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("message", new Message());
        model.addAttribute("topics", topicRepository.findAll());
        Page<Message> messagePage = messageService.findBySection(sectionService.findById(id), updatePageable(pageable, messagesPerPage));
        PageWrapper<Message> page = new PageWrapper<Message>(messagePage, "/index");
        model.addAttribute("messages", page.getContent());
        model.addAttribute("page", page);
        return "index";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String showAccessDenidedPage() {
        return "403";
    }
}
