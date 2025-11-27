package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.RefreshToken;
import com.Gerenciador.Biblioteca_BackEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  @Query("SELECT r FROM RefreshToken r WHERE r.token = :token")
  Optional<RefreshToken> findByToken(@Param("token") String token);

  @Query("SELECT r FROM RefreshToken r WHERE r.user = :user AND r.revokedAt IS NULL")
  List<RefreshToken> findByUserAndRevokedAtIsNull(@Param("user") User user);

  @Modifying
  @Transactional
  @Query("DELETE FROM RefreshToken r WHERE r.user = :user")
  void deleteByUser(@Param("user") User user);
}
