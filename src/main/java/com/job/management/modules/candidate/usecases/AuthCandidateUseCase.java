package com.job.management.modules.candidate.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.job.management.exceptions.EmailNotFoundException;
import com.job.management.modules.candidate.dto.AuthCandidateRequestDTO;
import com.job.management.modules.candidate.dto.AuthCandidateResponseDTO;
import com.job.management.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateUseCase {

  @Value("${security.token.secret.candidate}")
  private String secretKey;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
    throws AuthenticationException {
    var candidate = this.candidateRepository.findByEmail(authCandidateRequestDTO.email()).orElseThrow(EmailNotFoundException::new);

    var passwordMatches = this.passwordEncoder
      .matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
    var token = JWT.create()
      .withIssuer("job_management")
      .withSubject(candidate.getId().toString())
      .withClaim("roles", List.of("CANDIDATE"))
      .withExpiresAt(expiresIn)
      .sign(algorithm);

    return new AuthCandidateResponseDTO(token, expiresIn.toEpochMilli());

  }
}
