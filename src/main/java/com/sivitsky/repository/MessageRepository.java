package com.sivitsky.repository;

import com.sivitsky.domain.Message;
import com.sivitsky.domain.Section;
import com.sivitsky.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    String FIND_LAST_RAITING_QUERY = "SELECT m FROM Message m WHERE m.created IN " +
            "(SELECT MAX(mm.created) FROM Message mm GROUP BY mm.topic) ORDER BY m.created DESC ";

    String FIND_ALL_BY_SECTION = "SELECT m FROM Message m WHERE m.topic IN " +
            "(SELECT t FROM Topic t WHERE t.section =:section) GROUP BY m.topic ORDER BY m.created DESC ";

    Page<Message> findByTopic(Topic topic, Pageable pageable);

    List<Message> findByHeaderLike(String header);

    List<Message> findByContentLike(String content);

    List<Message> findByUser(String user);

    @Query(FIND_LAST_RAITING_QUERY)
    Page<Message> findByMaxRating(Pageable page);

    @Query(FIND_ALL_BY_SECTION)
    Page<Message> findBySection(@Param("section") Section section, Pageable page);

}
