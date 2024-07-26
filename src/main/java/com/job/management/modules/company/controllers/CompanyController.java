package com.job.management.modules.company.controllers;

import com.job.management.modules.company.dto.CreateCompanyDTO;
import com.job.management.modules.company.entities.Company;
import com.job.management.modules.company.usecases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @PostMapping("/")
  @Operation(
    summary = "Criar uma nova empresa",
    description = "Esta operação cria uma nova empresa no sistema"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso", content = @Content(schema = @Schema(implementation = Company.class))),
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content)
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CreateCompanyDTO createCompanyDTO) {
    try {
      var result = this.createCompanyUseCase.execute(createCompanyDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
