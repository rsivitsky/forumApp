package com.sivitsky.service;


import com.sivitsky.domain.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SectionService {
    Page<Section> findAll(Pageable page);

    Section findById(Long id);

    Section createUpdate(Section section);

    void deleteById(Long id);
}
