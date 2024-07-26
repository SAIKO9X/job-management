package com.job.management.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TestUtils {
  public static String objectToJSON(Object obj) {
    try {
      final ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateToken(UUID idCompany, String secret) {
    Algorithm algorithm = Algorithm.HMAC256(secret);

    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    return JWT.create().withIssuer("job_management")
      .withSubject(idCompany.toString())
      .withExpiresAt(expiresIn)
      .withClaim("roles", List.of("COMPANY"))
      .sign(algorithm);
  }
}
