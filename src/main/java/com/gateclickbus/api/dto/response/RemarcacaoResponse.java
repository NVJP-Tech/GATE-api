package com.gateclickbus.api.dto.response;

import com.gateclickbus.api.model.Remarcacao;
import com.gateclickbus.api.model.enums.StatusRemarcacao;
import com.gateclickbus.api.model.enums.TipoRemarcacao;

import java.time.LocalDateTime;

public class RemarcacaoResponse {

    private Long id;
    private String codigoQrTicketOriginal;
    private TipoRemarcacao tipo;
    private StatusRemarcacao status;
    private String motivo;
    private LocalDateTime dataHora;

    public static RemarcacaoResponse fromEntity(Remarcacao remarcacao) {
        RemarcacaoResponse dto = new RemarcacaoResponse();
        dto.id = remarcacao.getId();
        dto.codigoQrTicketOriginal = remarcacao.getTicket().getCodigoQr();
        dto.tipo = remarcacao.getTipo();
        dto.status = remarcacao.getStatus();
        dto.motivo = remarcacao.getMotivo();
        dto.dataHora = remarcacao.getDataHora();
        return dto;
    }

    public Long getId() { return id; }
    public String getCodigoQrTicketOriginal() { return codigoQrTicketOriginal; }
    public TipoRemarcacao getTipo() { return tipo; }
    public StatusRemarcacao getStatus() { return status; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getDataHora() { return dataHora; }
}