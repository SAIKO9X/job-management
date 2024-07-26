package com.job.management.modules.candidate.usecases;

import com.job.management.exceptions.UserFoundException;
import com.job.management.modules.candidate.entities.Candidate;
import com.job.management.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate execute(Candidate candidate) {
        this.candidateRepository.findByEmail(candidate.getEmail()).ifPresent((user) -> {
            throw new UserFoundException();
        });

        return this.candidateRepository.save(candidate);
    }
}
