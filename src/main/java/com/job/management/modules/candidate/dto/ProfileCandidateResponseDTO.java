package com.job.management.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Schema(description = "DTO de resposta para o perfil do candidato")
@Builder
public record ProfileCandidateResponseDTO(

  @Schema(description = "ID do candidato", example = "123e4567-e89b-12d3-a456-426614174000")
  UUID id,

  @Schema(description = "Descrição do candidato", example = "Desenvolvedor Java com 5 anos de experiência")
  String description,

  @Schema(description = "Nome completo do candidato", example = "João da Silva")
  String fullName,

  @Schema(description = "Email do candidato", example = "joao.silva@example.com")
  String email,

  @Schema(description = "Localização do candidato", example = "São Paulo, SP")
  String location,

  @Schema(description = "Currículo do candidato em formato PDF", example = "url/para/curriculo.pdf")
  String curriculum
) {
}
