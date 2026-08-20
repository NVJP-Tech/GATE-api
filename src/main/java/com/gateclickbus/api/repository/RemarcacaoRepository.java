package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Remarcacao;
import com.gateclickbus.api.model.Ticket;
import com.gateclickbus.api.model.enums.StatusRemarcacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RemarcacaoRepository extends JpaRepository<Remarcacao,Long> {
    List<Remarcacao> findByStatusOrderByDataHoraAsc(StatusRemarcacao status);

    Optional<Remarcacao> findByTicketAndStatus(Ticket ticket, StatusRemarcacao status);

}
