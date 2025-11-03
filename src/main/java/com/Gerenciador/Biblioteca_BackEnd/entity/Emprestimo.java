package com.Gerenciador.Biblioteca_BackEnd.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "tb_emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Date dataDevolvido;
    private Integer renovacoes = 0;

    @Column(nullable = false)
    private String Status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;
}
