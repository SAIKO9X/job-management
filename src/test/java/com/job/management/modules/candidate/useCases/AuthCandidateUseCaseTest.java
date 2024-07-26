package com.job.management.modules.candidate.usecases;

import com.job.management.exceptions.EmailNotFoundException;
import com.job.management.modules.candidate.dto.AuthCandidateRequestDTO;
import com.job.management.modules.candidate.entities.Candidate;
import com.job.management.modules.candidate.repositories.CandidateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthCandidateUseCaseTest {

  @InjectMocks
  private AuthCandidateUseCase authCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Value("${security.token.secret.candidate}")
  private String secretKey = "testSecretKey";

  @Test
  @DisplayName("Should throw EmailNotFoundException when email is not found")
  public void should_throw_EmailNotFoundException_when_email_not_found() {
    var authCandidateRequestDTO = new AuthCandidateRequestDTO("test@example.com", "password123");

    when(candidateRepository.findByEmail(authCandidateRequestDTO.email())).thenReturn(Optional.empty());

    assertThrows(EmailNotFoundException.class, () -> {
      authCandidateUseCase.execute(authCandidateRequestDTO);
    });
  }

  @Test
  @DisplayName("Should throw AuthenticationException when password is incorrect")
  public void should_throw_AuthenticationException_when_password_incorrect() {
    var authCandidateRequestDTO = new AuthCandidateRequestDTO("test@example.com", "password123");

    var candidate = Candidate.builder()
      .id(UUID.randomUUID())
      .email("test@example.com")
      .password("encodedPassword")
      .build();

    when(candidateRepository.findByEmail(authCandidateRequestDTO.email())).thenReturn(Optional.of(candidate));
    when(passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword())).thenReturn(false);

    assertThrows(AuthenticationException.class, () -> {
      authCandidateUseCase.execute(authCandidateRequestDTO);
    });
  }
}
