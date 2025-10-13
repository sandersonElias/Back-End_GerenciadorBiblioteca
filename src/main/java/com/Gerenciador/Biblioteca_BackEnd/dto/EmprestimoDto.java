package com.Gerenciador.Biblioteca_BackEnd.dto;


import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
public class EmprestimoDto {

    private Integer id;
    private String dataEmprestimo;
    private String dataPrevista;
    private LivroMinDto livro;
    private AlunoMinDto aluno;
}
