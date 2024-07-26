package com.job.management.modules.candidate.repositories;

import com.job.management.modules.candidate.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    Optional<Candidate> findByEmail(String email);
}
