package com.job.management.modules.candidate.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Candidate {

    private UUID id;

    @NotBlank
    private String fullName;

    @Email(message = "O campo [email] deve conter um e-mail válido")
    private String email;

    @Length(min = 8, max = 100, message = "A senha deve conter entre (8) e (100) caracteres")
    private String password;

    private String description;
    private String location;
    private String curriculum;

    private LocalDateTime createdAt;
}
