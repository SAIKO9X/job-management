package com.job.management.modules.company.controllers;

import com.job.management.modules.company.dto.CreateCompanyDTO;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.usecases.CreateCompanyUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

  @InjectMocks
  private CompanyController companyController;

  @Mock
  private CreateCompanyUseCase createCompanyUseCase;

  private MockMvc mockMvc;

  @Test
  @DisplayName("Should create a new company")
  public void should_create_new_company() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO(
      "email@example.com", "password123", "Company Name", "Address", "12345678901234",
      "1234567890", "www.company.com", "Description");

    String createCompanyDTOJson = "{ \"email\": \"email@example.com\", \"password\": \"password123\", \"name\": \"Company Name\", \"address\": \"Address\", \"cnpj\": \"12345678901234\", \"phone\": \"1234567890\", \"website\": \"www.company.com\", \"description\": \"Description\" }";

    Company company = Company.builder()
      .email("email@example.com")
      .password("password123")
      .name("Company Name")
      .address("Address")
      .cnpj("12345678901234")
      .phone("1234567890")
      .website("www.company.com")
      .description("Description")
      .build();

    when(createCompanyUseCase.execute(createCompanyDTO)).thenReturn(company);

    mockMvc.perform(post("/company/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createCompanyDTOJson))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().json("{ \"email\": \"email@example.com\", \"name\": \"Company Name\", \"address\": \"Address\", \"cnpj\": \"12345678901234\", \"phone\": \"1234567890\", \"website\": \"www.company.com\", \"description\": \"Description\" }"));
  }

  @Test
  @DisplayName("Should return bad request when company creation fails")
  public void should_return_bad_request_when_company_creation_fails() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();

    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO(
      "email@example.com", "password123", "Company Name", "Address", "12345678901234",
      "1234567890", "www.company.com", "Description");

    String createCompanyDTOJson = "{ \"email\": \"email@example.com\", \"password\": \"password123\", \"name\": \"Company Name\", \"address\": \"Address\", \"cnpj\": \"12345678901234\", \"phone\": \"1234567890\", \"website\": \"www.company.com\", \"description\": \"Description\" }";

    when(createCompanyUseCase.execute(createCompanyDTO)).thenThrow(new RuntimeException("Company already exists"));

    mockMvc.perform(post("/company/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createCompanyDTOJson))
      .andExpect(status().isBadRequest());
  }
}
