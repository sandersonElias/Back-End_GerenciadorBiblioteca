package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.*;
import com.Gerenciador.Biblioteca_BackEnd.entity.Livro;
import com.Gerenciador.Biblioteca_BackEnd.repository.EmprestimoRepository;
import com.Gerenciador.Biblioteca_BackEnd.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final ObjectMapper objectMapper;

    // Criar novo livro
    public LivroDto insertLivro(LivroDto dto) {
        Livro entity = objectMapper.convertValue(dto, Livro.class);
        if (entity.getContadorEmprestimos() == null) entity.setContadorEmprestimos(0);
        if (entity.getTotalExemplares() == null) entity.setTotalExemplares(0);

        Livro salvo = livroRepository.save(entity);
        return toLivroDto(salvo);
    }

    // Buscar todos os livros (completo)
    public List<LivroDto> listarTodos() {
        return livroRepository.findAll()
                .stream()
                .map(this::toLivroDto)
                .toList();
    }

    // Buscar por ID
    public LivroDto buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        return toLivroDto(livro);
    }

    // Buscar por título
    public List<LivroDto> buscarPorTitulo(String titulo) {
        return livroRepository.buscarTitulo(titulo)
                .stream()
                .map(this::toLivroDto)
                .toList();
    }

    // Buscar por autor
    public List<LivroDto> buscarPorAutor(String autor) {
        return livroRepository.buscarAutor(autor)
                .stream()
                .map(this::toLivroDto)
                .toList();
    }

    // Buscar por gênero
    public List<LivroDto> buscarPorGenero(String genero) {
        return livroRepository.buscarGenero(genero)
                .stream()
                .map(this::toLivroDto)
                .toList();
    }

    // Buscar por catalogação
    public List<LivroDto> buscarPorCatalogacao(String catalogacao) {
        return livroRepository.buscarCatalogacao(catalogacao)
                .stream()
                .map(this::toLivroDto)
                .toList();
    }

    // Listar livros disponíveis
    public List<LivroDto> listarDisponiveis() {
        return livroRepository.findAll()
                .stream()
                .filter(this::isDisponivel)
                .map(this::toLivroDto)
                .toList();
    }

    // Listar livros mais populares
    public List<LivroDto> listarMaisPopulares(int limite) {
        return livroRepository.listarMaisPopulares(PageRequest.of(0, Math.max(1, limite)))
                .stream()
                .map(this::toLivroDto)
                .toList();
    }

    // Listar apenas ID e título (LivroMinDto)
    public List<LivroMinDto> listarMinimos() {
        return livroRepository.findAll()
                .stream()
                .map(l -> {
                    LivroMinDto dto = new LivroMinDto();
                    dto.setId(l.getId());
                    dto.setTitulo(l.getTitulo());
                    return dto;
                })
                .toList();
    }

    // ---------- Helpers ----------

    private boolean isDisponivel(Livro livro) {
        int emprestados = emprestimoRepository.contarEmprestimosPendentes(livro.getId());
        return emprestados < (livro.getTotalExemplares() != null ? livro.getTotalExemplares() : 0);
    }

    private LivroDto toLivroDto(Livro livro) {
        LivroDto dto = new LivroDto();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setEditora(livro.getEditora());
        dto.setCdd(livro.getCdd());
        dto.setLocalizacao(livro.getLocalizacao());
        dto.setDescricao(livro.getDescricao());
        dto.setUrlImg(livro.getUrlImg());
        dto.setTotalExemplares(livro.getTotalExemplares());
        dto.setContadorEmprestimos(livro.getContadorEmprestimos());

        int emprestados = emprestimoRepository.contarEmprestimosPendentes(livro.getId());
        dto.setEmprestados(emprestados);
        dto.setDisponivel(emprestados < (livro.getTotalExemplares() != null ? livro.getTotalExemplares() : 0));

        if (livro.getAutor() != null) {
            AutorDto autor = new AutorDto();
            autor.setId(livro.getAutor().getId());
            autor.setNome(livro.getAutor().getNome());
            dto.setAutor(autor);
        }

        if (livro.getGenero() != null) {
            GeneroDto genero = new GeneroDto();
            genero.setId(livro.getGenero().getId());
            genero.setNome(livro.getGenero().getGenero());
            dto.setGenero(genero);
        }

        if (livro.getCatalogacao() != null) {
            CatalogacaoDto catalogacao = new CatalogacaoDto();
            catalogacao.setId(livro.getCatalogacao().getId());
            catalogacao.setNome(livro.getCatalogacao().getCatalogacao());
            dto.setCatalogacao(catalogacao);
        }

        return dto;
    }
}
