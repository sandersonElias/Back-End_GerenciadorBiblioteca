package com.Gerenciador.Biblioteca_BackEnd.dto;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
@Data
public class AlunoDto {
    private UUID id;
    private String nome;
    private Integer ano;
    private String turma;
}
