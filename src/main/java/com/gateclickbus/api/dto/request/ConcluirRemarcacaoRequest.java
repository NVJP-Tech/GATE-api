package com.gateclickbus.api.dto.request;

import jakarta.validation.constraints.NotNull;

public class ConcluirRemarcacaoRequest {

    @NotNull(message = "viagemNovaId é obrigatório")
    private Long viagemNovaId;

    public Long getViagemNovaId() { return viagemNovaId; }
    public void setViagemNovaId(Long viagemNovaId) { this.viagemNovaId = viagemNovaId; }
}