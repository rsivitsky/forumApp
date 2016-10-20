package com.sivitsky.controller;

import com.sivitsky.domain.Message;
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

import java.util.Date;

@Controller
@PropertySource("classpath:forum.properties")
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
        model.addAttribute("message", new Message(topicRepository.findOne(id), userRepository.findOne(2l)));
        Page<Message> messagePage = messageService.findByTopic(topicRepository.findOne(id), updatePageable(pageable, messagesPerPage));
        PageWrapper<Message> page = new PageWrapper<Message>(messagePage, "/topic/" + id);
        model.addAttribute("messages", page.getContent());
        model.addAttribute("page", page);
        return "topic";
    }

    @RequestMapping(value = {"/message/save"}, method = RequestMethod.POST)
    public String saveMessage(Message message) {
        message.setCreated(new Date());
        messageRepository.save(message);
        return "redirect:/topic/" + message.getTopic().getId();
    }
}
