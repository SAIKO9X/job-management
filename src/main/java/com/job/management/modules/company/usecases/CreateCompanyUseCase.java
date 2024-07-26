package com.job.management.modules.company.usecases;

import com.job.management.exceptions.CompanyAlreadyExistsException;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public Company execute(Company company) {
        this.companyRepository.findByEmail(company.getEmail()).ifPresent(existingCompany -> {
            throw new CompanyAlreadyExistsException();
        });

        return this.companyRepository.save(company);
    }
}
