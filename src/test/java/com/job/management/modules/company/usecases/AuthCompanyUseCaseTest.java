package com.job.management.modules.company.usecases;

import com.job.management.exceptions.EmailNotFoundException;
import com.job.management.modules.company.dto.AuthCompanyDTO;
import com.job.management.modules.company.dto.AuthCompanyResponseDTO;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthCompanyUseCaseTest {

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthCompanyUseCase authCompanyUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    authCompanyUseCase.setSecretKey("test_secret_key");
  }

  @Test
  void testExecuteEmailNotFound() {
    // Arrange
    AuthCompanyDTO authCompanyDTO = new AuthCompanyDTO("test@example.com", "password123");
    when(companyRepository.findByEmail(authCompanyDTO.email())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EmailNotFoundException.class, () -> {
      authCompanyUseCase.execute(authCompanyDTO);
    });
  }

  @Test
  void testExecuteInvalidPassword() {
    AuthCompanyDTO authCompanyDTO = new AuthCompanyDTO("test@example.com", "password123");
    Company company = new Company();
    company.setEmail("test@example.com");
    company.setPassword("encodedPassword");

    when(companyRepository.findByEmail(authCompanyDTO.email())).thenReturn(Optional.of(company));
    when(passwordEncoder.matches(authCompanyDTO.password(), company.getPassword())).thenReturn(false);

    assertThrows(AuthenticationException.class, () -> {
      authCompanyUseCase.execute(authCompanyDTO);
    });
  }
}
