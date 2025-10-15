package com.Gerenciador.Biblioteca_BackEnd.repository;

import com.Gerenciador.Biblioteca_BackEnd.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findAll();

    @Query("SELECT a FROM Livro a WHERE a.titulo like %:titulo%")
    List<Livro> buscarTitulo(@Param("titulo") String titulo);

    @Query("SELECT I FROM Livro I JOIN I.genero g WHERE LOWER(g.genero) LIKE LOWER(CONCAT('%',:nomeGenero,'%'))")
    List<Livro> buscarGenero(@Param("nomeGenero") String nomeGenero);

    @Query("SELECT I FROM Livro I JOIN I.catalogacao c WHERE LOWER(c.catalogacao) LIKE LOWER(CONCAT('%',:nomeCatalogacao,'%'))")
    List<Livro> buscarCatalogacao(@Param("nomeCatalogacao") String nomeCatalogacao);

    @Query("SELECT I FROM Livro I JOIN I.autor a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%',:nomeAutor,'%'))")
    List<Livro> buscarAutor(@Param("nomeAutor") String nomeAutor);
}
