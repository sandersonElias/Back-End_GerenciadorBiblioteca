package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.LivroDto;
import com.Gerenciador.Biblioteca_BackEnd.entity.Livro;
import com.Gerenciador.Biblioteca_BackEnd.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final ObjectMapper objectMapper;

    //Novo Livro:
    public LivroDto insertLivro (LivroDto livroDto){
        Livro entity = objectMapper.convertValue(livroDto, Livro.class);
        livroRepository.save(entity);
        return objectMapper.convertValue(entity, LivroDto.class);
    }

    //Todos od Livros:
    public List<LivroDto> todosLivro(){
        return livroRepository.findAll()
                .stream().map(livro -> objectMapper.convertValue(livro, LivroDto.class)).toList();
    }

    //Livros por Id:
    public LivroDto buscarLivro (UUID idLivro){
        return livroRepository.findById(idLivro)
                .stream().map(livro -> objectMapper.convertValue(livro, LivroDto.class))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
    }

    //Livros por Titulo:
    public List<LivroDto> buscarTitulo (String titulo){
        return livroRepository.buscarTitulo(titulo)
                .stream().map(livro -> objectMapper.convertValue(livro, LivroDto.class)).toList();
    }

    //Livros por Genero:
    public List<LivroDto> buscarGenero (String genero){
        return livroRepository.buscarGenero(genero)
                .stream().map(livro -> objectMapper.convertValue(livro, LivroDto.class)).toList();
    }

    //Livros por Catalogacao:
    public List<LivroDto> buscarCatalogacao (String catalogacao){
        return livroRepository.buscarCatalogacao(catalogacao)
                .stream().map(livro -> objectMapper.convertValue(livro, LivroDto.class)).toList();
    }

    //Livro por Autor:
    public List<LivroDto> buscarAutor (String autor){
        return livroRepository.buscarAutor(autor)
                .stream().map(livro -> objectMapper.convertValue(livro, LivroDto.class)).toList();
    }
}
