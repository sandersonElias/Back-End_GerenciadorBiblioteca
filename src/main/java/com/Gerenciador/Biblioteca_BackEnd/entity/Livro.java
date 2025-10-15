package com.Gerenciador.Biblioteca_BackEnd.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "tb_livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String titulo;
    private String editora;
    private Integer quantTotal;
    private String cdd;
    private String localizacao;
    private Boolean disponibilidade;

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