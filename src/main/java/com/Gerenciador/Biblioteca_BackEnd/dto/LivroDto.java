package com.Gerenciador.Biblioteca_BackEnd.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LivroDto {
    private Long id;
    private String titulo;
    private String editora;
    private String cdd;
    private String localizacao;
    private String descricao;
    private String urlImg;

    private Integer totalExemplares;
    private Integer contadorEmprestimos;
    private Integer emprestados;
    private Boolean disponivel;

    private AutorDto autor;
    private GeneroDto genero;
    private CatalogacaoDto catalogacao;
}
