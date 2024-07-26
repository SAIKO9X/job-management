package com.job.management.modules.company.usecases;

import com.job.management.exceptions.CompanyNotFoundException;
import com.job.management.modules.company.entities.Jobs;
import com.job.management.modules.company.repositories.CompanyRepository;
import com.job.management.modules.company.repositories.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobsUseCase {

    @Autowired
    private JobsRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Jobs execute(Jobs jobEntity) {
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        return this.jobRepository.save(jobEntity);
    }
}
