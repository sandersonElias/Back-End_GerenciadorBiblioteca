package com.Gerenciador.Biblioteca_BackEnd.dto;


import lombok.Getter;

import java.sql.Date;
import java.util.UUID;

@Getter
public class EmprestimoDto {

    private UUID id;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Integer renovacoes;
    private String Status;
    private LivroMinDto livro;
    private AlunoDto aluno;
}
