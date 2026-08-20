package com.gateclickbus.api.dto.response;

import com.gateclickbus.api.model.Plataforma;
import com.gateclickbus.api.model.enums.StatusOcupacao;

public class StatusPlataformaResponse {

    private String numero;
    private StatusOcupacao statusOcupacao;
    private int ocupacaoAtual;
    private int capacidadeMaxima;

    public static StatusPlataformaResponse fromEntity(Plataforma plataforma) {
        StatusPlataformaResponse dto = new StatusPlataformaResponse();
        dto.numero = plataforma.getNumero();
        dto.statusOcupacao = plataforma.getStatusOcupacao();
        dto.ocupacaoAtual = plataforma.getOcupacaoAtual();
        dto.capacidadeMaxima = plataforma.getCapacidadeMaxima();
        return dto;
    }

    public String getNumero() { return numero; }
    public StatusOcupacao getStatusOcupacao() { return statusOcupacao; }
    public int getOcupacaoAtual() { return ocupacaoAtual; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
}