package com.Gerenciador.Biblioteca_BackEnd.security;

import com.Gerenciador.Biblioteca_BackEnd.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
  private Long id;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  // factory
  public static UserPrincipal create(User user) {
    var auths = user.getRoles().stream()
        .map(r -> (GrantedAuthority) () -> "ROLE_" + r)
        .collect(Collectors.toList());
    UserPrincipal p = new UserPrincipal();
    p.id = user.getId();
    p.email = user.getEmail();
    p.password = user.getPasswordHash();
    p.authorities = auths;
    return p;
  }

  // getters e métodos do UserDetails
}
