package com.Gerenciador.Biblioteca_BackEnd.dto;

import lombok.Getter;

import java.sql.Date;

@Getter
public class EmprestimoMinDto {
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Integer renovacoes;
    private String Status;
    private Long idLivro;
    private Long idAluno;
}
