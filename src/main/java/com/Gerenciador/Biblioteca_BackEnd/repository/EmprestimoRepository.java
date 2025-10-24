package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findAll();

    @Query(value = "SELECT e.id, e.data_emprestimo, e.data_prevista, a.nome AS nome, l.titulo AS titulo \n" +
            "FROM tb_emprestimo e\n" +
            "JOIN tb_aluno a ON e.aluno_id = a.id\n" +
            "JOIN tb_livro l ON e.livro_id = l.id\n" +
            "WHERE a.nome ILIKE '%' || nome || '%'",
            nativeQuery = true
    )
    List<Emprestimo> buscarAluno(@Param("nome") String nome);
}
