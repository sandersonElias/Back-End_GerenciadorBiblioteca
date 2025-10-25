package com.Gerenciador.Biblioteca_BackEnd.service;

import com.Gerenciador.Biblioteca_BackEnd.dto.EmprestimoDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final AlunoRepository alunoRepository;
    private final LivroRepository livroRepository;
    private final ObjectMapper objectMapper;

    //Novo Emprestimo:
    public EmprestimoDto insertEmprestimo(EmprestimoDto emprestimoDto){
        Emprestimo emprestimo = new Emprestimo();
        Aluno aluno = alunoRepository.findById(emprestimoDto.getIdAluno())
                        .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        emprestimo.setAluno(aluno);
        Livro livro = livroRepository.findById(emprestimoDto.getIdLivro())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        emprestimo.setLivro(livro);

        emprestimo.setDataEmprestimo(emprestimoDto.getDataEmprestimo());
        emprestimo.setDataDevolucao(emprestimoDto.getDataDevolucao());
        emprestimo.setStatus(emprestimoDto.getStatus());
        Emprestimo novo = emprestimoRepository.save(emprestimo);
        return objectMapper.convertValue(novo, EmprestimoDto.class);
    }

    //Deletar Emprestimo:
    public void deletarEmprestimo(Long id){
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Emprestimo não encontrado"));
        emprestimoRepository.delete(emprestimo);
    }

    //Update Emprestimo(Renovar Emprestimo):
    public EmprestimoDto renovarEmprestimo (Long id, EmprestimoDto emprestimoDto){
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Emprestimo mão encontrado"));

        emprestimo.setDataEmprestimo(emprestimoDto.getDataEmprestimo());
        Aluno aluno = alunoRepository.findById(emprestimoDto.getIdAluno())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        emprestimo.setAluno(aluno);
        Livro livro = livroRepository.findById(emprestimoDto.getIdLivro())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        emprestimo.setLivro(livro);

        Emprestimo atualizado = emprestimoRepository.save(emprestimo);
        return objectMapper.convertValue(atualizado, EmprestimoDto.class);
    }

    //Lista de Emprestimo:
    public List<EmprestimoDto> todosEmprestimos(){
        return emprestimoRepository.findAll()
                .stream()
                .map(emprestimo -> objectMapper.convertValue(emprestimo, EmprestimoDto.class))
                .toList();
    }

    //Buscar por Id:
    public EmprestimoDto buscarId (Long idEmprestimo){
        return emprestimoRepository.findById(idEmprestimo)
                .stream().map(emprestimo -> objectMapper.convertValue(emprestimo, EmprestimoDto.class))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Emprestimo não encontrado."));
    }

    //Buscar por Nome Aluno:
    public EmprestimoDto buscarAluno (String aluno){
        return emprestimoRepository.buscarAluno(aluno)
                .stream().map(emprestimo -> objectMapper.convertValue(emprestimo, EmprestimoDto.class))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Emprestimo não encontrado."));
    }
}