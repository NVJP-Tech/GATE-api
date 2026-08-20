package com.gateclickbus.api.exception;

public class PlataformaNaoEncontradaException extends RuntimeException {
    public PlataformaNaoEncontradaException(Long id) {
        super("Plataforma não encontrada: " + id);
    }
}
