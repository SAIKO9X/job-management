package com.job.management.modules.company.usecases;

import com.job.management.modules.company.entities.Jobs;
import com.job.management.modules.company.repositories.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {

  @Autowired
  private JobsRepository jobsRepository;

  public List<Jobs> execute(String filter) {
    return this.jobsRepository.findByDescriptionContainingIgnoreCase(filter);
  }
}
