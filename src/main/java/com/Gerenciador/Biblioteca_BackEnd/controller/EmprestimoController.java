package com.Gerenciador.Biblioteca_BackEnd.controller;

import com.Gerenciador.Biblioteca_BackEnd.dto.EmprestimoDto;
import com.Gerenciador.Biblioteca_BackEnd.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/emprestimo")
@CrossOrigin(origins = "https://biblioteca-monsa.onrender.com/")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    //Novo Empretimo:
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmprestimoDto> newEmprestimo(@RequestBody EmprestimoDto emprestimo){
        EmprestimoDto emprestimoDto = emprestimoService.insertEmprestimo(emprestimo);
        return new ResponseEntity<>(emprestimoDto, HttpStatus.CREATED);
    }

    //Buscar todos os Emprestimos:
    @GetMapping("/todos")
    public ResponseEntity<List<EmprestimoDto>> todosEmprestimos(){
        List<EmprestimoDto> emprestimoDto = emprestimoService.todosEmprestimos();
        return new ResponseEntity<List<EmprestimoDto>>(emprestimoDto, HttpStatus.OK);
    }

    //Buscar por Id:
    @GetMapping("/id/{id}")
    public ResponseEntity<EmprestimoDto> buscarEmprestimo(@PathVariable Long id){
        EmprestimoDto emprestimoDto = emprestimoService.buscarId(id);
        return new ResponseEntity<>(emprestimoDto, HttpStatus.OK);
    }

    //Buscar por Nome Aluno:
    @GetMapping("/aluno/{aluno}")
    public ResponseEntity<EmprestimoDto> buscarAluno(@PathVariable String aluno){
        EmprestimoDto emprestimoDto = emprestimoService.buscarAluno(aluno);
        return new ResponseEntity<>(emprestimoDto, HttpStatus.OK);
    }

    //Renovar Emprestimo:
    @PutMapping("/renovar/{id}")
    public ResponseEntity<EmprestimoDto> renovarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoDto emprestimoDto){
        EmprestimoDto emprestimoDtos = emprestimoService.renovarEmprestimo(id, emprestimoDto);
        return ResponseEntity.ok(emprestimoDtos);
    }

    //Deletar Emprestimo:
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long id){
        emprestimoService.deletarEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}
