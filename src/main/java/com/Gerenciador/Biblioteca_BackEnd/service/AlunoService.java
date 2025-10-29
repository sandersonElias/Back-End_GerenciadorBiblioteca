package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.AlunoDto;
import com.Gerenciador.Biblioteca_BackEnd.dto.AlunoMaxDto;
import com.Gerenciador.Biblioteca_BackEnd.entity.Aluno;
import com.Gerenciador.Biblioteca_BackEnd.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;

    //Novo Aluno:
    public AlunoDto insertAluno (AlunoDto alunoDto){
        Aluno entity = objectMapper.convertValue(alunoDto, Aluno.class);
        alunoRepository.save(entity);
        return objectMapper.convertValue(entity, AlunoDto.class);
    }

    //Buscar por Nome:
    public List<AlunoMaxDto> buscarNome (String nome){
        return alunoRepository.buscarNome(nome)
                .stream().map(aluno -> objectMapper.convertValue(aluno, AlunoMaxDto.class)).toList();
    }
}
