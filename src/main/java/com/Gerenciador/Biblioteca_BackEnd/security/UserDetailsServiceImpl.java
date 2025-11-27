package com.Gerenciador.Biblioteca_BackEnd.security;

import com.Gerenciador.Biblioteca_BackEnd.entity.User;
import com.Gerenciador.Biblioteca_BackEnd.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetails loadUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow();
    // converta para UserDetails (implemente UserPrincipal)
    return UserPrincipal.create(user);
  }
}
