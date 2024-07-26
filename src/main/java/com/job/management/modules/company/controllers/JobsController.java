package com.job.management.modules.company.controllers;

import com.job.management.modules.company.dto.CreateJobDTO;
import com.job.management.modules.company.entities.Jobs;
import com.job.management.modules.company.usecases.CreateJobsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/jobs")
public class JobsController {

  @Autowired
  private CreateJobsUseCase createJobsUseCase;

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  @Tag(name = "Vagas", description = "Informações das vagas")
  @Operation(
    summary = "Cadastro de vagas",
    description = "Essa função é responsável por cadastrar as vagas dentro da empresa"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = Jobs.class))
    })
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    try {
      var jobs = Jobs.builder()
        .companyId(UUID.fromString(companyId.toString()))
        .title(createJobDTO.title())
        .location(createJobDTO.location())
        .salary(createJobDTO.salary())
        .status(createJobDTO.status())
        .benefits(createJobDTO.benefits())
        .description(createJobDTO.description())
        .requirements(createJobDTO.requirements())
        .level(createJobDTO.level())
        .build();

      var result = this.createJobsUseCase.execute(jobs);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }
}
