package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByCodigoQr(String codigoQr);

    @Query("""
        SELECT t FROM Ticket t
        JOIN FETCH t.viagem v
        LEFT JOIN FETCH v.plataforma
        WHERE t.codigoQr = :codigoQr
        """)
    Optional<Ticket> findByCodigoQrComViagemEPlataforma(@Param("codigoQr") String codigoQr);

    @Query("""
        SELECT t FROM Ticket t
        JOIN FETCH t.passageiro
        JOIN FETCH t.viagem v
        LEFT JOIN FETCH v.plataforma
        WHERE t.id = :id
        """)
    Optional<Ticket> findByIdComPassageiroEViagem(@Param("id") Long id);
}
