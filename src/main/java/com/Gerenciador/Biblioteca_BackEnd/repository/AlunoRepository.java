package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {}
