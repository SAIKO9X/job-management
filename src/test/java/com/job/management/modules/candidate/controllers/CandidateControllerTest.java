package com.job.management.modules.candidate.controllers;

import com.job.management.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.job.management.modules.candidate.entities.ApplyJobs;
import com.job.management.modules.candidate.entities.Candidate;
import com.job.management.modules.candidate.usecases.ApplyJobCandidateUseCase;
import com.job.management.modules.candidate.usecases.CreateCandidateUseCase;
import com.job.management.modules.candidate.usecases.ListAllJobsByFilterUseCase;
import com.job.management.modules.candidate.usecases.ProfileCandidateUseCase;
import com.job.management.modules.company.entities.Jobs;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CandidateControllerTest {

  @InjectMocks
  private CandidateController candidateController;

  @Mock
  private CreateCandidateUseCase createCandidateUseCase;

  @Mock
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Mock
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @Mock
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Test
  @DisplayName("Should create a new candidate successfully")
  public void should_create_new_candidate_successfully() {
    var candidate = Candidate.builder().email("test@gmail.com").password("password123").build();

    when(createCandidateUseCase.execute(candidate)).thenReturn(candidate);

    var response = candidateController.create(candidate);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(candidate);
  }

  @Test
  @DisplayName("Should return profile of a candidate")
  public void should_return_profile_of_candidate() {
    var candidateId = UUID.randomUUID();
    var profile = ProfileCandidateResponseDTO.builder()
      .id(candidateId)
      .email("test@gmail.com")
      .description("Developer")
      .fullName("Test User")
      .location("City")
      .curriculum("Curriculum")
      .build();

    when(profileCandidateUseCase.execute(candidateId)).thenReturn(profile);

    var request = mock(HttpServletRequest.class);
    when(request.getAttribute("candidate_id")).thenReturn(candidateId.toString());

    var response = candidateController.get(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(profile);
  }

  @Test
  @DisplayName("Should list all jobs by filter")
  public void should_list_all_jobs_by_filter() {
    var jobsList = List.of(Jobs.builder().id(UUID.randomUUID()).title("Java Developer").build());

    when(listAllJobsByFilterUseCase.execute("Developer")).thenReturn(jobsList);

    var response = candidateController.findJobByFilter("Developer");

    assertThat(response).isEqualTo(jobsList);
  }

  @Test
  @DisplayName("Should apply for a job")
  public void should_apply_for_job() {
    var jobId = UUID.randomUUID();
    var candidateId = UUID.randomUUID();
    var applyJob = ApplyJobs.builder().candidateId(candidateId).jobId(jobId).build();

    var request = mock(HttpServletRequest.class);
    when(request.getAttribute("candidate_id")).thenReturn(candidateId.toString());

    when(applyJobCandidateUseCase.execute(candidateId, jobId)).thenReturn(applyJob);

    var response = candidateController.applyJob(request, jobId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
