package com.gateclickbus.api.controller;

import com.gateclickbus.api.dto.response.TicketResponse;
import com.gateclickbus.api.repository.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

/**
 * SIMPLIFICACAO DE MVP: acessa o Repository diretamente, sem uma
 * TicketService dedicada, ja que este e o unico metodo de leitura
 * que este controller expoe. Numa evolucao pos-MVP, isso migraria
 * para um TicketService, mantendo a regra geral da arquitetura.
 */
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> buscarPorId(@PathVariable Long id) {
        var ticket = ticketRepository.findByIdComPassageiroEViagem(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket não encontrado: " + id));
        return ResponseEntity.ok(TicketResponse.fromEntity(ticket));
    }
}