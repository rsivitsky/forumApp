package com.sivitsky.service;

import com.sivitsky.domain.Message;
import com.sivitsky.domain.Section;
import com.sivitsky.domain.Topic;
import com.sivitsky.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Override
    public Page<Message> findAll(Pageable page) {
        return this.messageRepository.findAll(page);
    }

    @Override
    public Message findById(Long id) {
        return this.messageRepository.findOne(id);
    }

    @Override
    public Message createUpdate(Message message) {
        return this.messageRepository.save(message);
    }

    @Override
    public void deleteById(Long id) {
        this.messageRepository.delete(id);
    }

    @Override
    public Page<Message> findByRating(Pageable page) {
        return this.messageRepository.findByMaxRating(page);
    }

    @Override
    public Page<Message> findByTopic(Topic topic, Pageable page) {
        return this.messageRepository.findByTopic(topic, page);
    }

    @Override
    public Page<Message> findBySection(Section section, Pageable page) {
        return this.messageRepository.findBySection(section, page);
    }
}
