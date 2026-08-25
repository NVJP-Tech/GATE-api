package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Remarcacao;
import com.gateclickbus.api.model.Ticket;
import com.gateclickbus.api.model.enums.StatusRemarcacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RemarcacaoRepository extends JpaRepository<Remarcacao, Long> {

        @Query("""
        SELECT r FROM Remarcacao r
        JOIN FETCH r.ticket t
        WHERE r.status = :status
        ORDER BY r.dataHora ASC
        """)
        List<Remarcacao> findByStatusComTicketOrderByDataHoraAsc(@Param("status") StatusRemarcacao status);

        Optional<Remarcacao> findByTicketAndStatus(Ticket ticket, StatusRemarcacao status);

}
