package com.job.management.modules.candidate.useCases;

import com.job.management.exceptions.UserNotFoundException;
import com.job.management.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.job.management.modules.candidate.entities.Candidate;
import com.job.management.modules.candidate.repositories.CandidateRepository;
import com.job.management.modules.candidate.usecases.ProfileCandidateUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileCandidateUseCaseTest {

  @InjectMocks
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Test
  @DisplayName("Should return profile of a candidate")
  public void should_return_profile_of_candidate() {
    var candidateId = UUID.randomUUID();
    var candidate = Candidate.builder()
      .id(candidateId)
      .email("test@gmail.com")
      .description("Developer")
      .fullName("Test User")
      .location("City")
      .curriculum("Curriculum")
      .build();

    var profileResponse = ProfileCandidateResponseDTO.builder()
      .id(candidateId)
      .email("test@gmail.com")
      .description("Developer")
      .fullName("Test User")
      .location("City")
      .curriculum("Curriculum")
      .build();

    when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

    var result = profileCandidateUseCase.execute(candidateId);

    assertThat(result).isEqualTo(profileResponse);
  }

  @Test
  @DisplayName("Should throw UserNotFoundException when candidate is not found")
  public void should_throw_user_not_found_exception_when_candidate_not_found() {
    var candidateId = UUID.randomUUID();

    when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> profileCandidateUseCase.execute(candidateId))
      .isInstanceOf(UserNotFoundException.class);
  }
}
