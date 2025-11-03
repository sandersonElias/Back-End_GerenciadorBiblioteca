package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findAll();

    @Query(value = """
    SELECT e.id, e.data_emprestimo, e.data_prevista, a.nome AS nome, l.titulo AS titulo
    FROM tb_emprestimo e
    JOIN tb_aluno a ON e.aluno_id = a.id
    JOIN tb_livro l ON e.livro_id = l.id
    WHERE a.nome ILIKE %:nome%
    """, nativeQuery = true)
    List<Emprestimo> buscarAluno(@Param("nome") String nome);

    @Query(value = """
    SELECT e.id, e.data_emprestimo, e.data_prevista, a.nome AS nome, l.titulo AS titulo
    FROM tb_emprestimo e
    JOIN tb_aluno a ON e.aluno_id = a.id
    JOIN tb_livro l ON e.livro_id = l.id
    WHERE l.titulo ILIKE %:titulo%
    """, nativeQuery = true)
    List<Emprestimo> buscarLivro(@Param("titulo") String titulo);


    @Query("SELECT e FROM Emprestimo e WHERE e.status = :status")
    List<Emprestimo> buscarStatus(@Param("status") String status);

    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucao = :data")
    List<Emprestimo> buscarDataDevolucao(@Param("data")LocalDate date);

    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucao <= :hoje")
    List<Emprestimo> buscarDevolucaoDoDia(@Param ("hoje") LocalDate hoje);

    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'PENDENTE' AND e.dataDevolucao < :hoje")
    List<Emprestimo> buscarVencidos(@Param("hoje") LocalDate hoje);

    @Query("SELECT e FROM Emprestimo e WHERE e.aluno.id = :alunoId ORDER BY e.dataEmprestimo DESC")
    List<Emprestimo> buscarHistoricoPorAluno(@Param("alunoId") Long alunoId);

    @Query("SELECT e FROM Emprestimo e WHERE e.livro.id = :livroId ORDER BY e.dataEmprestimo DESC")
    List<Emprestimo> buscarHistoricoPorLivro(@Param("livroId") Long livroId);

    @Query("SELECT e FROM Emprestimo e WHERE e.dataEmprestimo BETWEEN :inicio AND :fim")
    List<Emprestimo> buscarPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.livro.id = :livroId AND e.status = 'PENDENTE'")
    int contarEmprestimosPendentes(@Param("livroId") Long livroId);
}