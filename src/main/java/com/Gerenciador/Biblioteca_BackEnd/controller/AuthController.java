package com.Gerenciador.Biblioteca_BackEnd.controller;

import com.Gerenciador.Biblioteca_BackEnd.dto.AuthRequest;
import com.Gerenciador.Biblioteca_BackEnd.dto.AuthResponse;
import com.Gerenciador.Biblioteca_BackEnd.service.AuthService;
import com.Gerenciador.Biblioteca_BackEnd.security.UserPrincipal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
  private final long refreshExpiresMs;
  private final boolean cookieSecure;

  public AuthController(
      AuthService authService,
      @Value("${security.jwt.refresh-exp-ms}") long refreshExpiresMs,
      @Value("${app.cookie.secure:true}") boolean cookieSecure) {
    this.authService = authService;
    this.refreshExpiresMs = refreshExpiresMs;
    this.cookieSecure = cookieSecure;
  }

  /**
   * POST /auth/login
   * Recebe email e senha, autentica e retorna accessToken no body.
   * Também seta cookie HttpOnly com refresh token.
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req,
      HttpServletRequest request,
      HttpServletResponse response) {
    String userAgent = request.getHeader("User-Agent");
    String ip = request.getRemoteAddr();

    // AuthService.login deve retornar AuthResponse contendo accessToken e
    // refreshToken
    AuthResponse auth = authService.login(req.email(), req.password(), userAgent, ip);

    // se o serviço não retornar refreshToken, adapte aqui para recuperá-lo
    String refreshToken = auth.refreshToken();
    if (refreshToken != null && !refreshToken.isBlank()) {
      Cookie cookie = new Cookie("refresh", refreshToken);
      cookie.setHttpOnly(true);
      cookie.setSecure(cookieSecure); // true em produção
      cookie.setPath("/");
      cookie.setMaxAge((int) (refreshExpiresMs / 1000));
      // SameSite não tem setter em todas as versões; adicionamos via header para
      // compatibilidade
      response.addCookie(cookie);
      // adicionar SameSite via header (concatena com possível header existente)
      String sameSite = "SameSite=Lax";
      String cookieHeader = String.format("refresh=%s; Max-Age=%d; Path=/; HttpOnly; %s%s",
          refreshToken,
          (int) (refreshExpiresMs / 1000),
          cookieSecure ? "Secure; " : "",
          sameSite);
      // setHeader sobrescreve; para múltiplos cookies você precisaria concatenar
      response.setHeader("Set-Cookie", cookieHeader);
    }

    return ResponseEntity.ok(auth);
  }

  /**
   * POST /auth/refresh
   * Lê cookie 'refresh', chama serviço para rotacionar e retorna novo
   * accessToken.
   * Também atualiza cookie refresh (rotacionado).
   */
  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(HttpServletRequest request, HttpServletResponse response) {
    String refresh = extractRefreshCookie(request);
    if (refresh == null) {
      return ResponseEntity.status(401).build();
    }

    String userAgent = request.getHeader("User-Agent");
    String ip = request.getRemoteAddr();

    // AuthService.refresh deve retornar AuthResponse com novo accessToken e novo
    // refreshToken
    AuthResponse auth = authService.refresh(refresh, userAgent, ip);

    String newRefreshToken = auth.refreshToken();
    if (newRefreshToken != null && !newRefreshToken.isBlank()) {
      Cookie cookie = new Cookie("refresh", newRefreshToken);
      cookie.setHttpOnly(true);
      cookie.setSecure(cookieSecure);
      cookie.setPath("/");
      cookie.setMaxAge((int) (refreshExpiresMs / 1000));
      response.addCookie(cookie);

      String sameSite = "SameSite=Lax";
      String cookieHeader = String.format("refresh=%s; Max-Age=%d; Path=/; HttpOnly; %s%s",
          newRefreshToken,
          (int) (refreshExpiresMs / 1000),
          cookieSecure ? "Secure; " : "",
          sameSite);
      response.setHeader("Set-Cookie", cookieHeader);
    }

    return ResponseEntity.ok(auth);
  }

  /**
   * POST /auth/logout
   * Revoga refresh token (se presente) e limpa cookie.
   */
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
    String refresh = extractRefreshCookie(request);
    if (refresh != null) {
      authService.logout(refresh);
    }

    // limpar cookie
    Cookie cookie = new Cookie("refresh", "");
    cookie.setHttpOnly(true);
    cookie.setSecure(cookieSecure);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    // também garantir header para SameSite
    String cookieHeader = "refresh=; Max-Age=0; Path=/; HttpOnly; " + (cookieSecure ? "Secure; " : "") + "SameSite=Lax";
    response.setHeader("Set-Cookie", cookieHeader);

    return ResponseEntity.noContent().build();
  }

  /**
   * GET /auth/me
   * Retorna dados do usuário autenticado (injetado pelo filtro JWT).
   */
  @GetMapping("/me")
  public ResponseEntity<?> me(@AuthenticationPrincipal UserPrincipal principal) {
    if (principal == null) {
      return ResponseEntity.status(401).build();
    }
    return ResponseEntity.ok(principal);
  }

  // utilitário para extrair cookie 'refresh'
  private String extractRefreshCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null)
      return null;
    for (Cookie c : cookies) {
      if ("refresh".equals(c.getName())) {
        return c.getValue();
      }
    }
    return null;
  }
}
