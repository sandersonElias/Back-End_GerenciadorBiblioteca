package com.Gerenciador.Biblioteca_BackEnd.dto;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
@Data
public class LivroMinDto {
    private UUID id;
    private String titulo;
}
