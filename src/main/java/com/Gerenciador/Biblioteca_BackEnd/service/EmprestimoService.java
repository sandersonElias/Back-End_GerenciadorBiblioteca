package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.EmprestimoDto;
import com.Gerenciador.Biblioteca_BackEnd.dto.EmprestimoMinDto;
import com.Gerenciador.Biblioteca_BackEnd.dto.LivroDto;
import com.Gerenciador.Biblioteca_BackEnd.entity.Aluno;
import com.Gerenciador.Biblioteca_BackEnd.entity.Emprestimo;
import com.Gerenciador.Biblioteca_BackEnd.entity.Livro;
import com.Gerenciador.Biblioteca_BackEnd.repository.AlunoRepository;
import com.Gerenciador.Biblioteca_BackEnd.repository.EmprestimoRepository;
import com.Gerenciador.Biblioteca_BackEnd.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final AlunoRepository alunoRepository;
    private final LivroRepository livroRepository;
    private final ObjectMapper objectMapper;

    // Criar novo empréstimo
    public EmprestimoMinDto insertEmprestimo(EmprestimoMinDto dto) {
        Livro livro = livroRepository.findById(dto.getLivro())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        Aluno aluno = alunoRepository.findById(dto.getAluno())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        int totalExemplares = livro.getTotalExemplares() != null ? livro.getTotalExemplares() : 0;

        Integer emprestadosInteger = emprestimoRepository.contarEmprestimosPendentes(livro.getId());
        int emprestadosCount = emprestadosInteger != null ? emprestadosInteger : 0;

        if (emprestadosCount >= totalExemplares) {
            throw new IllegalStateException("Todos os exemplares deste livro estão emprestados.");
        }

        Emprestimo emp = new Emprestimo();
        emp.setAluno(aluno);
        emp.setLivro(livro);

        LocalDate hoje = dto.getDataEmprestimo() != null ? dto.getDataEmprestimo() : LocalDate.now();
        emp.setDataEmprestimo(hoje);

        LocalDate devolucao = dto.getDataDevolucao() != null ? dto.getDataDevolucao() : hoje.plusDays(7);
        emp.setDataDevolucao(devolucao);

        emp.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDENTE");
        emp.setRenovacoes(dto.getRenovacoes() != null ? dto.getRenovacoes() : 0);

        // atualiza contador de popularidade com proteção contra null
        int contador = livro.getContadorEmprestimos() != null ? livro.getContadorEmprestimos() : 0;
        livro.setContadorEmprestimos(contador + 1);
        livroRepository.save(livro);

        Emprestimo salvo = emprestimoRepository.save(emp);
        return objectMapper.convertValue(salvo, EmprestimoMinDto.class);
    }

    // Devolver empréstimo
    public void devolverEmprestimo(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        emp.setStatus("DEVOLVIDO");
        emp.setDataDevolvido(LocalDate.now());
        emprestimoRepository.save(emp);
    }

    // Renovar empréstimo
    public EmprestimoDto renovarEmprestimo(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        emp.setRenovacoes(Objects.requireNonNullElse(emp.getRenovacoes(), 0) + 1);
        LocalDate novaDevolucao = Objects.requireNonNullElse(emp.getDataDevolucao(), LocalDate.now()).plusDays(7);
        emp.setDataDevolucao(novaDevolucao);
        emp.setStatus("RENOVADO");

        Emprestimo atualizado = emprestimoRepository.save(emp);
        return objectMapper.convertValue(atualizado, EmprestimoDto.class);
    }

    // Listar todos
    public List<EmprestimoDto> todosEmprestimos() {
        return emprestimoRepository.findAll().stream()
                .map(e -> objectMapper.convertValue(e, EmprestimoDto.class))
                .toList();
    }

    // Buscar por ID
    public EmprestimoDto buscarId(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));
        return objectMapper.convertValue(emp, EmprestimoDto.class);
    }

    // Buscar por nome do aluno
    public List<EmprestimoDto> buscarPorAluno(String nome) {
        return emprestimoRepository.buscarAluno(nome).stream()
                .map(e -> objectMapper.convertValue(e, EmprestimoDto.class))
                .toList();
    }

    // Buscar por título do livro
    public List<EmprestimoDto> buscarPorLivro(String titulo) {
        return emprestimoRepository.buscarLivro(titulo).stream()
                .map(e -> objectMapper.convertValue(e, EmprestimoDto.class))
                .toList();
    }

    // Buscar por status
    public List<EmprestimoDto> buscarPorStatus(String status) {
        return emprestimoRepository.buscarStatus(status).stream()
                .map(e -> objectMapper.convertValue(e, EmprestimoDto.class))
                .toList();
    }

    // Buscar devoluções do dia
    public List<EmprestimoDto> buscarDevolucaoDoDia(LocalDate hoje) {
        return emprestimoRepository.buscarDevolucaoDoDia(hoje).stream()
                .map(e -> objectMapper.convertValue(e, EmprestimoDto.class))
                .toList();
    }
}
