package com.job.management.modules.company.controllers;

import com.job.management.modules.company.dto.CreateJobDTO;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.repositories.CompanyRepository;
import com.job.management.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private CompanyRepository companyRepository;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
      .apply(SecurityMockMvcConfigurers.springSecurity())
      .build();
  }

  @Test
  public void should_be_able_to_create_a_new_job() throws Exception {
    var company = Company.builder()
      .description("COMPANY_DESCRIPTION")
      .email("email@company.com")
      .password("1234567890")
      .name("COMPANY_NAME")
      .build();

    company = companyRepository.saveAndFlush(company);

    var createdJobDTO = CreateJobDTO.builder()
      .benefits("BENEFITS_TEST")
      .description("DESCRIPTION_TEST")
      .level("LEVEL_TEST")
      .title("TITLE_TEST")
      .requirements("REQUIREMENTS_TEST")
      .salary("SALARY_TEST")
      .location("LOCATION_TEST")
      .status("STATUS_TEST")
      .build();

    var result = mvc.perform(MockMvcRequestBuilders.post("/company/jobs/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJSON(createdJobDTO))
        .header("Authorization", TestUtils.generateToken(company.getId(), "JOB_MANAGEMENT-@123#")))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {
    var createdJobDTO = CreateJobDTO.builder()
      .benefits("BENEFITS_TEST")
      .description("DESCRIPTION_TEST")
      .level("LEVEL_TEST")
      .title("TITLE_TEST")
      .requirements("REQUIREMENTS_TEST")
      .salary("SALARY_TEST")
      .location("LOCATION_TEST")
      .status("STATUS_TEST")
      .build();

    mvc.perform(MockMvcRequestBuilders.post("/company/jobs/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJSON(createdJobDTO))
        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JOB_MANAGEMENT-@123#")))
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
