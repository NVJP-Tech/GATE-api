package com.gateclickbus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal - unico ponto de entrada da API.
 *
 * Nao contem regra de negocio: apenas sobe o contexto do Spring Boot.
 * Toda a logica fica nas camadas service/, controller/, repository/ etc.
 */

@SpringBootApplication
public class GateApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateApiApplication.class, args);
    }

}
