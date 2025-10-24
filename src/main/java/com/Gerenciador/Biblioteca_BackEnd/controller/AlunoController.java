package com.Gerenciador.Biblioteca_BackEnd.controller;

import com.Gerenciador.Biblioteca_BackEnd.dto.AlunoDto;
import com.Gerenciador.Biblioteca_BackEnd.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://biblioteca-monsa.onrender.com/")
@RequestMapping("/aluno")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    //Novo Aluno:
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlunoDto> novoAluno(@RequestBody AlunoDto aluno){
        AlunoDto alunoDto = alunoService.insertAluno(aluno);
        return new ResponseEntity<>(alunoDto, HttpStatus.CREATED);
    }

    //Buscar por Nome:
    public ResponseEntity<List<AlunoDto>> buscarNome(@PathVariable String nome){
        List<AlunoDto> alunoDto = alunoService.buscarNome(nome);
        return new ResponseEntity<List<AlunoDto>>(alunoDto, HttpStatus.OK);
    }
}