package com.nicollasfrei.usuario.infrastructure.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "telefone")
public class Telefone {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "ddd", length = 3)
    private String ddd;
}
