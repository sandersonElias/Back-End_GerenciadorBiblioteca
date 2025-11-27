package com.Gerenciador.Biblioteca_BackEnd.security;

import com.Gerenciador.Biblioteca_BackEnd.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

  private final Long id;
  private final String email;
  private final String password;
  private final List<GrantedAuthority> authorities;
  private final boolean active;

  public UserPrincipal(Long id, String email, String password, List<String> roles, boolean active) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = roles == null ? List.of()
        : roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList());
    this.active = active;
  }

  public static UserPrincipal create(User user) {
    return new UserPrincipal(
        user.getId(),
        user.getEmail(),
        user.getPasswordHash(),
        user.getRoles(),
        Boolean.TRUE.equals(user.getIsActive()));
  }

  public Long getId() {
    return id;
  }

  // UserDetails methods

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  // getUsername must be implemented (erro que você viu)
  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  // account non expired / locked / credentials non expired — ajuste conforme sua
  // lógica
  @Override
  public boolean isAccountNonExpired() {
    return active;
  }

  @Override
  public boolean isAccountNonLocked() {
    return active;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return active;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }
}