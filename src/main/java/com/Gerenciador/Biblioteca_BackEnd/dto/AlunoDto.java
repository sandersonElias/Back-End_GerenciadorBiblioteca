package com.Gerenciador.Biblioteca_BackEnd.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class AlunoDto {
    private Long id;
    private String nome;
    private Integer ano;
    private String turma;
}
