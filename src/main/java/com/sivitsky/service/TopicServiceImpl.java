package com.sivitsky.service;

import com.sivitsky.domain.Topic;
import com.sivitsky.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Override
    public Topic createUpdate(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic findById(Long id) {
        return topicRepository.findOne(id);
    }

    @Override
    public Page<Topic> findAll(Pageable page) {
        return topicRepository.findAll(page);
    }
}
