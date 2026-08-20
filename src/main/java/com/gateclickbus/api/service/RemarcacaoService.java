package com.gateclickbus.api.service;

import com.gateclickbus.api.model.Remarcacao;
import com.gateclickbus.api.model.Ticket;
import com.gateclickbus.api.model.Viagem;
import com.gateclickbus.api.model.enums.StatusRemarcacao;
import com.gateclickbus.api.model.enums.StatusTicket;
import com.gateclickbus.api.model.enums.StatusViagem;
import com.gateclickbus.api.model.enums.TipoRemarcacao;
import com.gateclickbus.api.repository.RemarcacaoRepository;
import com.gateclickbus.api.repository.TicketRepository;
import com.gateclickbus.api.repository.ViagemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class RemarcacaoService {

    private static final int LIMITE_DIAS_REMARCACAO_AUTOMATICA = 1;

    private final ViagemRepository viagemRepository;
    private final TicketRepository ticketRepository;
    private final RemarcacaoRepository remarcacaoRepository;

    public RemarcacaoService(ViagemRepository viagemRepository,
                             TicketRepository ticketRepository,
                             RemarcacaoRepository remarcacaoRepository) {
        this.viagemRepository = viagemRepository;
        this.ticketRepository = ticketRepository;
        this.remarcacaoRepository = remarcacaoRepository;
    }

    @Transactional
    public void processarAtraso(Ticket ticketAtrasado) {

        if (existeRemarcacaoPendente(ticketAtrasado)) {
            return;
        }

        Viagem viagemOriginal = ticketAtrasado.getViagem();

        Optional<Viagem> proximaViagem = viagemRepository
                .findFirstByOrigemAndDestinoAndDataHoraPartidaAfterAndStatusNotOrderByDataHoraPartidaAsc(
                        viagemOriginal.getOrigem(),
                        viagemOriginal.getDestino(),
                        LocalDateTime.now(),
                        StatusViagem.CANCELADA
                );

        if (proximaViagem.isPresent() && dentroDoLimiteAutomatico(proximaViagem.get())) {
            remarcarAutomaticamente(ticketAtrasado, proximaViagem.get());
        } else {
            sinalizarRemarcacaoManual(ticketAtrasado);
        }
    }

    @Transactional(readOnly = true)
    public List<Remarcacao> listarPendentes() {
        return remarcacaoRepository.findByStatusOrderByDataHoraAsc(StatusRemarcacao.PENDENTE);
    }

    @Transactional
    public Remarcacao concluirManualmente(Long remarcacaoId, Long viagemNovaId) {
        Remarcacao remarcacao = remarcacaoRepository.findById(remarcacaoId)
                .orElseThrow(() -> new NoSuchElementException("Remarcação não encontrada: " + remarcacaoId));

        Viagem novaViagem = viagemRepository.findById(viagemNovaId)
                .orElseThrow(() -> new NoSuchElementException("Viagem não encontrada: " + viagemNovaId));

        criarTicketRemarcado(remarcacao.getTicket(), novaViagem);

        remarcacao.setViagemNova(novaViagem);
        remarcacao.setStatus(StatusRemarcacao.CONCLUIDA);

        return remarcacao;
    }

    private boolean existeRemarcacaoPendente(Ticket ticket) {
        return remarcacaoRepository
                .findByTicketAndStatus(ticket, StatusRemarcacao.PENDENTE)
                .isPresent();
    }

    private boolean dentroDoLimiteAutomatico(Viagem proximaViagem) {
        long diasAteProximaViagem = Duration.between(
                LocalDateTime.now(), proximaViagem.getDataHoraPartida()).toDays();
        return diasAteProximaViagem <= LIMITE_DIAS_REMARCACAO_AUTOMATICA;
    }

    private void remarcarAutomaticamente(Ticket ticketOriginal, Viagem proximaViagem) {
        criarTicketRemarcado(ticketOriginal, proximaViagem);

        Remarcacao remarcacao = new Remarcacao(
                ticketOriginal,
                proximaViagem,
                TipoRemarcacao.AUTOMATICA,
                "Passageiro validado apos o horario de embarque; remarcado automaticamente para a proxima viagem da mesma rota."
        );
        remarcacaoRepository.save(remarcacao);
    }

    private void sinalizarRemarcacaoManual(Ticket ticketOriginal) {
        ticketOriginal.setStatus(StatusTicket.EXPIRADO);

        Remarcacao remarcacao = new Remarcacao(
                ticketOriginal,
                null,
                TipoRemarcacao.MANUAL,
                "Nao ha viagem da mesma rota em ate 1 dia; aguardando remarcacao manual."
        );
        remarcacaoRepository.save(remarcacao);
    }

    private Ticket criarTicketRemarcado(Ticket ticketOriginal, Viagem novaViagem) {
        Ticket novoTicket = new Ticket(
                ticketOriginal.getTicketExternoId(),
                UUID.randomUUID().toString(),
                ticketOriginal.getPassageiro(),
                novaViagem,
                ticketOriginal.getAssento(),
                LocalDateTime.now()
        );
        ticketRepository.save(novoTicket);
        ticketOriginal.setStatus(StatusTicket.REMARCADO);
        return novoTicket;
    }
}