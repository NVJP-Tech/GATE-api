package com.gateclickbus.api.exception;

public class TicketNaoEncontradoException extends RuntimeException {
    public TicketNaoEncontradoException(String message) {
        super(message);
    }
}
