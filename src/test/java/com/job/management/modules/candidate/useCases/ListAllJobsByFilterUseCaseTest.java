package com.job.management.modules.candidate.useCases;

import com.job.management.modules.candidate.usecases.ListAllJobsByFilterUseCase;
import com.job.management.modules.company.entities.Jobs;
import com.job.management.modules.company.repositories.JobsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListAllJobsByFilterUseCaseTest {

  @InjectMocks
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @Mock
  private JobsRepository jobsRepository;

  @Test
  @DisplayName("Should list all jobs by filter")
  public void should_list_all_jobs_by_filter() {
    var jobsList = List.of(Jobs.builder().id(UUID.randomUUID()).title("Java Developer").description("Developer").build());

    when(jobsRepository.findByDescriptionContainingIgnoreCase("Developer")).thenReturn(jobsList);

    var result = listAllJobsByFilterUseCase.execute("Developer");

    assertThat(result).isEqualTo(jobsList);
  }
}
