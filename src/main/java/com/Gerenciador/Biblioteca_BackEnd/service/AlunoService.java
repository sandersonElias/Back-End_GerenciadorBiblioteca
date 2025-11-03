package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.AlunoDto;
import com.Gerenciador.Biblioteca_BackEnd.dto.AlunoMinDto;
import com.Gerenciador.Biblioteca_BackEnd.entity.Aluno;
import com.Gerenciador.Biblioteca_BackEnd.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;

    // Criar novo aluno
    public AlunoDto insertAluno(AlunoDto alunoDto) {
        Aluno entity = objectMapper.convertValue(alunoDto, Aluno.class);
        Aluno salvo = alunoRepository.save(entity);
        return objectMapper.convertValue(salvo, AlunoDto.class);
    }

    // Listar todos (detalhado)
    public List<AlunoDto> listarTodos() {
        return alunoRepository.findAll()
                .stream()
                .map(a -> objectMapper.convertValue(a, AlunoDto.class))
                .toList();
    }

    // Buscar por ID
    public AlunoDto buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        return objectMapper.convertValue(aluno, AlunoDto.class);
    }

    // Buscar por nome (lista)
    public List<AlunoDto> buscarPorNome(String nome) {
        return alunoRepository.buscarNome(nome)
                .stream()
                .map(a -> objectMapper.convertValue(a, AlunoDto.class))
                .toList();
    }

    // Listar mínimos (id + nome) para dropdowns
    public List<AlunoMinDto> listarMinimos() {
        return alunoRepository.findAll()
                .stream()
                .map(a -> {
                    AlunoMinDto dto = new AlunoMinDto();
                    dto.setId(a.getId());
                    dto.setNome(a.getNome());
                    return dto;
                })
                .toList();
    }
}
