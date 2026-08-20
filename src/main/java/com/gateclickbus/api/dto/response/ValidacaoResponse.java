package com.gateclickbus.api.dto.response;

import com.gateclickbus.api.model.ValidacaoEmbarque;
import com.gateclickbus.api.model.enums.ResultadoTriagem;
import com.gateclickbus.api.model.enums.StatusOcupacao;

import java.time.LocalDateTime;

public class ValidacaoResponse {

    private ResultadoTriagem resultadoTriagem;
    private int tempoRestanteMinutos;
    private String plataformaNumero;
    private StatusOcupacao statusOcupacao;
    private LocalDateTime dataHoraValidacao;

    public static ValidacaoResponse fromEntity(ValidacaoEmbarque validacao) {
        ValidacaoResponse dto = new ValidacaoResponse();
        dto.resultadoTriagem = validacao.getResultadoTriagem();
        dto.tempoRestanteMinutos = validacao.getTempoRestanteMinutos();
        dto.dataHoraValidacao = validacao.getDataHoraValidacao();

        var plataforma = validacao.getTicket().getViagem().getPlataforma();
        if (plataforma != null) {
            dto.plataformaNumero = plataforma.getNumero();
            dto.statusOcupacao = plataforma.getStatusOcupacao();
        }
        return dto;
    }

    public ResultadoTriagem getResultadoTriagem() { return resultadoTriagem; }
    public int getTempoRestanteMinutos() { return tempoRestanteMinutos; }
    public String getPlataformaNumero() { return plataformaNumero; }
    public StatusOcupacao getStatusOcupacao() { return statusOcupacao; }
    public LocalDateTime getDataHoraValidacao() { return dataHoraValidacao; }
}