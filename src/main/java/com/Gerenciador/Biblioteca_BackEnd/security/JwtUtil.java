package com.Gerenciador.Biblioteca_BackEnd.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
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
    this.accessTokenMillis = accessExpMs;
    this.refreshTokenMillis = refreshExpMs;

    this.accessKey = buildKey(accessSecret, "access");
    this.refreshKey = buildKey(refreshSecret, "refresh");
  }

  private Key buildKey(String secret, String name) {
    if (secret == null || secret.isBlank() || "change_me_in_env".equals(secret)) {
      throw new IllegalStateException(
          "JWT " + name + " secret não definido ou inválido. Defina a variável de ambiente correspondente.");
    }

    byte[] keyBytes = tryDecodeBase64(secret);
    if (keyBytes == null) {
      // usar bytes da string UTF-8 como fallback (não recomendado para produção)
      keyBytes = secret.getBytes();
    }

    // valida comprimento mínimo para HS256 (32 bytes)
    if (keyBytes.length < 32) {
      throw new IllegalStateException(
          "JWT " + name + " secret muito curto. Gere um secret com pelo menos 32 bytes (256 bits).");
    }

    try {
      return Keys.hmacShaKeyFor(keyBytes);
    } catch (Exception ex) {
      throw new IllegalStateException("Falha ao construir chave JWT " + name + ": " + ex.getMessage(), ex);
    }
  }

  private byte[] tryDecodeBase64(String s) {
    try {
      return Base64.getDecoder().decode(s);
    } catch (IllegalArgumentException ex) {
      return null;
    }
  }

  // key parts only
  public String generateAccessToken(Long userId, List<String> roles) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + accessTokenMillis);
    return Jwts.builder()
        .setSubject(String.valueOf(userId))
        .claim("roles", roles)
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

  public long getAccessTokenMillis() {
    return accessTokenMillis;
  }

  public long getRefreshTokenMillis() {
    return refreshTokenMillis;
  }
}
