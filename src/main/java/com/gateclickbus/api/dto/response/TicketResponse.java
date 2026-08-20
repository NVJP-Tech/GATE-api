package com.gateclickbus.api.dto.response;

import com.gateclickbus.api.model.Ticket;
import com.gateclickbus.api.model.enums.StatusTicket;

import java.time.LocalDateTime;

public class TicketResponse {

    private Long id;
    private String codigoQr;
    private StatusTicket status;
    private String passageiroNome;
    private String origem;
    private String destino;
    private LocalDateTime dataHoraPartida;

    public static TicketResponse fromEntity(Ticket ticket) {
        TicketResponse dto = new TicketResponse();
        dto.id = ticket.getId();
        dto.codigoQr = ticket.getCodigoQr();
        dto.status = ticket.getStatus();
        dto.passageiroNome = ticket.getPassageiro().getNome();
        dto.origem = ticket.getViagem().getOrigem();
        dto.destino = ticket.getViagem().getDestino();
        dto.dataHoraPartida = ticket.getViagem().getDataHoraPartida();
        return dto;
    }

    public Long getId() { return id; }
    public String getCodigoQr() { return codigoQr; }
    public StatusTicket getStatus() { return status; }
    public String getPassageiroNome() { return passageiroNome; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public LocalDateTime getDataHoraPartida() { return dataHoraPartida; }
}