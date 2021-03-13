package com.greenfoxacademy.mybookshelf.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtServiceImpl implements JwtService {
  private final String secret = System.getenv("SECRET_KEY");
  @Override
  public String createToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  @Override
  public Optional<String> extractUsernameFromToken(String token) {
    return Optional.ofNullable(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
  }
}
