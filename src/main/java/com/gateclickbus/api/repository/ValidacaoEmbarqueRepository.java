package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Gate;
import com.gateclickbus.api.model.Ticket;
import com.gateclickbus.api.model.ValidacaoEmbarque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ValidacaoEmbarqueRepository extends JpaRepository<ValidacaoEmbarque, Long> {
    List<ValidacaoEmbarque> findByTicketOrderByDataHoraValidacaoDesc(Ticket ticket);

    List<ValidacaoEmbarque> findByGateAndDataHoraValidacaoBetween(
            Gate gate, LocalDateTime inicio, LocalDateTime fim);

    boolean existsByTicketAndDataHoraValidacaoAfter(Ticket ticket, LocalDateTime momento);


}
