package com.sivitsky.repository;

import com.sivitsky.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    public final static String FIND_BY_NAME_LIKE_QUERY = "SELECT t " +
            "FROM Topic t " +
            "WHERE t.name LIKE :name";

   /* public final static String FIND_BY_SECTION_LIKE_QUERY = "SELECT t " +
            "FROM Topic t " +
            "WHERE t.section = :section";*/

    @Query(FIND_BY_NAME_LIKE_QUERY)
    List<Topic> findByNameLikeNew(@Param("name") String name);
/*
    @Query(FIND_BY_SECTION_LIKE_QUERY)
    List<Topic> findBySection(@Param("section") Section section);*/

    List<Topic> findByNameLike(String name);

}
