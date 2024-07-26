package com.job.management.modules.company.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.job.management.exceptions.EmailNotFoundException;
import com.job.management.modules.company.dto.AuthCompanyDTO;
import com.job.management.modules.company.dto.AuthCompanyResponseDTO;
import com.job.management.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    var company = this.companyRepository.findByEmail(authCompanyDTO.email()).orElseThrow(EmailNotFoundException::new);

    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.password(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create()
      .withIssuer("job_management")
      .withSubject(company.getId().toString())
      .withExpiresAt(expiresIn)
      .withClaim("roles", List.of("COMPANY"))
      .sign(algorithm);


    return new AuthCompanyResponseDTO(token, expiresIn.toEpochMilli());
  }
}
