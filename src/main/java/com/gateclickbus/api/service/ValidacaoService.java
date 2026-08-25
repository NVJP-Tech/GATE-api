package com.gateclickbus.api.service;


import com.gateclickbus.api.dto.response.ValidacaoResponse;
import com.gateclickbus.api.exception.TicketNaoEncontradoException;
import com.gateclickbus.api.model.Gate;
import com.gateclickbus.api.model.Plataforma;
import com.gateclickbus.api.model.Ticket;
import com.gateclickbus.api.model.ValidacaoEmbarque;
import com.gateclickbus.api.model.enums.CanalValidacao;
import com.gateclickbus.api.model.enums.ResultadoTriagem;
import com.gateclickbus.api.model.enums.StatusTicket;
import com.gateclickbus.api.repository.GateRepository;
import com.gateclickbus.api.repository.TicketRepository;
import com.gateclickbus.api.repository.ValidacaoEmbarqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ValidacaoService {
    private static final int JANELA_IDEMPOTENCIA_MINUTOS = 2;


    private final TicketRepository ticketRepository;
    private final GateRepository gateRepository;
    private final ValidacaoEmbarqueRepository validacaoEmbarqueRepository;
    private final TriagemService triagemService;
    private final RemarcacaoService remarcacaoService;

    public ValidacaoService(TicketRepository ticketRepository,
                            GateRepository gateRepository,
                            ValidacaoEmbarqueRepository validacaoEmbarqueRepository,
                            TriagemService triagemService,
                            RemarcacaoService remarcacaoService) {
        this.ticketRepository = ticketRepository;
        this.gateRepository = gateRepository;
        this.validacaoEmbarqueRepository = validacaoEmbarqueRepository;
        this.triagemService = triagemService;
        this.remarcacaoService = remarcacaoService;
    }

    /*
    @Transactional
    public ValidacaoEmbarque validar(String codigoQr,
                                     CanalValidacao canal,
                                     Long gateId,
                                     Double latitude,
                                     Double longitude) {
        // Ticket ticket = ticketRepository.findByCodigoQr(codigoQr)
        //       .orElseThrow(() -> new TicketNaoEncontradoException(codigoQr));

        Ticket ticket = ticketRepository.findByCodigoQrComViagemEPlataforma(codigoQr)
                .orElseThrow(() -> new TicketNaoEncontradoException(codigoQr));

        ValidacaoEmbarque validacaoExistente = buscarValidacaoRecente(ticket);
        if (validacaoExistente != null) {
            return validacaoExistente;
        }
        Gate gate = resolverGate(canal, gateId);

        int tempoRestanteMinutos = calcularTempoRestanteMinutos(
                ticket.getViagem().getDataHoraPartida());

        ResultadoTriagem resultado = decidirResultado(ticket, tempoRestanteMinutos);

        ValidacaoEmbarque validacao = new ValidacaoEmbarque(
                ticket, gate, canal, latitude, longitude, tempoRestanteMinutos, resultado);

        ticket.setStatus(StatusTicket.VALIDADO);

        return validacaoEmbarqueRepository.save(validacao);
    }
    */

    @Transactional
    public ValidacaoResponse validar(String codigoQr,
                                     CanalValidacao canal,
                                     Long gateId,
                                     Double latitude,
                                     Double longitude) {

        Ticket ticket = ticketRepository.findByCodigoQrComViagemEPlataforma(codigoQr)
                .orElseThrow(() -> new TicketNaoEncontradoException(codigoQr));

        ValidacaoEmbarque validacaoExistente = buscarValidacaoRecente(ticket);
        if (validacaoExistente != null) {
            // A conversão ocorre DENTRO do método transacional
            return ValidacaoResponse.fromEntity(validacaoExistente);
        }
        Gate gate = resolverGate(canal, gateId);

        int tempoRestanteMinutos = calcularTempoRestanteMinutos(
                ticket.getViagem().getDataHoraPartida());

        ResultadoTriagem resultado = decidirResultado(ticket, tempoRestanteMinutos);

        ValidacaoEmbarque validacao = new ValidacaoEmbarque(
                ticket, gate, canal, latitude, longitude, tempoRestanteMinutos, resultado);

        ticket.setStatus(StatusTicket.VALIDADO);

        ValidacaoEmbarque validacaoSalva = validacaoEmbarqueRepository.save(validacao);

        return ValidacaoResponse.fromEntity(validacaoSalva);
    }

    private ValidacaoEmbarque buscarValidacaoRecente(Ticket ticket) {
        LocalDateTime janela = LocalDateTime.now().minusMinutes(JANELA_IDEMPOTENCIA_MINUTOS);
        boolean jaValidadoRecentemente = validacaoEmbarqueRepository
                .existsByTicketAndDataHoraValidacaoAfter(ticket, janela);

        if (!jaValidadoRecentemente) {
            return null;
        }

        List<ValidacaoEmbarque> historico = validacaoEmbarqueRepository
                .findByTicketComDadosCompletosOrderByDataHoraValidacaoDesc(ticket); // <-- troca aqui

        return historico.stream().findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Inconsistência: idempotência indicou validação recente, mas nenhuma foi encontrada"));
    }

    private Gate resolverGate(CanalValidacao canal, Long gateId) {
        if (canal != CanalValidacao.GATE_QR) {
            return null;
        }
        return gateRepository.findById(gateId)
                .orElseThrow(() -> new NoSuchElementException("Gate não encontrado: " + gateId));
    }

    private ResultadoTriagem decidirResultado(Ticket ticket, int tempoRestanteMinutos) {
        if (tempoRestanteMinutos < 0) {
            remarcacaoService.processarAtraso(ticket);
            return ResultadoTriagem.REALOCACAO;
        }
        return triagemService.decidir(tempoRestanteMinutos, ticket.getViagem().getPlataforma());
    }

    private int calcularTempoRestanteMinutos(LocalDateTime dataHoraPartida) {
        return (int) Duration.between(LocalDateTime.now(), dataHoraPartida).toMinutes();
    }

}
