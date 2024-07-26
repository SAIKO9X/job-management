package com.job.management.modules.company.usecases;

import com.job.management.exceptions.UserNotFoundException;
import com.job.management.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.job.management.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(UserNotFoundException::new);

    return ProfileCandidateResponseDTO.builder()
      .id(candidate.getId())
      .description(candidate.getDescription())
      .email(candidate.getEmail())
      .fullName(candidate.getFullName())
      .location(candidate.getLocation())
      .curriculum(candidate.getCurriculum())
      .build();
  }
}