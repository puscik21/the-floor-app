package com.thefloorapp.repository;

import com.thefloorapp.entity.HelloEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for HelloEntry entities.
 */
@Repository
public interface HelloEntryRepository extends JpaRepository<HelloEntry, Long> {
}

