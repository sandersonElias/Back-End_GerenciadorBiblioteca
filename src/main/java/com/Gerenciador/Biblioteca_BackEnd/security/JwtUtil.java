package com.Gerenciador.Biblioteca_BackEnd.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final Key accessKey;
  private final Key refreshKey;
  private final long accessTokenMillis;
  private final long refreshTokenMillis;

  public JwtUtil(
      @Value("${security.jwt.access-secret}") String accessSecret,
      @Value("${security.jwt.refresh-secret}") String refreshSecret,
      @Value("${security.jwt.access-exp-ms}") long accessExpMs,
      @Value("${security.jwt.refresh-exp-ms}") long refreshExpMs) {
    this.accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes());
    this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
    this.accessTokenMillis = accessExpMs;
    this.refreshTokenMillis = refreshExpMs;
  }

  public String generateAccessToken(Long userId) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + accessTokenMillis);
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(accessKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefreshToken(Long userId) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + refreshTokenMillis);
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(refreshKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public Jws<Claims> parseAccessToken(String token) {
    return Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token);
  }

  public Jws<Claims> parseRefreshToken(String token) {
    return Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(token);
  }
}
