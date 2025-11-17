package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.AlunoDto;
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
        return toAlunoDto(salvo);
    }

    // Listar todos
    public List<AlunoDto> listarTodos() {
        return alunoRepository.findAll()
                .stream()
                .map(this::toAlunoDto)
                .toList();
    }

    // Buscar por ID
    public AlunoDto buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        return toAlunoDto(aluno);
    }

    // Buscar por nome (lista)
    public List<AlunoDto> buscarPorNome(String nome) {
        return alunoRepository.buscarNome(nome)
                .stream()
                .map(this::toAlunoDto)
                .toList();
    }

    private AlunoDto toAlunoDto(Aluno aluno) {
        AlunoDto dto = new AlunoDto();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setAno(aluno.getAno());
        dto.setTurma(aluno.getTurma());

        return dto;
    }
}
