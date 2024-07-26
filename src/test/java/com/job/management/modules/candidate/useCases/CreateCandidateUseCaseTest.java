package com.job.management.modules.candidate.usecases;

import com.job.management.exceptions.UserFoundException;
import com.job.management.modules.candidate.entities.Candidate;
import com.job.management.modules.candidate.repositories.CandidateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCandidateUseCaseTest {

  @InjectMocks
  private CreateCandidateUseCase createCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Should create a new candidate successfully")
  public void should_create_new_candidate_successfully() {
    var candidate = Candidate.builder()
      .email("test@gmail.com")
      .password("password123")
      .build();

    when(candidateRepository.findByEmail(candidate.getEmail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(candidate.getPassword())).thenReturn("encodedPassword");
    when(candidateRepository.save(candidate)).thenReturn(candidate);

    var result = createCandidateUseCase.execute(candidate);

    assertThat(result).isNotNull();
    assertThat(result.getPassword()).isEqualTo("encodedPassword");
  }

  @Test
  @DisplayName("Should throw UserFoundException when email is already in use")
  public void should_throw_UserFoundException_when_email_already_in_use() {
    var candidate = Candidate.builder()
      .email("test@gmail.com")
      .password("password123")
      .build();

    when(candidateRepository.findByEmail(candidate.getEmail())).thenReturn(Optional.of(candidate));

    assertThrows(UserFoundException.class, () -> {
      createCandidateUseCase.execute(candidate);
    });
  }
}
