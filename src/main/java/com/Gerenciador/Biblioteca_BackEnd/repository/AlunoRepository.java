package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Query("SELECT n FROM Aluno n WHERE n.nome like %:nome%")
    List<Aluno> buscarNome(@Param("nome") String nome);
}
