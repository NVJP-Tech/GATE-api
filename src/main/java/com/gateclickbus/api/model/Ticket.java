package com.gateclickbus.api.model;


import com.gateclickbus.api.model.enums.StatusTicket;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_externo_id")
    private Long ticketExternoId;

    @Column(name = "codigo_qr", nullable = false, unique = true, length = 100)
    private String codigoQr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passageiro_id", nullable = false)
    private Passageiro passageiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viagem_id", nullable = false)
    private Viagem viagem;

    @Column(length = 10)
    private String assento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTicket status;

    @Column(name = "data_compra")
    private LocalDateTime dataCompra;

    public Ticket() {}


    public Ticket(Long ticketExternoId, String codigoQr, Passageiro passageiro,
                  Viagem viagem, String assento, LocalDateTime dataCompra) {
        this.ticketExternoId = ticketExternoId;
        this.codigoQr = codigoQr;
        this.passageiro = passageiro;
        this.viagem = viagem;
        this.assento = assento;
        this.dataCompra = dataCompra;
        this.status = StatusTicket.COMPRADO;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketExternoId() {
        return ticketExternoId;
    }

    public void setTicketExternoId(Long ticketExternoId) {
        this.ticketExternoId = ticketExternoId;
    }

    public String getCodigoQr() {
        return codigoQr;
    }

    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public void setPassageiro(Passageiro passageiro) {
        this.passageiro = passageiro;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public String getAssento() {
        return assento;
    }

    public void setAssento(String assento) {
        this.assento = assento;
    }

    public StatusTicket getStatus() {
        return status;
    }

    public void setStatus(StatusTicket status) {
        this.status = status;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }
}
