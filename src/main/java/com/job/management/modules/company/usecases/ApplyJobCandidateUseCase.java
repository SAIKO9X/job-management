package com.job.management.modules.company.usecases;

import com.job.management.exceptions.JobNotFoundException;
import com.job.management.exceptions.UserNotFoundException;
import com.job.management.modules.candidate.entities.ApplyJobs;
import com.job.management.modules.candidate.repositories.ApplyJobRepository;
import com.job.management.modules.candidate.repositories.CandidateRepository;
import com.job.management.modules.company.repositories.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ApplyJobCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobsRepository jobsRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  public ApplyJobs execute(UUID idCandidate, UUID idJob) {
    this.candidateRepository.findById(idCandidate).orElseThrow(UserNotFoundException::new);
    this.jobsRepository.findById(idJob).orElseThrow(JobNotFoundException::new);

    var applyJob = ApplyJobs.builder().candidateId(idCandidate).jobId(idJob).build();
    applyJob = applyJobRepository.save(applyJob);

    return applyJob;
  }
}