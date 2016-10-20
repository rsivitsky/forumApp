package com.sivitsky.repository;

import com.sivitsky.domain.Section;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends PagingAndSortingRepository<Section, Long> {
}
