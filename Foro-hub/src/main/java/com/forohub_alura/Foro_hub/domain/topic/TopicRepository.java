package com.forohub_alura.Foro_hub.domain.topic;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByMessageIgnoreCase(String message);

    Page<Topic> findByActiveTrue(Pageable paged);
}
