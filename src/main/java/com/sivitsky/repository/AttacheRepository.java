package com.sivitsky.repository;

import com.sivitsky.domain.Attache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttacheRepository extends JpaRepository<Attache, Long> {
}
