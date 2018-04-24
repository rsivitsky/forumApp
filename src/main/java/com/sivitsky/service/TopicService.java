package com.sivitsky.service;


import com.sivitsky.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {

    Topic createUpdate(Topic topic);

    Topic findById(Long id);

    Page<Topic> findAll(Pageable page);

}
