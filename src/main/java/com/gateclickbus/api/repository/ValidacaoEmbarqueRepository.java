package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Gate;
import com.gateclickbus.api.model.Ticket;
import com.gateclickbus.api.model.ValidacaoEmbarque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ValidacaoEmbarqueRepository extends JpaRepository<ValidacaoEmbarque, Long> {

    List<ValidacaoEmbarque> findByTicketOrderByDataHoraValidacaoDesc(Ticket ticket);

    List<ValidacaoEmbarque> findByGateAndDataHoraValidacaoBetween(
            Gate gate, LocalDateTime inicio, LocalDateTime fim);

    boolean existsByTicketAndDataHoraValidacaoAfter(Ticket ticket, LocalDateTime momento);

    @Query("""
        SELECT v FROM ValidacaoEmbarque v
        JOIN FETCH v.ticket t
        JOIN FETCH t.viagem vi
        LEFT JOIN FETCH vi.plataforma
        WHERE v.ticket = :ticket
        ORDER BY v.dataHoraValidacao DESC
        """)
    List<ValidacaoEmbarque> findByTicketComDadosCompletosOrderByDataHoraValidacaoDesc(@Param("ticket") Ticket ticket);

}