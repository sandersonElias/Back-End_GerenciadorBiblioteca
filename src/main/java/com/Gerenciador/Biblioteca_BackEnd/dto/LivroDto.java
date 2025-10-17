package com.Gerenciador.Biblioteca_BackEnd.dto;

import com.Gerenciador.Biblioteca_BackEnd.entity.Autor;
import com.Gerenciador.Biblioteca_BackEnd.entity.Catalogacao;
import com.Gerenciador.Biblioteca_BackEnd.entity.Genero;
import lombok.Getter;

import java.util.UUID;

@Getter
public class LivroDto {

    private UUID id;
    private String titulo;
    private String editora;
    private Boolean disponibilidade;
    private String cdd;
    private Integer quantTotal;
    private String localizacao;
    private Genero genero;
    private Catalogacao catalogacao;
    private Autor autor;
}