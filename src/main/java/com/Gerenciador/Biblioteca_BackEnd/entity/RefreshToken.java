package com.Gerenciador.Biblioteca_BackEnd.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "refresh_tokens")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(name = "user_agent")
  private String userAgent;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "created_at")
  private OffsetDateTime createdAt = OffsetDateTime.now();

  @Column(name = "expires_at")
  private OffsetDateTime expiresAt;

  @Column(name = "revoked_at")
  private OffsetDateTime revokedAt;
}
