package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.AuthResponse;
import com.Gerenciador.Biblioteca_BackEnd.entity.RefreshToken;
import com.Gerenciador.Biblioteca_BackEnd.entity.User;
import com.Gerenciador.Biblioteca_BackEnd.repository.RefreshTokenRepository;
import com.Gerenciador.Biblioteca_BackEnd.repository.UserRepository;
import com.Gerenciador.Biblioteca_BackEnd.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserRepository userRepository,
      RefreshTokenRepository refreshTokenRepository,
      JwtUtil jwtUtil,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.refreshTokenRepository = refreshTokenRepository;
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Autentica o usuário e retorna access + refresh tokens.
   */
  @Transactional
  public AuthResponse login(String email, String password, String userAgent, String ip) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

    if (!Boolean.TRUE.equals(user.getIsActive())) {
      throw new RuntimeException("Conta inativa");
    }

    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
      throw new RuntimeException("Credenciais inválidas");
    }

    // gerar tokens
    String accessToken = jwtUtil.generateAccessToken(user.getId());
    String refreshToken = jwtUtil.generateRefreshToken(user.getId());

    // persistir refresh token
    RefreshToken rt = new RefreshToken();
    rt.setUser(user);
    rt.setToken(refreshToken);
    rt.setUserAgent(userAgent);
    rt.setIpAddress(ip);
    // jwtUtil.getRefreshTokenMillis() retorna milissegundos
    rt.setExpiresAt(OffsetDateTime.now().plusSeconds(jwtUtil.getRefreshTokenMillis() / 1000));
    refreshTokenRepository.save(rt);

    // atualizar last login
    user.setLastLogin(OffsetDateTime.now());
    userRepository.save(user);

    // retornar ambos tokens (access + refresh)
    return new AuthResponse(accessToken, refreshToken, "Bearer", jwtUtil.getAccessTokenMillis() / 1000);
  }

  /**
   * Recebe um refresh token válido, revoga-o, cria um novo refresh token e
   * retorna novo access + refresh.
   */
  @Transactional
  public AuthResponse refresh(String refreshToken, String userAgent, String ip) {
    var opt = refreshTokenRepository.findByToken(refreshToken);
    if (opt.isEmpty()) {
      throw new RuntimeException("Refresh token inválido");
    }

    RefreshToken rt = opt.get();
    if (rt.getRevokedAt() != null || rt.getExpiresAt() == null || rt.getExpiresAt().isBefore(OffsetDateTime.now())) {
      throw new RuntimeException("Refresh token inválido ou expirado");
    }

    Long userId = rt.getUser().getId();

    // revogar token antigo
    rt.setRevokedAt(OffsetDateTime.now());
    refreshTokenRepository.save(rt);

    // criar novo refresh token e persistir
    String newRefresh = jwtUtil.generateRefreshToken(userId);
    RefreshToken newRt = new RefreshToken();
    newRt.setUser(rt.getUser());
    newRt.setToken(newRefresh);
    newRt.setUserAgent(userAgent);
    newRt.setIpAddress(ip);
    newRt.setExpiresAt(OffsetDateTime.now().plusSeconds(jwtUtil.getRefreshTokenMillis() / 1000));
    refreshTokenRepository.save(newRt);

    // gerar novo access token
    String newAccess = jwtUtil.generateAccessToken(userId);

    return new AuthResponse(newAccess, newRefresh, "Bearer", jwtUtil.getAccessTokenMillis() / 1000);
  }

  /**
   * Revoga um refresh token (logout).
   */
  @Transactional
  public void logout(String refreshToken) {
    refreshTokenRepository.findByToken(refreshToken).ifPresent(rt -> {
      rt.setRevokedAt(OffsetDateTime.now());
      refreshTokenRepository.save(rt);
    });
  }
}
