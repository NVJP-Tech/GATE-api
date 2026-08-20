package com.gateclickbus.api.model;



import com.gateclickbus.api.model.enums.StatusViagem;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "viagem")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String origem;

    @Column(nullable = false, length = 100)
    private String destino;

    @Column(name = "data_hora_partida", nullable = false)
    private LocalDateTime dataHoraPartida;

    // Dados do onibus embutidos (sem entidade Onibus separada no MVP)
    @Column(name = "placa_onibus", length = 10)
    private String placaOnibus;

    @Column(name = "capacidade_onibus")
    private Integer capacidadeOnibus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusViagem status;


    public Viagem(){
    }

    public Viagem(String origem, String destino, LocalDateTime dataHoraPartida,
                  String placaOnibus, Integer capacidadeOnibus, Plataforma plataforma)
    {
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.placaOnibus = placaOnibus;
        this.capacidadeOnibus = capacidadeOnibus;
        this.plataforma = plataforma;
        this.status = StatusViagem.AGENDADA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getDataHoraPartida() {
        return dataHoraPartida;
    }

    public void setDataHoraPartida(LocalDateTime dataHoraPartida) {
        this.dataHoraPartida = dataHoraPartida;
    }

    public String getPlacaOnibus() {
        return placaOnibus;
    }

    public void setPlacaOnibus(String placaOnibus) {
        this.placaOnibus = placaOnibus;
    }

    public Integer getCapacidadeOnibus() {
        return capacidadeOnibus;
    }

    public void setCapacidadeOnibus(Integer capacidadeOnibus) {
        this.capacidadeOnibus = capacidadeOnibus;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public StatusViagem getStatus() {
        return status;
    }

    public void setStatus(StatusViagem status) {
        this.status = status;
    }
}
