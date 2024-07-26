package com.job.management.modules.company.repositories;

import com.job.management.modules.company.entities.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobsRepository extends JpaRepository<Jobs, UUID> {

    // "contains - LIKE " Select * from job where description like %filter%
    List<Jobs> findByDescriptionContainingIgnoreCase(String filter);
}
