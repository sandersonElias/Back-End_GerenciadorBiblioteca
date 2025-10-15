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
@Table(name = "tb_genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String genero;
}
