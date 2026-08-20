package com.gateclickbus.api.model;

import com.gateclickbus.api.model.enums.StatusOcupacao;
import jakarta.persistence.*;

@Entity
@Table(name = "plataforma")
public class Plataforma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_ocupacao", nullable = false, length = 20)
    private StatusOcupacao statusOcupacao;

    @Column(name = "capacidade_maxima", nullable = false)
    private Integer capacidadeMaxima;

    @Column(name = "ocupacao_atual", nullable = false)
    private Integer ocupacaoAtual;

    public Plataforma() {
    }


    public Plataforma(String numero, Integer capacidadeMaxima) {
        this.numero = numero;
        this.capacidadeMaxima = capacidadeMaxima;
        this.ocupacaoAtual = 0;
        this.statusOcupacao = StatusOcupacao.VAZIA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public StatusOcupacao getStatusOcupacao() {
        return statusOcupacao;
    }

    public void setStatusOcupacao(StatusOcupacao statusOcupacao) {
        this.statusOcupacao = statusOcupacao;
    }

    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Integer getOcupacaoAtual() {
        return ocupacaoAtual;
    }

    public void setOcupacaoAtual(Integer ocupacaoAtual) {
        this.ocupacaoAtual = ocupacaoAtual;
    }

}
