package com.job.management.modules.company.usecases;

import com.job.management.exceptions.CompanyAlreadyExistsException;
import com.job.management.modules.company.dto.CreateCompanyDTO;
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

  public Company execute(CreateCompanyDTO createCompanyDTO) {
    this.companyRepository.findByEmail(createCompanyDTO.email()).ifPresent((user) -> {
      throw new CompanyAlreadyExistsException();
    });

    var password = passwordEncoder.encode(createCompanyDTO.password());

    var company = Company.builder()
      .email(createCompanyDTO.email())
      .password(password)
      .name(createCompanyDTO.name())
      .address(createCompanyDTO.address())
      .cnpj(createCompanyDTO.cnpj())
      .phone(createCompanyDTO.phone())
      .website(createCompanyDTO.website())
      .description(createCompanyDTO.description())
      .build();

    return this.companyRepository.save(company);
  }
}
