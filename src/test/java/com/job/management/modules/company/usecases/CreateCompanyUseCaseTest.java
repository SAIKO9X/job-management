package com.job.management.modules.company.usecases;

import com.job.management.exceptions.CompanyAlreadyExistsException;
import com.job.management.modules.company.dto.CreateCompanyDTO;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCompanyUseCaseTest {

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private CreateCompanyUseCase createCompanyUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testExecuteSuccess() {
    // Arrange
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("test@example.com", "password123", "Test Company", "Address", "123456789", "123456789", "www.test.com", "Description");
    Company company = new Company();
    company.setEmail(createCompanyDTO.email());

    when(companyRepository.findByEmail(createCompanyDTO.email())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(createCompanyDTO.password())).thenReturn("encodedPassword");
    when(companyRepository.save(any(Company.class))).thenReturn(company);

    // Act
    Company result = createCompanyUseCase.execute(createCompanyDTO);

    // Assert
    assertNotNull(result);
    assertEquals(createCompanyDTO.email(), result.getEmail());
    verify(companyRepository, times(1)).findByEmail(createCompanyDTO.email());
    verify(passwordEncoder, times(1)).encode(createCompanyDTO.password());
    verify(companyRepository, times(1)).save(any(Company.class));
  }

  @Test
  void testExecuteCompanyAlreadyExists() {
    // Arrange
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("test@example.com", "password123", "Test Company", "Address", "123456789", "123456789", "www.test.com", "Description");
    Company existingCompany = new Company();
    existingCompany.setEmail(createCompanyDTO.email());

    when(companyRepository.findByEmail(createCompanyDTO.email())).thenReturn(Optional.of(existingCompany));

    // Act & Assert
    assertThrows(CompanyAlreadyExistsException.class, () -> {
      createCompanyUseCase.execute(createCompanyDTO);
    });
  }
}
