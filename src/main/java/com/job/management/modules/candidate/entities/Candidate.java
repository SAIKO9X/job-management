package com.job.management.modules.candidate.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_candidate")
public class Candidate {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Column(name = "full_name")
  private String fullName;

  @Email(message = "O campo [email] deve conter um e-mail válido")
  private String email;

  @Length(min = 8, max = 100, message = "A senha deve conter entre (8) e (100) caracteres")
  private String password;

  private String description;
  private String location;
  private String curriculum;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
