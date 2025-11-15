package com.Gerenciador.Biblioteca_BackEnd.controller;

import com.Gerenciador.Biblioteca_BackEnd.dto.EmprestimoDto;
import com.Gerenciador.Biblioteca_BackEnd.dto.EmprestimoMinDto;
import com.Gerenciador.Biblioteca_BackEnd.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/emprestimo")
@CrossOrigin(origins = "https://biblioteca-monsa.onrender.com")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    // Novo empréstimo
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmprestimoDto> novoEmprestimo(@RequestBody EmprestimoDto emprestimo) {
        EmprestimoDto dto = emprestimoService.insertEmprestimo(emprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Listar todos
    @GetMapping("/todos")
    public ResponseEntity<List<EmprestimoDto>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.todosEmprestimos());
    }

    // Buscar por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<EmprestimoDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.buscarId(id));
    }

    // Buscar por nome do aluno
    @GetMapping("/aluno/{nome}")
    public ResponseEntity<List<EmprestimoDto>> buscarPorAluno(@PathVariable String nome) {
        return ResponseEntity.ok(emprestimoService.buscarPorAluno(nome));
    }

    // Buscar por título do livro
    @GetMapping("/livro/{titulo}")
    public ResponseEntity<List<EmprestimoDto>> buscarPorLivro(@PathVariable String titulo) {
        return ResponseEntity.ok(emprestimoService.buscarPorLivro(titulo));
    }

    // Buscar por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmprestimoDto>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(emprestimoService.buscarPorStatus(status));
    }

    // Buscar devoluções do dia
    @GetMapping("/devolucao/{data}")
    public ResponseEntity<List<EmprestimoDto>> buscarDevolucaoDoDia(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(emprestimoService.buscarDevolucaoDoDia(data));
    }

    // Renovar empréstimo
    @PutMapping("/renovar/{id}")
    public ResponseEntity<EmprestimoDto> renovar(@PathVariable Long id) {
        EmprestimoDto dto = emprestimoService.renovarEmprestimo(id);
        return ResponseEntity.ok(dto);
    }

    // Devolver empréstimo
    @PutMapping("/devolver/{id}")
    public ResponseEntity<Void> devolver(@PathVariable Long id) {
        emprestimoService.devolverEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}
