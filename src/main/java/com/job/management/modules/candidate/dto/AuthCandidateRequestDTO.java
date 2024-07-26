package com.job.management.modules.candidate.dto;

import lombok.Builder;

@Builder
public record AuthCandidateRequestDTO(String email, String password) {
}
