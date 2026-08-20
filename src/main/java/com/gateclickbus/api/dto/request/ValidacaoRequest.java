package com.gateclickbus.api.dto.request;

import com.gateclickbus.api.model.enums.CanalValidacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ValidacaoRequest {

    @NotBlank(message = "codigoQr é obrigatório")
    private String codigoQr;

    @NotNull(message = "canalValidacao é obrigatório")
    private CanalValidacao canalValidacao;

    private Long gateId;       // obrigatorio apenas se canalValidacao = GATE_QR
    private Double latitude;   // obrigatorio apenas se canalValidacao = APP_GPS
    private Double longitude;

    public String getCodigoQr() { return codigoQr; }
    public void setCodigoQr(String codigoQr) { this.codigoQr = codigoQr; }

    public CanalValidacao getCanalValidacao() { return canalValidacao; }
    public void setCanalValidacao(CanalValidacao canalValidacao) { this.canalValidacao = canalValidacao; }

    public Long getGateId() { return gateId; }
    public void setGateId(Long gateId) { this.gateId = gateId; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}