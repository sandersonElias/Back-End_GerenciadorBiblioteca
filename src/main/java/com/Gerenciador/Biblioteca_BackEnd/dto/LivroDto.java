package com.Gerenciador.Biblioteca_BackEnd.dto;

import com.Gerenciador.Biblioteca_BackEnd.entity.Autor;
import com.Gerenciador.Biblioteca_BackEnd.entity.Catalogacao;
import com.Gerenciador.Biblioteca_BackEnd.entity.Genero;
import lombok.Getter;

@Getter
public class LivroDto {

    private Long id;
    private String titulo;
    private String editora;
    private Boolean disponibilidade;
    private String cdd;
    private Integer exemplares;
    private String localizacao;
    private Genero genero;
    private Catalogacao catalogacao;
    private Autor autor;
}