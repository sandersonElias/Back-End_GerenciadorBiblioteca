package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findAll();

    @Query("SELECT I FROM Livro a WHERE a.titulo like %:titulo%")
    List<Livro> buscarTitulo(@Param("titulo") String titulo);

    @Query("SELECT I FROM Livro I JOIN I.genero g WHERE LOWER(g.genero) LIKE LOWER(CONCAT('%',:nomeGenero,'%'))")
    List<Livro> buscarGenero(@Param("nomeGenero") String nomeGenero);

    @Query("SELECT I FROM Livro I JOIN I.catalogacao c WHERE LOWER(c.catalogacao) LIKE LOWER(CONCAT('%',:nomeCatalogacao,'%'))")
    List<Livro> buscarCatalogacao(@Param("nomeCatalogacao") String nomeCatalogacao);

    @Query("SELECT I FROM Livro I JOIN I.autor a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%',:nomeAutor,'%'))")
    List<Livro> buscarAutor(@Param("nomeAutor") String nomeAutor);

    @Query("SELECT l FROM Livro l ORDER BY l.contadorEmprestimos DESC")
    List<Livro> listarMaisPopulares(Pageable pageable);
}
