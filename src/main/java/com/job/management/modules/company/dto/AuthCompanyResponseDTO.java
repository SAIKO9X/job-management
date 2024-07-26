package com.job.management.modules.company.dto;

import lombok.Builder;

@Builder
public record AuthCompanyResponseDTO(String access_token, Long expires_in) {
}
