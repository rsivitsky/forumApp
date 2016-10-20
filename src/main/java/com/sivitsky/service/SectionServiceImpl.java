package com.sivitsky.service;


import com.sivitsky.domain.Section;
import com.sivitsky.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    SectionRepository sectionRepository;

    @Override
    public Page<Section> findAll(Pageable page) {
        return sectionRepository.findAll(page);
    }

    @Override
    public Section findById(Long id) {
        return sectionRepository.findOne(id);
    }

    @Override
    public Section createUpdate(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public void deleteById(Long id) {
        sectionRepository.delete(id);
    }

}
