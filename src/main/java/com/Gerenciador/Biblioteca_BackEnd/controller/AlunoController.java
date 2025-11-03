package com.Gerenciador.Biblioteca_BackEnd.controller;

import com.Gerenciador.Biblioteca_BackEnd.dto.AlunoDto;
import com.Gerenciador.Biblioteca_BackEnd.dto.AlunoMinDto;
import com.Gerenciador.Biblioteca_BackEnd.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://biblioteca-monsa.onrender.com/")
@RequestMapping("/aluno")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    // Criar novo aluno
    @PostMapping
    public ResponseEntity<AlunoDto> criarAluno(@RequestBody AlunoDto alunoDto) {
        AlunoDto criado = alunoService.insertAluno(alunoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // Listar todos (detalhado)
    @GetMapping("/todos")
    public ResponseEntity<List<AlunoDto>> listarTodos() {
        List<AlunoDto> lista = alunoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> buscarPorId(@PathVariable Long id) {
        AlunoDto dto = alunoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // Buscar por nome (ex.: /aluno/buscar/joao)
    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<AlunoDto>> buscarPorNome(@PathVariable String nome) {
        List<AlunoDto> lista = alunoService.buscarPorNome(nome);
        return ResponseEntity.ok(lista);
    }

    // Listar mínimos (id + nome)
    @GetMapping("/minimos")
    public ResponseEntity<List<AlunoMinDto>> listarMinimos() {
        List<AlunoMinDto> lista = alunoService.listarMinimos();
        return ResponseEntity.ok(lista);
    }
}
