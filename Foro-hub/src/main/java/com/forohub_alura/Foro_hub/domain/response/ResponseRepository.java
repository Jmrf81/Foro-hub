package com.forohub_alura.Foro_hub.domain.response;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Long> {
    Page<Response> findByActiveTrue(Pageable paged);
}
