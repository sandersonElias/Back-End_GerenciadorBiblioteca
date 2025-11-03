package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    /**
     * Conta empréstimos com status PENDENTE para um livro específico.
     * Retorna Integer (nulo pode ocorrer), por isso o service deve tratar null.
     */
    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.livro.id = :livroId AND e.status = 'PENDENTE'")
    Integer contarEmprestimosPendentes(@Param("livroId") Long livroId);

    /**
     * Busca empréstimos pelo nome do aluno (case insensitive, parcial).
     */
    @Query("SELECT e FROM Emprestimo e WHERE LOWER(e.aluno.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Emprestimo> buscarAluno(@Param("nome") String nome);

    /**
     * Busca empréstimos pelo título do livro (case insensitive, parcial).
     */
    @Query("SELECT e FROM Emprestimo e WHERE LOWER(e.livro.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Emprestimo> buscarLivro(@Param("titulo") String titulo);

    /**
     * Busca empréstimos por status exato (ex.: 'PENDENTE', 'DEVOLVIDO', 'RENOVADO').
     */
    @Query("SELECT e FROM Emprestimo e WHERE e.status = :status")
    List<Emprestimo> buscarStatus(@Param("status") String status);

    /**
     * Busca empréstimos com dataDevolucao igual à data informada.
     */
    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucao = :data")
    List<Emprestimo> buscarDevolucaoDoDia(@Param("data") LocalDate data);
}