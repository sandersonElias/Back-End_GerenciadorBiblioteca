package com.Gerenciador.Biblioteca_BackEnd.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmprestimoMinDto {
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private Integer renovacoes;
    private String status;
    private Long idLivro;
    private Long idAluno;
}
