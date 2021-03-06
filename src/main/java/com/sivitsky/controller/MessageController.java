package com.sivitsky.controller;

import com.sivitsky.domain.Message;
import com.sivitsky.domain.User;
import com.sivitsky.repository.MessageRepository;
import com.sivitsky.repository.SectionRepository;
import com.sivitsky.repository.TopicRepository;
import com.sivitsky.repository.UserRepository;
import com.sivitsky.service.MessageService;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.Date;

@Controller
@PropertySource("classpath:forum.properties")
@SessionAttributes("message")
public class MessageController {
    @Value("${model.messages-per-page}")
    private int messagesPerPage;

    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    public static Pageable updatePageable(final Pageable source, final int size) {
        return new PageRequest(source.getPageNumber(), size, source.getSort());
    }

    @RequestMapping(value = {"/topic/{id}"}, method = RequestMethod.GET)
    public String index(@PathVariable Long id, Model model, Pageable pageable) {
        model.addAttribute("sections", sectionRepository.findAll());
        Message mes = new Message(topicRepository.findOne(id), userRepository.findOne(2l));
        mes.setCreated(new Date());
        model.addAttribute("message", mes);
        Page<Message> messagePage = messageService.findByTopic(topicRepository.findOne(id), updatePageable(pageable, messagesPerPage));
        PageWrapper<Message> page = new PageWrapper<Message>(messagePage, "/topic/" + id);
        model.addAttribute("messages", page.getContent());
        model.addAttribute("page", page);
        return "topic";
    }

    @RequestMapping(value = {"/message/add"}, method = RequestMethod.GET)
    public String AddNewMessage(Model model, Principal principal) {
        model.addAttribute("topics", topicRepository.findAll());
        model.addAttribute("sections", sectionRepository.findAll());
        User user;
        Message message;
        if (principal != null) {
            user = userRepository.findOneByEmail(principal.getName());
            message = new Message(user);
        } else {
            message = new Message(userRepository.findOne(2l));
        }
        model.addAttribute("message", message);
        return "message";
    }

    @RequestMapping(value = {"/message/save"}, method = RequestMethod.POST)
    public String saveMessage(Message message) {
        message.setCreated(new Date());
        messageRepository.save(message);
        return "redirect:/topic/" + message.getTopic().getId();
    }

    @RequestMapping("message/delete/{id}")
    public String delete(@PathVariable Long id) {
        messageService.deleteById(id);
        return "redirect:/message";
    }

    @RequestMapping(value = {"/allmessages"}, method = RequestMethod.GET)
    public String listMessage(Model model, Pageable pageable, Principal principal) {
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("topics", topicRepository.findAll());
        Page<Message> messagePage = messageService.findByRating(updatePageable(pageable, messagesPerPage));
        PageWrapper<Message> page = new PageWrapper<Message>(messagePage, "/index");
        model.addAttribute("messages", page.getContent());
        model.addAttribute("page", page);
        if (principal != null) {
            User user = userRepository.findOneByEmail(principal.getName());
            Message message = new Message(user);
            model.addAttribute("message", message);
            return "listmessages";
        }
        return "redirect:/index/";
    }
}
