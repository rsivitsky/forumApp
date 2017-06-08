package com.sivitsky.service;

import com.sivitsky.domain.Topic;
import com.sivitsky.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Override
    public Topic createUpdate(Topic topic) {
        return topicRepository.save(topic);
    }
}
