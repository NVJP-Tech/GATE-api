package com.gateclickbus.api.model;


import com.gateclickbus.api.model.enums.StatusGate;
import jakarta.persistence.*;

@Entity
@Table(name = "gate")
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String localizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusGate status;

    public Gate() {}

    public Gate(String localizacao) {
        this.localizacao = localizacao;
        this.status = StatusGate.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public StatusGate getStatus() {
        return status;
    }

    public void setStatus(StatusGate status) {
        this.status = status;
    }
}
