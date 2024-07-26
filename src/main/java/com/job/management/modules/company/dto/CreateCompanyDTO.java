package com.job.management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Schema(description = "DTO para criação de empresas")
public record CreateCompanyDTO(

  @Schema(description = "Email da empresa", example = "empresa@exemplo.com")
  @Email(message = "O campo [email] deve conter um e-mail válido")
  String email,

  @Schema(description = "Senha da empresa", example = "senhaSegura123")
  @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres")
  String password,

  @Schema(description = "Nome da empresa", example = "Empresa Exemplo")
  @NotBlank(message = "Digite o nome da empresa")
  String name,

  @Schema(description = "Endereço da empresa", example = "Rua Exemplo, 123")
  String address,

  @Schema(description = "CNPJ da empresa", example = "12.345.678/0001-99")
  String cnpj,

  @Schema(description = "Telefone da empresa", example = "(11) 1234-5678")
  String phone,

  @Schema(description = "Website da empresa", example = "www.empresaexemplo.com")
  String website,

  @Schema(description = "Descrição da empresa", example = "Empresa de tecnologia focada em desenvolvimento de software")
  String description
) {
}
