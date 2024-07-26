package com.job.management.modules.company.usecases;

import com.job.management.exceptions.CompanyNotFoundException;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.entities.Jobs;
import com.job.management.modules.company.repositories.CompanyRepository;
import com.job.management.modules.company.repositories.JobsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateJobsUseCaseTest {

  @Mock
  private JobsRepository jobRepository;

  @Mock
  private CompanyRepository companyRepository;

  @InjectMocks
  private CreateJobsUseCase createJobsUseCase;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testExecuteSuccess() {
    // Arrange
    Jobs job = new Jobs();
    job.setCompanyId(UUID.randomUUID());

    Company company = new Company();
    company.setId(UUID.randomUUID());

    when(companyRepository.findById(job.getCompanyId())).thenReturn(Optional.of(company));
    when(jobRepository.save(any(Jobs.class))).thenReturn(job);

    // Act
    Jobs result = createJobsUseCase.execute(job);

    // Assert
    assertNotNull(result);
    assertEquals(job.getCompanyId(), result.getCompanyId());
    verify(companyRepository, times(1)).findById(job.getCompanyId());
    verify(jobRepository, times(1)).save(job);
  }

  @Test
  void testExecuteCompanyNotFound() {
    // Arrange
    Jobs job = new Jobs();
    job.setCompanyId(UUID.randomUUID());

    when(companyRepository.findById(job.getCompanyId())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(CompanyNotFoundException.class, () -> {
      createJobsUseCase.execute(job);
    });
  }
}
