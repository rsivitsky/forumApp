package com.sivitsky.service;

import com.sivitsky.domain.Message;
import com.sivitsky.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Page<Message> findAll(Pageable page);

    Message findById(Long id);

    Message createUpdate(Message message);

    void deleteById(Long id);

    Page<Message> findByRating(Pageable page);

    Page<Message> findByTopic(Topic topic, Pageable page);
}
