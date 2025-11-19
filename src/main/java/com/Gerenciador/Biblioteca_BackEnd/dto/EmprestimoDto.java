package com.Gerenciador.Biblioteca_BackEnd.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmprestimoDto {
    private Long id;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private LocalDate dataDevolvido;
    private Integer renovacoes;
    private String status;
    private LivroMinDto livro;
    private AlunoDto aluno;
}
