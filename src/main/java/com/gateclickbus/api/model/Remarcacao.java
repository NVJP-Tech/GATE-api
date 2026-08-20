package com.gateclickbus.api.model;


import com.gateclickbus.api.model.enums.StatusRemarcacao;
import com.gateclickbus.api.model.enums.TipoRemarcacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "remarcacao")
public class Remarcacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viagem_nova_id")
    private Viagem viagemNova;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoRemarcacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusRemarcacao status;

    @Column(length = 255)
    private String motivo;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public Remarcacao() {}

    public Remarcacao(Ticket ticket, Viagem viagemNova, TipoRemarcacao tipo, String motivo)
    {
        this.ticket = ticket;
        this.viagemNova = viagemNova;
        this.tipo = tipo;
        this.motivo = motivo;
        this.dataHora = LocalDateTime.now();
        this.status = (viagemNova != null) ? StatusRemarcacao.CONCLUIDA : StatusRemarcacao.PENDENTE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Viagem getViagemNova() {
        return viagemNova;
    }

    public void setViagemNova(Viagem viagemNova) {
        this.viagemNova = viagemNova;
    }

    public TipoRemarcacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoRemarcacao tipo) {
        this.tipo = tipo;
    }

    public StatusRemarcacao getStatus() {
        return status;
    }

    public void setStatus(StatusRemarcacao status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }


}
