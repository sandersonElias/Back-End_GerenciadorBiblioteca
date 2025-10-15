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
@Table(name = "tb_catalogacao")
public class Catalogacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String catalogacao;
}
