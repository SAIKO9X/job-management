package com.job.management.modules.company.usecases;

import com.job.management.exceptions.UserFoundException;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public Company execute(Company company) {
        this.companyRepository.findByEmail(company.getEmail()).ifPresent((user) -> {
            throw new UserFoundException();
        });

        return this.companyRepository.save(company);
    }
}
