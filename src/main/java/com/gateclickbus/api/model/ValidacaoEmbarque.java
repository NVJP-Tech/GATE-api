package com.gateclickbus.api.model;

import com.gateclickbus.api.model.enums.CanalValidacao;
import com.gateclickbus.api.model.enums.ResultadoTriagem;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "validacao_embarque")
public class ValidacaoEmbarque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gate_id")
    private Gate gate;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal_validacao", nullable = false, length = 20)
    private CanalValidacao canalValidacao;

    private Double latitude;
    private Double longitude;

    @Column(name = "data_hora_validacao", nullable = false)
    private LocalDateTime dataHoraValidacao;

    @Column(name = "tempo_restante_minutos", nullable = false)
    private Integer tempoRestanteMinutos;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultado_triagem", nullable = false, length = 20)
    private ResultadoTriagem resultadoTriagem;

    public ValidacaoEmbarque() {}

    public ValidacaoEmbarque(Ticket ticket, Gate gate, CanalValidacao canalValidacao,
                             Double latitude, Double longitude,
                             Integer tempoRestanteMinutos, ResultadoTriagem resultadoTriagem)
    {
        this.ticket = ticket;
        this.gate = gate;
        this.canalValidacao = canalValidacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dataHoraValidacao = LocalDateTime.now();
        this.tempoRestanteMinutos = tempoRestanteMinutos;
        this.resultadoTriagem = resultadoTriagem;
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

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    public CanalValidacao getCanalValidacao() {
        return canalValidacao;
    }

    public void setCanalValidacao(CanalValidacao canalValidacao) {
        this.canalValidacao = canalValidacao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getDataHoraValidacao() {
        return dataHoraValidacao;
    }

    public void setDataHoraValidacao(LocalDateTime dataHoraValidacao) {
        this.dataHoraValidacao = dataHoraValidacao;
    }

    public Integer getTempoRestanteMinutos() {
        return tempoRestanteMinutos;
    }

    public void setTempoRestanteMinutos(Integer tempoRestanteMinutos) {
        this.tempoRestanteMinutos = tempoRestanteMinutos;
    }

    public ResultadoTriagem getResultadoTriagem() {
        return resultadoTriagem;
    }

    public void setResultadoTriagem(ResultadoTriagem resultadoTriagem) {
        this.resultadoTriagem = resultadoTriagem;
    }

}
