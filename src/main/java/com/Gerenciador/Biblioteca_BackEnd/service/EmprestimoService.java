package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.*;
import com.Gerenciador.Biblioteca_BackEnd.entity.Aluno;
import com.Gerenciador.Biblioteca_BackEnd.entity.Emprestimo;
import com.Gerenciador.Biblioteca_BackEnd.entity.Livro;
import com.Gerenciador.Biblioteca_BackEnd.repository.AlunoRepository;
import com.Gerenciador.Biblioteca_BackEnd.repository.EmprestimoRepository;
import com.Gerenciador.Biblioteca_BackEnd.repository.LivroRepository;
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

    // Criar novo empréstimo
    public EmprestimoMinDto insertEmprestimo(EmprestimoMinDto dto) {
        Livro livro = livroRepository.findById(dto.getIdLivro())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        Aluno aluno = alunoRepository.findById(dto.getIdAluno())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        int totalExemplares = livro.getTotalExemplares() != null ? livro.getTotalExemplares() : 0;

        Integer emprestadosInteger = emprestimoRepository.contarEmprestimosPendentes(livro.getId());
        int emprestadosCount = emprestadosInteger != null ? emprestadosInteger : 0;
        if (emprestadosCount >= totalExemplares) {
            throw new IllegalStateException("Todos os exemplares deste livro estão emprestados.");
        }

        Emprestimo emp = new Emprestimo();
        emp.setId(dto.getId());

        emp.setAluno(aluno);
        emp.setLivro(livro);

        LocalDate hoje = dto.getDataEmprestimo() != null ? dto.getDataEmprestimo() : LocalDate.now();
        emp.setDataEmprestimo(hoje);

        LocalDate devolucao = dto.getDataDevolucao() != null ? dto.getDataDevolucao() : hoje.plusDays(7);
        emp.setDataDevolucao(devolucao);

        emp.setStatus(dto.getStatus() != null ? dto.getStatus() : "Pendente");
        emp.setRenovacoes(dto.getRenovacoes() != null ? dto.getRenovacoes() : 0);

        int contador = livro.getContadorEmprestimos() != null ? livro.getContadorEmprestimos() : 0;
        livro.setContadorEmprestimos(contador + 1);

        livroRepository.save(livro);
        Emprestimo salvo = emprestimoRepository.save(emp);

        return toEmprestimoMinDto(salvo);
    }

    // Devolver empréstimo
    public void devolverEmprestimo(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        emp.setStatus("Devolvido");
        emp.setDataDevolvido(LocalDate.now());
        emprestimoRepository.save(emp);
    }

    // Renovar empréstimo
    public EmprestimoDto renovarEmprestimo(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        if ("Devolvido".equalsIgnoreCase(emp.getStatus())) {
            throw new IllegalStateException("Empréstimo já devolvido; renovação não permitida");
        }

        emp.setRenovacoes(Objects.requireNonNullElse(emp.getRenovacoes(), 0) + 1);
        LocalDate novaDevolucao = Objects.requireNonNullElse(emp.getDataDevolucao(), LocalDate.now()).plusDays(7);
        emp.setDataDevolucao(novaDevolucao);
        emp.setStatus("Emprestado");

        Emprestimo atualizado = emprestimoRepository.save(emp);
        return toEmprestimoDto(atualizado);
    }

    // Listar todos
    public List<EmprestimoDto> todosEmprestimos() {
        return emprestimoRepository.findAll()
                .stream()
                .map(this::toEmprestimoDto)
                .toList();
    }

    // Buscar por ID
    public EmprestimoDto buscarId(Long id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));
        return toEmprestimoDto(emp);
    }

    // Buscar por nome do aluno
    public List<EmprestimoDto> buscarPorAluno(String nome) {
        return emprestimoRepository.buscarAluno(nome).stream()
                .map(this::toEmprestimoDto)
                .toList();
    }

    // Buscar por título do livro
    public List<EmprestimoDto> buscarPorLivro(String titulo) {
        return emprestimoRepository.buscarLivro(titulo)
                .stream()
                .map(this::toEmprestimoDto)
                .toList();
    }

    // Buscar por título do livro para renovação
    public List<EmprestimoDto> buscarPorLivroRenovacao(String titulo) {
        return emprestimoRepository.buscarRenovacaoLivro(titulo).stream()
                .map(this::toEmprestimoDto)
                .toList();
    }

    // Buscar por nome do aluno para renovação
    public List<EmprestimoDto> buscarPorAlunoRenovacao(String nome) {
        return emprestimoRepository.buscarRenovacaoAluno(nome).stream()
                .map(this::toEmprestimoDto)
                .toList();
    }

    // Buscar por status
    public List<EmprestimoDto> buscarPorStatus(String status) {
        return emprestimoRepository.buscarStatus(status).stream()
                .map(this::toEmprestimoDto)
                .toList();
    }

    // Buscar devoluções do dia
    public List<EmprestimoDto> buscarDevolucaoDoDia(LocalDate hoje) {
        return emprestimoRepository.buscarDevolucaoDoDia(hoje).stream()
                .map(this::toEmprestimoDto)
                .toList();
    }

    private EmprestimoDto toEmprestimoDto(Emprestimo emprestimo) {
        EmprestimoDto dto = new EmprestimoDto();
        dto.setId(emprestimo.getId());
        dto.setDataEmprestimo(emprestimo.getDataEmprestimo());
        dto.setDataDevolucao(emprestimo.getDataDevolucao());
        dto.setDataDevolvido(emprestimo.getDataDevolvido());
        dto.setRenovacoes(emprestimo.getRenovacoes());
        dto.setStatus(emprestimo.getStatus());

        if (emprestimo.getAluno() != null) {
            AlunoDto aluno = new AlunoDto();
            aluno.setId(emprestimo.getAluno().getId());
            aluno.setNome(emprestimo.getAluno().getNome());
            aluno.setAno(emprestimo.getAluno().getAno());
            aluno.setTurma(emprestimo.getAluno().getTurma());
            dto.setAluno(aluno);
        }

        if (emprestimo.getLivro() != null) {
            LivroMinDto livro = new LivroMinDto();
            livro.setId(emprestimo.getLivro().getId());
            livro.setTitulo(emprestimo.getLivro().getTitulo());
            dto.setLivro(livro);
        }

        return dto;
    }

    private EmprestimoMinDto toEmprestimoMinDto(Emprestimo emprestimo) {
        EmprestimoMinDto dto = new EmprestimoMinDto();
        dto.setId(emprestimo.getId());
        dto.setDataEmprestimo(emprestimo.getDataEmprestimo());
        dto.setDataDevolucao(emprestimo.getDataDevolucao());
        dto.setDataDevolvido(emprestimo.getDataDevolvido());
        dto.setRenovacoes(emprestimo.getRenovacoes());
        dto.setStatus(emprestimo.getStatus());
        dto.setIdLivro(emprestimo.getLivro().getId());
        dto.setIdAluno(emprestimo.getAluno().getId());

        return dto;
    }
}
