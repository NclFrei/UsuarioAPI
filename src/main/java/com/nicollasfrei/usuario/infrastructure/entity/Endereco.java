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
@Table(name = "endereco")
public class Endereco {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "complemento", length = 20)
    private String complemento;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "cep", length = 9)
    private String cep;
}
