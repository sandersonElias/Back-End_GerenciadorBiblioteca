package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.EmprestimoDto;
import com.Gerenciador.Biblioteca_BackEnd.dto.EmprestimoMinDto;
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

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final AlunoRepository alunoRepository;
    private final LivroRepository livroRepository;
    private final ObjectMapper objectMapper;

    // Novo empréstimo
    public EmprestimoMinDto insertEmprestimo(EmprestimoMinDto dto) {
        Livro livro = livroRepository.findById(dto.getIdLivro())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        Aluno aluno = alunoRepository.findById(dto.getIdAluno())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        // Verifica disponibilidade
        int emprestados = emprestimoRepository.buscarStatus("PENDENTE").stream()
                .filter(e -> e.getLivro().getId().equals(livro.getId()))
                .toList().size();

        if (emprestados >= livro.getTotalExemplares()) {
            throw new IllegalStateException("Todos os exemplares deste livro estão emprestados.");
        }

        // Cria empréstimo
        Emprestimo emp = new Emprestimo();
        emp.setAluno(aluno);
        emp.setLivro(livro);
        emp.setDataEmprestimo(dto.getDataEmprestimo());
        emp.setDataDevolucao(dto.getDataDevolucao());
        emp.setStatus("PENDENTE");
        emp.setRenovacoes(0);

        // Atualiza contador de popularidade
        livro.setContadorEmprestimos(livro.getContadorEmprestimos() + 1);
        livroRepository.save(livro);

        Emprestimo salvo = emprestimoRepository.save(emp);
        return objectMapper.convertValue(salvo, EmprestimoMinDto.class);
    }

    // Devolver empréstimo (não deleta, apenas atualiza status)
    public void devolverEmprestimo(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        emp.setStatus("DEVOLVIDO");
        emp.setDataDevolvido(LocalDate.now());
        emprestimoRepository.save(emp);
    }

    // Renovar empréstimo
    public EmprestimoMinDto renovarEmprestimo(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        emp.setRenovacoes(emp.getRenovacoes() + 1);
        emp.setDataDevolucao(emp.getDataDevolucao().plusDays(7));
        emp.setStatus("RENOVADO");

        Emprestimo atualizado = emprestimoRepository.save(emp);
        return objectMapper.convertValue(atualizado, EmprestimoMinDto.class);
    }

    // Listar todos os empréstimos
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
