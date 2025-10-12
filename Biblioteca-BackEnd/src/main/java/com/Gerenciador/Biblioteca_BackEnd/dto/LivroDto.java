package com.Gerenciador.Biblioteca_BackEnd.dto;

import com.Gerenciador.Biblioteca_BackEnd.entity.Autor;
import com.Gerenciador.Biblioteca_BackEnd.entity.Catalogacao;
import com.Gerenciador.Biblioteca_BackEnd.entity.Genero;
import lombok.Getter;

@Getter
public class LivroDto {

    private Integer id;
    private String titulo;
    private String editora;
    private String disponibilidade;
    private String cdd;
    private String localizacao;
    private Genero genero;
    private Catalogacao catalogacao;
    private Autor autor;
}