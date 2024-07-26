package com.job.management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "DTO para criação de vagas")
@Builder
public record CreateJobDTO(
  @Schema(description = "Título da vaga", example = "Desenvolvedor Java")
  String title,

  @Schema(description = "Descrição da vaga", example = "Responsável pelo desenvolvimento de aplicações Java")
  String description,

  @Schema(description = "Benefícios oferecidos", example = "Vale refeição, plano de saúde")
  String benefits,

  @Schema(description = "Nível da vaga", example = "Júnior, Pleno, Sênior")
  String level,

  @Schema(description = "Requisitos para a vaga", example = "Experiência com Spring Boot, Microservices")
  String requirements,

  @Schema(description = "Salário oferecido", example = "5000-7000")
  String salary,

  @Schema(description = "Localização da vaga", example = "São Paulo, SP")
  String location,

  @Schema(description = "Status da vaga", example = "Aberta, Fechada")
  String status
) {
}
