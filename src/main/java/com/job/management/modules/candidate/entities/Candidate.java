package com.job.management.modules.candidate.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Candidate {

    private UUID id;
    private String fullName;
    private String email;
    private String password;
    private String description;
    private String location;
    private String curriculum;

    private LocalDateTime createdAt;
}
