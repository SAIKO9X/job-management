package com.job.management.modules.company.controllers;

import com.job.management.modules.company.dto.AuthCompanyDTO;
import com.job.management.modules.company.dto.AuthCompanyResponseDTO;
import com.job.management.modules.company.usecases.AuthCompanyUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.naming.AuthenticationException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthCompanyControllerTest {

  @InjectMocks
  private AuthCompanyController authCompanyController;

  @Mock
  private AuthCompanyUseCase authCompanyUseCase;

  private MockMvc mockMvc;

  @Test
  @DisplayName("Should authenticate company")
  public void should_authenticate_company() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(authCompanyController).build();

    AuthCompanyDTO authCompanyDTO = new AuthCompanyDTO("password", "email@example.com");
    String authCompanyDTOJson = "{ \"password\": \"password\", \"email\": \"email@example.com\" }";

    when(authCompanyUseCase.execute(authCompanyDTO)).thenReturn(new AuthCompanyResponseDTO("token", 7200000L));

    mockMvc.perform(post("/company/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .content(authCompanyDTOJson))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().json("{ \"access_token\": \"token\", \"expires_in\": 7200000 }"));
  }

  @Test
  @DisplayName("Should return unauthorized when authentication fails")
  public void should_return_unauthorized_when_authentication_fails() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(authCompanyController).build();

    AuthCompanyDTO authCompanyDTO = new AuthCompanyDTO("password", "email@example.com");
    String authCompanyDTOJson = "{ \"password\": \"password\", \"email\": \"email@example.com\" }";

    when(authCompanyUseCase.execute(authCompanyDTO)).thenThrow(AuthenticationException.class);

    mockMvc.perform(post("/company/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .content(authCompanyDTOJson))
      .andExpect(status().isUnauthorized());
  }
}
