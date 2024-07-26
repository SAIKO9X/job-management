package com.job.management.modules.candidate.controllers;

import com.job.management.modules.candidate.dto.AuthCandidateRequestDTO;
import com.job.management.modules.candidate.dto.AuthCandidateResponseDTO;
import com.job.management.modules.candidate.usecases.AuthCandidateUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthCandidateControllerTest {

  @InjectMocks
  private AuthCandidateController authCandidateController;

  @Mock
  private AuthCandidateUseCase authCandidateUseCase;

  @Test
  @DisplayName("Should return token when authentication is successful")
  public void should_return_token_when_authentication_successful() throws AuthenticationException {
    var authCandidateRequestDTO = new AuthCandidateRequestDTO("test@gmail.com", "password123");
    var authCandidateResponseDTO = new AuthCandidateResponseDTO("token", 1000L);

    when(authCandidateUseCase.execute(authCandidateRequestDTO)).thenReturn(authCandidateResponseDTO);

    var response = authCandidateController.auth(authCandidateRequestDTO);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(authCandidateResponseDTO);
  }

  @Test
  @DisplayName("Should return UNAUTHORIZED when authentication fails")
  public void should_return_UNAUTHORIZED_when_authentication_fails() throws AuthenticationException {
    var authCandidateRequestDTO = new AuthCandidateRequestDTO("test@gmail.com", "password123");

    when(authCandidateUseCase.execute(authCandidateRequestDTO)).thenThrow(AuthenticationException.class);

    var response = authCandidateController.auth(authCandidateRequestDTO);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }
}
