package com.job.management.modules.company.usecases;

import com.job.management.exceptions.CompanyAlreadyExistsException;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Company execute(Company company) {
        this.companyRepository.findByEmail(company.getEmail()).ifPresent((user) -> {
            throw new CompanyAlreadyExistsException();
        });

        var password = passwordEncoder.encode(company.getPassword());
        company.setPassword(password);

        return this.companyRepository.save(company);
    }
}
