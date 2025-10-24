package com.Gerenciador.Biblioteca_BackEnd.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "tb_livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String editora;
    private Integer exemplares;
    private String cdd;
    private String localizacao;
    private Boolean disponibilidade;
    private String descricao;
    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "catalogacao_id")
    private Catalogacao catalogacao;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
}