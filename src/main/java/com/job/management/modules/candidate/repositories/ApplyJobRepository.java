package com.job.management.modules.candidate.repositories;

import com.job.management.modules.candidate.entities.ApplyJobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ApplyJobRepository extends JpaRepository<ApplyJobs, UUID> {

}