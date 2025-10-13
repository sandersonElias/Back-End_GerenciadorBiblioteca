package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {}
